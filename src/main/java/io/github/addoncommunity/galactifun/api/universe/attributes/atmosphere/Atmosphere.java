package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import lombok.Getter;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * An atmosphere of a celestial object, use {@link AtmosphereBuilder} to create
 *
 * @author Mooy1
 *
 */
public final class Atmosphere {

    public static final Atmosphere EARTH_LIKE = new AtmosphereBuilder().addOxygen(21).addCarbonDioxide(.004).enableWeather().build();
    public static final Atmosphere NONE = new AtmosphereBuilder().build();

    @Getter 
    private final double oxygenPercentage;
    @Getter
    private final double carbonDioxidePercentage;
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
    
    // builder's constructor
    Atmosphere(double oxygenPercentage, double carbonDioxidePercentage, boolean weatherCycle, boolean storming, boolean thundering,
               boolean flammable, @Nonnull World.Environment environment, @Nonnull AtmosphericEffect[] effects) {
        
        this.oxygenPercentage = oxygenPercentage;
        this.weatherCycle = weatherCycle;
        this.environment = environment;
        this.carbonDioxidePercentage = carbonDioxidePercentage;
        this.thundering = thundering;
        this.storming = storming;
        this.flammable = flammable;
        this.effects = effects;
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
    
}
