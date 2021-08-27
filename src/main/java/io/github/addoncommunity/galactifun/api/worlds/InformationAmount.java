package io.github.addoncommunity.galactifun.api.worlds;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * The amount of the information about a {@link PlanetaryWorld} known by a player
 */
public enum InformationAmount {

    /**
     * Just the bare minimum: distance
     */
    NONE {
        @Override
        public void addLore(@Nonnull List<Component> lore, @Nonnull PlanetaryWorld world) {
            // do nothing
        }
    },

    /**
     * Effects, daylight cycle, and atmospheric pressure
     */
    BASIC {
        @Override
        public void addLore(@Nonnull List<Component> lore, @Nonnull PlanetaryWorld world) {
            if (!world.atmosphere().effects().isEmpty()) {
                lore.add(Component.empty());
                lore.add(Component.text("Effects:").color(NamedTextColor.GRAY));
                for (Map.Entry<AtmosphericEffect, Integer> effect : world.atmosphere().effects().entrySet()) {
                    lore.add(Component.text(effect.getKey().toString())
                            .color(NamedTextColor.RED)
                            .append(Component.text(": "))
                            .append(Component.text(effect.getValue()))
                    );
                }
            }

            lore.add(Component.empty());
            lore.add(Component.text("Day/Night Cycle: ")
                    .color(NamedTextColor.GRAY)
                    .append(Component.text(world.dayCycle().description()))
            );

            lore.add(Component.empty());
            lore.add(Component.text("Atmospheric Pressure: ")
                    .color(NamedTextColor.GRAY)
                    .append(Component.text(formatter.format(world.atmosphere().pressure())))
                    .append(Component.text(" atm"))
            );
        }
    },

    /**
     * Atmospheric composition, gravity, and anything else we might add in the future
     */
    ADVANCED {
        @Override
        public void addLore(@Nonnull List<Component> lore, @Nonnull PlanetaryWorld world) {
            BASIC.addLore(lore, world);

            // if pressure is not 0, accounting for double imprecision
            if (Math.abs(world.atmosphere().pressure()) > 1e-6) {
                lore.add(Component.empty());
                lore.add(Component.text("Atmospheric Composition:").color(NamedTextColor.GRAY));

                // sort them by amount
                LinkedHashMap<Gas, Double> gases = new LinkedHashMap<>(world.atmosphere().composition());
                InformationAmount.orderByValue(gases, Comparator.reverseOrder());
                for (Map.Entry<Gas, Double> gas : gases.entrySet()) {
                    lore.add(Component.text(ChatUtils.humanize(gas.getKey().name()))
                            .color(NamedTextColor.GREEN)
                            .append(Component.text(": "))
                            .append(Component.text(formatter.format(gas.getValue())))
                            .append(Component.text('%'))
                    );
                }
            }

            lore.add(Component.empty());
            lore.add(Component.text("Gravity: ")
                    .color(NamedTextColor.GRAY)
                    .append(Component.text(formatter.format(world.gravity().percent())))
                    .append(Component.text(" g"))
            );
        }
    };

    private static final DecimalFormat formatter = new DecimalFormat("0.###");

    public abstract void addLore(@Nonnull List<Component> lore, @Nonnull PlanetaryWorld world);

    private static <K, V> void orderByValue(LinkedHashMap<K, V> m, final Comparator<? super V> c) {
        List<Map.Entry<K, V>> entries = new ArrayList<>(m.entrySet());

        entries.sort((lhs, rhs) -> c.compare(lhs.getValue(), rhs.getValue()));

        m.clear();
        for(Map.Entry<K, V> e : entries) {
            m.put(e.getKey(), e.getValue());
        }
    }
}
