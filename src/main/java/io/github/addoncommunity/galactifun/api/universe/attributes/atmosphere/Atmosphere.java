package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import lombok.Getter;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

/**
 * An atmosphere of a celestial object, use {@link AtmosphereBuilder} to create
 *
 * @author Mooy1
 *
 */
public final class Atmosphere {
    
    private static final double EARTH_CARBON_DIOXIDE = 0.0415;

    public static final Atmosphere EARTH_LIKE = new AtmosphereBuilder().enableWeather()
        .addNitrogen(77.084) // subtracted 1 to allow water to fit in
        .addOxygen(20.946)
        .addComponent(AtmosphericGas.WATER, 0.95)
        .addComponent(AtmosphericGas.ARGON, 0.934)
        .addCarbonDioxide(EARTH_CARBON_DIOXIDE)
        .build();
    public static final Atmosphere NONE = new AtmosphereBuilder().build();
    
    private final boolean weatherCycle;
    private final boolean storming;
    private final boolean thundering;
    private final boolean flammable;
    
    @Nonnull
    @Getter 
    private final World.Environment environment;
    @Nonnull
    @Getter
    private final AtmosphericEffect[] effects;
    @Nonnull
    @Getter
    private final Map<AtmosphericGas, Double> composition = new EnumMap<>(AtmosphericGas.class);
    @Getter
    private final int growthAttempts;

    // builder's constructor
    Atmosphere(boolean weatherCycle, boolean storming, boolean thundering, boolean flammable,
               @Nonnull World.Environment environment, Map<AtmosphericGas, Double> composition,
               @Nonnull AtmosphericEffect[] effects) {

        this.weatherCycle = weatherCycle;
        this.environment = environment;
        this.thundering = thundering;
        this.storming = storming;
        this.flammable = flammable;
        this.composition.putAll(composition);
        this.effects = effects;
        this.growthAttempts = (int) (composition.getOrDefault(AtmosphericGas.CARBON_DIOXIDE, 0.0) / EARTH_CARBON_DIOXIDE);
    }
    
    public void applyEffects(@Nonnull World world) {
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, this.weatherCycle);
        world.setStorm(this.storming);
        if (this.storming) {
            world.setWeatherDuration(Integer.MAX_VALUE);
        }
        world.setThundering(this.thundering);
        if (this.thundering) {
            world.setThunderDuration(Integer.MAX_VALUE);
        }
        world.setGameRule(GameRule.DO_FIRE_TICK, this.flammable);
        // find a way to implement crop growth stuff, maybe check out spigots method?
    }
    
    public void applyEffects(@Nonnull Player player) {
        for (AtmosphericEffect effect : this.effects) {
            effect.apply(player);
        }
    }

    public double getOxygenPercentage() {
        return this.composition.getOrDefault(AtmosphericGas.OXYGEN, 0.0);
    }

    public double getCarbonDioxidePercentage() {
        return this.composition.getOrDefault(AtmosphericGas.CARBON_DIOXIDE, 0.0);
    }
    
}
