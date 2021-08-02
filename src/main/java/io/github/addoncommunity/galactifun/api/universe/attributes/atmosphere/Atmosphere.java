package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.Galactifun;

/**
 * An atmosphere of a celestial object, use {@link AtmosphereBuilder} to create
 *
 * @author Mooy1
 */
@Getter
public final class Atmosphere {

    private static final double EARTH_CARBON_DIOXIDE = 0.0415;

    public static final Atmosphere NONE = new AtmosphereBuilder()
            .setEnd()
            .setPressure(0)
            .addEffect(AtmosphericEffect.COLD, 5)
            .build();

    public static final Atmosphere EARTH_LIKE = new AtmosphereBuilder().enableWeather()
            .add(Gas.NITROGEN, 77.084) // subtracted 1 to allow water to fit in
            .add(Gas.OXYGEN, 20.946)
            .add(Gas.WATER, 0.95)
            .add(Gas.ARGON, 0.934)
            .add(Gas.CARBON_DIOXIDE, EARTH_CARBON_DIOXIDE)
            .build();

    private final boolean weatherEnabled;
    private final boolean storming;
    private final boolean thundering;
    private final boolean flammable;
    private final int growthAttempts;
    private final double pressure;
    private final World.Environment environment;
    private final Map<AtmosphericEffect, Integer> effects;
    private final Map<Gas, Double> composition = new EnumMap<>(Gas.class);

    // builder's constructor
    Atmosphere(boolean weatherEnabled, boolean storming, boolean thundering,
               @Nonnull World.Environment environment, @Nonnull Map<Gas, Double> composition,
               double pressure, @Nonnull Map<AtmosphericEffect, Integer> effects) {

        this.weatherEnabled = weatherEnabled;
        this.environment = environment;
        this.thundering = thundering;
        this.storming = storming;
        this.pressure = pressure;
        this.effects = effects;
        this.composition.putAll(composition);

        // calculated values
        this.flammable = composition.getOrDefault(Gas.OXYGEN, 0.0) > 5;
        this.growthAttempts = (int) (this.AdjustedComposition(Gas.CARBON_DIOXIDE) / EARTH_CARBON_DIOXIDE);
    }

    public void applyEffects(@Nonnull World world) {
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, this.weatherEnabled);
        world.setStorm(this.storming);
        if (this.storming) {
            world.setWeatherDuration(Integer.MAX_VALUE);
        }
        world.setThundering(this.thundering);
        if (this.thundering) {
            world.setThunderDuration(Integer.MAX_VALUE);
        }
        world.setGameRule(GameRule.DO_FIRE_TICK, this.flammable);
    }

    public void applyEffects(@Nonnull Player player) {
        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            for (Map.Entry<AtmosphericEffect, Integer> entry : this.effects.entrySet()) {
                AtmosphericEffect effect = entry.getKey();

                int protection = 0; // TODO replace with space suit protection
                protection += Galactifun.protectionManager().protectionAt(player.getLocation(), effect);

                int level = Math.max(0, entry.getValue() - protection);
                if (level > 0) {
                    player.sendMessage(ChatColor.RED + "You have been exposed to deadly " + effect + "!");
                    effect.applier().accept(player, level);
                }
            }
        }
    }

    public double Composition(@Nonnull Gas gas) {
        return this.composition.getOrDefault(gas, 0.0);
    }

    public double AdjustedComposition(@Nonnull Gas gas) {
        return Composition(gas) * this.pressure;
    }

}
