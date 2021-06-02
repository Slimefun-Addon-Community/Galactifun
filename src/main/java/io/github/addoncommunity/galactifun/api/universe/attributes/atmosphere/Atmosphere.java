package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * An atmosphere of a celestial object, use {@link AtmosphereBuilder} to create
 *
 * @author Mooy1
 *
 */
@Getter
public final class Atmosphere {
    
    private static final double EARTH_CARBON_DIOXIDE = 0.0415;

    public static final Atmosphere NONE = new AtmosphereBuilder().setEnd().build();
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
    private final World.Environment environment;
    private final AtmosphericEffect[] effects;
    private final Map<Gas, Double> composition = new EnumMap<>(Gas.class);

    // builder's constructor
    Atmosphere(boolean weatherEnabled, boolean storming, boolean thundering, boolean flammable,
               @Nonnull World.Environment environment, Map<Gas, Double> composition,
               @Nonnull AtmosphericEffect[] effects) {
        
        this.weatherEnabled = weatherEnabled;
        this.environment = environment;
        this.thundering = thundering;
        this.storming = storming;
        this.flammable = flammable;
        this.composition.putAll(composition);
        this.effects = effects;
        this.growthAttempts = (int) (composition.getOrDefault(Gas.CARBON_DIOXIDE, 0.0) / EARTH_CARBON_DIOXIDE);
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
        for (AtmosphericEffect effect : this.effects) {
            effect.apply(player);
        }
    }

    public double getComposition(Gas gas) {
        return this.composition.getOrDefault(gas, 0.0);
    }
    
}
