package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import lombok.Getter;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

/**
 * An atmosphere of a celestial object, use {@link AtmosphereBuilder} to create
 *
 * @author Mooy1
 *
 */
public final class Atmosphere {

    public static final Atmosphere EARTH_LIKE = new AtmosphereBuilder().enableWeather()
        .addNitrogen(77.084) // subtracted 1 to allow water to fit in
        .addOxygen(20.946)
        .addComponent(AtmosphericComponent.WATER, 0.95)
        .addComponent(AtmosphericComponent.ARGON, 0.934)
        .addCarbonDioxide(0.0415)
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
    // BigDecimal for precision
    @Getter
    private final Map<AtmosphericComponent, BigDecimal> composition = new EnumMap<>(AtmosphericComponent.class);
    
    // builder's constructor
    Atmosphere(boolean weatherCycle, boolean storming, boolean thundering, boolean flammable,
               @Nonnull World.Environment environment, Map<AtmosphericComponent, BigDecimal> composition,
               @Nonnull AtmosphericEffect[] effects) {

        this.weatherCycle = weatherCycle;
        this.environment = environment;
        this.thundering = thundering;
        this.storming = storming;
        this.flammable = flammable;
        this.composition.putAll(composition);
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

    public BigDecimal getOxygenPercentage() {
        return composition.getOrDefault(AtmosphericComponent.OXYGEN, BigDecimal.ZERO);
    }

    public BigDecimal getCarbonDioxidePercentage() {
        return composition.getOrDefault(AtmosphericComponent.CARBON_DIOXIDE, BigDecimal.ZERO);
    }
    
}
