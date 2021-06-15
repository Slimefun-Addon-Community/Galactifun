package io.github.addoncommunity.galactifun.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.TheUniverse;
import io.github.addoncommunity.galactifun.api.universe.UniversalObject;
import io.github.addoncommunity.galactifun.api.worlds.CelestialWorld;
import io.github.addoncommunity.galactifun.api.worlds.WorldManager;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.MenuClickHandler;

/**
 * Class for exploring the universe through ChestMenus
 * 
 * @author Mooy1
 */
public final class GalacticExplorer {

    private final TheUniverse theUniverse;
    private final WorldManager worldManager;
    private final Map<UUID, UniversalObject<?>> history = new HashMap<>();

    public GalacticExplorer(Galactifun galactifun) {
        this.theUniverse = galactifun.getTheUniverse();
        this.worldManager = galactifun.getWorldManager();
    }
    
    public void explore(@Nonnull Player p, @Nonnull MenuClickHandler exitHandler) {
        open(p, this.history.computeIfAbsent(p.getUniqueId(), k -> this.theUniverse), exitHandler, false);
    }
    
    private void open(@Nonnull Player p, @Nonnull UniversalObject<?> object, @Nonnull MenuClickHandler exitHandler, boolean history) {

        List<UniversalObject<?>> orbiters = object.getOrbiters();

        // this shouldn't happen
        if (orbiters.size() == 0) {
            open(p, this.theUniverse, exitHandler, true);
            return;
        }
        
        // add to history
        if (history) {
            this.history.put(p.getUniqueId(), object);
        }

        // setup menu
        ChestMenu menu = new ChestMenu(Galactifun.inst(), object.getName());
        menu.setEmptySlotsClickable(false);

        // back button
        menu.addItem(0, ChestMenuUtils.getBackButton(p));
        if (object.getOrbiting() == null) {
            menu.addMenuClickHandler(0, exitHandler);
        } else {
            menu.addMenuClickHandler(0, (p1, slot, item, action, a) -> {
                open(p1, object.getOrbiting(), exitHandler,true);
                return false;
            });
        }
        
        CelestialWorld current = this.worldManager.getWorld(p.getWorld());
        boolean known = current != null;

        // objects
        for (int i = 0 ; i < Math.min(52, orbiters.size()); i++) {
            UniversalObject<?> orbiter = orbiters.get(i);
            ItemStack item = orbiter.getItem();
            
            if (known) {
                double distance = orbiter.getDistanceTo(current);
                
                // add distance from current
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();
                if (lore != null) {
                    lore.remove(lore.size() - 1);

                    if (distance > 0) {
                        lore.add(ChatColors.color("&7Distance: " + (distance < 1
                                ? LorePreset.format(distance * Util.KM_PER_LY) + " Kilometers"
                                : distance + " Light Years")
                        ));
                    } else {
                        lore.add(ChatColors.color("&7You are here!"));
                    }
                    
                    meta.setLore(lore);
                    item = item.clone();
                    item.setItemMeta(meta);
                }
            }
            
            menu.addItem(i + 1, item);
            if (orbiter.getOrbiters().size() == 0) {
                menu.addMenuClickHandler(i + 1, (a, b, c, d, e) -> false);
            } else {
                menu.addMenuClickHandler(i + 1, (p1, slot, item1, action, a) -> {
                    open(p1, orbiter,exitHandler, true);
                    return false;
                });
            }
        }
        
        menu.open(p);

    }
    
}
