package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.api.universe.TheUniverse;
import io.github.addoncommunity.galactifun.api.universe.UniversalObject;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class for exploring the universe through ChestMenus
 * 
 * @author Mooy1
 */
public final class UniversalExplorer {
    
    private static final Map<UUID, UniversalObject<?>> HISTORY = new HashMap<>();
    
    public static void explore(@Nonnull Player p) {
        open(p, HISTORY.computeIfAbsent(p.getUniqueId(), k -> TheUniverse.getInstance()), false);
    }
    
    private static void open(@Nonnull Player p, @Nonnull UniversalObject<?> object, boolean history) {

        List<UniversalObject<?>> orbiters = object.getOrbiters();

        // this shouldn't happen
        if (orbiters.size() == 0) {
            open(p, TheUniverse.getInstance(), true);
            return;
        }
        
        // add to history
        if (history) {
            HISTORY.put(p.getUniqueId(), object);
        }

        // setup menu
        ChestMenu menu = new ChestMenu(object.getName());
        menu.setEmptySlotsClickable(false);

        // back button
        if (object.getOrbiting() == null) {
            menu.addMenuClickHandler(1, ChestMenuUtils.getEmptyClickHandler());
        } else {
            menu.addMenuClickHandler(1, (p1, slot, item, action) -> {
                open(p1, object.getOrbiting(), true);
                return false;
            });
        }

        // objects
        for (int i = 1 ; i < Math.min(54, orbiters.size()); i++) {
            UniversalObject<?> orbiter = orbiters.get(i);
            menu.addItem(i, orbiter.getDistanceItem(object));
            if (orbiter.getOrbiters().size() == 0) {
                menu.addMenuClickHandler(i, ChestMenuUtils.getEmptyClickHandler());
            } else {
                menu.addMenuClickHandler(i, (p1, slot, item, action) -> {
                    open(p1, orbiter, true);
                    return false;
                });
            }
        }
        
        menu.open(p);

    }
    
}
