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
import io.github.addoncommunity.galactifun.api.universe.UniversalObject;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.base.BaseUniverse;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.MenuClickHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * Class for exploring the universe through ChestMenus
 *
 * @author Mooy1
 */
public final class GalacticExplorer {

    private static final int MAX_OBJECTS_PER_PAGE = 52;

    private final Map<UUID, UniversalObject> history = new HashMap<>();

    public void explore(@Nonnull Player p, @Nonnull MenuClickHandler exitHandler) {
        open(p, this.history.computeIfAbsent(p.getUniqueId(), k -> BaseUniverse.THE_UNIVERSE), exitHandler, false);
    }

    private void open(@Nonnull Player p, @Nonnull UniversalObject object, @Nonnull MenuClickHandler exitHandler, boolean history) {

        List<UniversalObject> orbiters = object.Orbiters();

        // this shouldn't happen
        if (orbiters.size() == 0) {
            open(p, BaseUniverse.THE_UNIVERSE, exitHandler, true);
            return;
        }

        // add to history
        if (history) {
            this.history.put(p.getUniqueId(), object);
        }

        // setup menu
        ChestMenu menu = new ChestMenu(Galactifun.instance(), object.name());
        menu.setEmptySlotsClickable(false);

        // back button
        menu.addItem(0, ChestMenuUtils.getBackButton(p));
        if (object.orbiting() == null) {
            menu.addMenuClickHandler(0, exitHandler);
        } else {
            menu.addMenuClickHandler(0, (p1, slot, item, action, a) -> {
                open(p1, object.orbiting(), exitHandler, true);
                return false;
            });
        }

        PlanetaryWorld current = Galactifun.worldManager().World(p.getWorld());
        boolean known = current != null;

        // objects
        for (int i = 0 ; i < Math.min(MAX_OBJECTS_PER_PAGE, orbiters.size()) ; i++) {
            UniversalObject orbiter = orbiters.get(i);
            ItemStack item = orbiter.item();

            if (known) {
                double distance = orbiter.DistanceTo(current);

                // add distance from current
                ItemMeta meta = item.getItemMeta();
                List<Component> lore = meta.lore();
                if (lore != null) {
                    lore.remove(lore.size() - 1);

                    if (distance > 0) {
                        lore.add(Component.text("Distance: " + (distance < 1
                                ? LorePreset.format(distance * Util.KM_PER_LY) + " Kilometers"
                                : distance + " Light Years")
                        ).color(NamedTextColor.GRAY));
                    } else {
                        lore.add(Component.text("You are here!").color(NamedTextColor.GRAY));
                    }

                    meta.lore(lore);
                    item = item.clone();
                    item.setItemMeta(meta);
                }
            }

            menu.addItem(i + 1, item);
            if (orbiter.Orbiters().size() == 0) {
                menu.addMenuClickHandler(i + 1, (a, b, c, d, e) -> false);
            } else {
                menu.addMenuClickHandler(i + 1, (p1, slot, item1, action, a) -> {
                    open(p1, orbiter, exitHandler, true);
                    return false;
                });
            }
        }

        menu.open(p);

    }

}
