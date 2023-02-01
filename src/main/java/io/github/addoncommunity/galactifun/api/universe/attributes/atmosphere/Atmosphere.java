package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.Getter;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuitProfile;
import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuitStat;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.RandomizedSet;

/**
 * An atmosphere of a celestial object, use {@link AtmosphereBuilder} to create
 *
 * @author Mooy1
 */
@Getter
@ParametersAreNonnullByDefault
public final class Atmosphere {

    public static final Atmosphere NONE = new AtmosphereBuilder()
            .setEnd()
            .setPressure(0)
            .addEffect(AtmosphericEffect.COLD, 3)
            .build();
    private static final double EARTH_CARBON_DIOXIDE = 0.0415;
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
    /**
     * Used for getting a gas proportionally to the composition
     */
    private final RandomizedSet<Gas> weightedCompositionSet = new RandomizedSet<>();

    // builder's constructor
    public Atmosphere(boolean weatherEnabled, boolean storming, boolean thundering,
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
        this.growthAttempts = (int) (this.pressurizedCompositionOf(Gas.CARBON_DIOXIDE) / EARTH_CARBON_DIOXIDE);
        for (Map.Entry<Gas, Double> entry : this.composition.entrySet()) {
            if (entry.getKey().item() != null) {
                this.weightedCompositionSet.add(entry.getKey(), entry.getValue().floatValue());
            }
        }
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
        for (Map.Entry<AtmosphericEffect, Integer> entry : this.effects.entrySet()) {
            AtmosphericEffect effect = entry.getKey();

            int protection = Galactifun.protectionManager().protectionAt(player.getLocation(), effect);
            SpaceSuitStat stat = effect.stat();
            if (stat != null) {
                protection += SpaceSuitProfile.get(player).getStat(stat);
            }

            effect.apply(player, entry.getValue() - protection);
        }
    }

    public double compositionOf(@Nonnull Gas gas) {
        return this.composition.getOrDefault(gas, 0.0);
    }

    public double pressurizedCompositionOf(@Nonnull Gas gas) {
        return compositionOf(gas) * this.pressure;
    }

    public boolean requiresOxygenTank() {
        return compositionOf(Gas.OXYGEN) < 16.0;
    }

    public AtmosphereBuilder toBuilder() {
        AtmosphereBuilder builder = new AtmosphereBuilder();
        switch (this.environment) {
            case NETHER -> builder.setNether();
            case THE_END -> builder.setEnd();
        }

        for (Map.Entry<AtmosphericEffect, Integer> effect : this.effects.entrySet()) {
            builder.addEffect(effect.getKey(), effect.getValue());
        }
        for (Map.Entry<Gas, Double> gas : this.composition.entrySet()) {
            builder.add(gas.getKey(), gas.getValue());
        }

        if (this.storming) builder.addStorm();
        if (this.thundering) builder.addThunder();
        if (this.weatherEnabled) builder.enableWeather();

        return builder;
    }

}
