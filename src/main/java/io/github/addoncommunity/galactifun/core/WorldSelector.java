package io.github.addoncommunity.galactifun.core;

import java.util.ArrayList;
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
import io.github.addoncommunity.galactifun.base.items.knowledge.KnowledgeLevel;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * Class for exploring the universe through {@link ChestMenu}s
 *
 * @author Mooy1
 * @author Seggan
 */
public final class WorldSelector {

    private static final int MAX_OBJECTS_PER_PAGE = 52;

    private final Map<UUID, UniversalObject> history = new HashMap<>();

    @Nonnull
    private final ChestMenu.MenuClickHandler exitHandler;
    @Nonnull
    private final ItemModifier modifier;
    @Nonnull
    private final SelectHandler selectHandler;

    public WorldSelector(@Nonnull ChestMenu.MenuClickHandler exitHandler, @Nonnull ItemModifier modifier, @Nonnull SelectHandler selectHandler) {
        this.exitHandler = exitHandler;
        this.selectHandler = selectHandler;
        this.modifier = modifier;
    }

    public WorldSelector(@Nonnull ChestMenu.MenuClickHandler exitHandler) {
        this(exitHandler, (p, world, lore) -> true, (p, world) -> {
        });
    }

    public WorldSelector(@Nonnull ItemModifier modifier, @Nonnull SelectHandler selectHandler) {
        this((p, i, s, a) -> false, modifier, selectHandler);
    }

    public WorldSelector(@Nonnull SelectHandler selectHandler) {
        this((p, world, lore) -> true, selectHandler);
    }

    public void open(@Nonnull Player p) {
        open(p, this.history.computeIfAbsent(p.getUniqueId(), k -> BaseUniverse.THE_UNIVERSE), false);
    }

    private void open(@Nonnull Player p, @Nonnull UniversalObject object, boolean history) {

        List<UniversalObject> orbiters = object.orbiters();

        // this shouldn't happen
        if (orbiters.size() == 0) {
            open(p, BaseUniverse.THE_UNIVERSE, true);
            return;
        }

        // add to history
        if (history) {
            this.history.put(p.getUniqueId(), object);
        }

        // setup menu
        ChestMenu menu = new ChestMenu(object.name());
        menu.setEmptySlotsClickable(false);

        // back button
        menu.addItem(0, ChestMenuUtils.getBackButton(p));
        if (object.orbiting() == null) {
            menu.addMenuClickHandler(0, exitHandler);
        } else {
            menu.addMenuClickHandler(0, (p1, slot, item, a) -> {
                open(p1, object.orbiting(), true);
                return false;
            });
        }

        PlanetaryWorld current = Galactifun.worldManager().getWorld(p.getWorld());
        boolean known = current != null;

        int offset = 1;

        if (object instanceof PlanetaryWorld world) {
            ItemStack item = world.item();
            offset++;

            if (known) {
                double distance = world.distanceTo(current);

                // add distance from current
                ItemMeta meta = item.getItemMeta();
                List<Component> lore = meta.lore();
                if (lore != null) {
                    lore.remove(lore.size() - 1);

                    if (distance > 0) {
                        lore.add(Component.text("Distance: " + (distance < .5
                                ? "%.3f Kilometers".formatted(distance * Util.KM_PER_LY)
                                : distance + " Light Years")
                        ).color(NamedTextColor.GRAY));
                    } else {
                        lore.add(Component.text("You are here!").color(NamedTextColor.GRAY));
                    }

                    KnowledgeLevel.get(p, world).addLore(lore, world);

                    if (modifier.modifyItem(p, world, lore)) {
                        meta.lore(lore);
                        item = item.clone();
                        item.setItemMeta(meta);
                    }
                }
            }

            menu.addItem(1, item, (player, i, itemStack, clickAction) -> {
                selectHandler.onSelect(player, world);
                return false;
            });
        }

        // objects
        for (int i = 0; i < Math.min(MAX_OBJECTS_PER_PAGE, orbiters.size()); i++) {
            UniversalObject orbiter = orbiters.get(i);
            if (orbiter instanceof PlanetaryWorld planetaryWorld && !planetaryWorld.enabled()) {
                offset--;
                continue;
            }

            ItemStack item = orbiter.item();

            if (known) {
                double distance = orbiter.distanceTo(current);

                // add distance from current
                ItemMeta meta = item.getItemMeta();
                List<Component> lore = meta.lore();
                if (lore != null) {
                    lore.remove(lore.size() - 1);

                    if (distance > 0) {
                        lore.add(Component.text("Distance: " + Util.formatDistance(distance))
                                .color(NamedTextColor.GRAY));
                    } else {
                        lore.add(Component.text("You are here!").color(NamedTextColor.GRAY));
                    }

                    if (orbiter instanceof PlanetaryWorld planetaryWorld) {
                        KnowledgeLevel.get(p, planetaryWorld).addLore(lore, planetaryWorld);
                    }

                    if (!modifier.modifyItem(p, orbiter, lore)) continue;

                    meta.lore(lore);
                    item = item.clone();
                    item.setItemMeta(meta);
                }
            }
            if (orbiter instanceof PlanetaryWorld || showObject(p, orbiter)) {
                menu.addItem(i + offset, item);
                if (orbiter.orbiters().size() == 0) {
                    menu.addMenuClickHandler(i + offset, (clicker, i1, s, a) -> {
                        // 99% true
                        if (orbiter instanceof PlanetaryWorld planetaryWorld) {
                            selectHandler.onSelect(clicker, planetaryWorld);
                        }
                        return false;
                    });
                } else {
                    menu.addMenuClickHandler(i + offset, (p1, slot, item1, a) -> {
                        open(p1, orbiter, true);
                        return false;
                    });
                }
            } else {
                offset--;
            }
        }

        menu.open(p);

    }

    private boolean showObject(Player p, UniversalObject object) {
        for (UniversalObject o : object.orbiters()) {
            if (o instanceof PlanetaryWorld world && world.enabled() && modifier.modifyItem(p, world, new ArrayList<>())) {
                return true;
            } else if (showObject(p, o) && modifier.modifyItem(p, o, new ArrayList<>())) {
                return true;
            }
        }
        return false;
    }

    @FunctionalInterface
    public interface SelectHandler {

        /**
         * Called when a player selects a world
         *
         * @param p the {@link Player} selecting the world
         * @param world the {@link PlanetaryWorld} selected
         */
        void onSelect(@Nonnull Player p, @Nonnull PlanetaryWorld world);

    }

    @FunctionalInterface
    public interface ItemModifier {

        /**
         * Called when the {@link WorldSelector} decides to display a {@link UniversalObject}
         *
         * @param p the {@link Player} that the {@link WorldSelector} is open to
         * @param object the {@link UniversalObject} that the item represents
         * @param lore the lore of the item, fresh for modification
         *
         * @return whether the item should be displayed
         */
        boolean modifyItem(@Nonnull Player p, @Nonnull UniversalObject object, @Nonnull List<Component> lore);

    }

}
