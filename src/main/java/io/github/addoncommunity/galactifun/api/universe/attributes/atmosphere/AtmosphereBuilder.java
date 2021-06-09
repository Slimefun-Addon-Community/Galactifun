package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.World;

import org.apache.commons.lang.Validate;

/**
 * Utility class for making atmospheres
 * 
 * @author Mooy1
 */
public final class AtmosphereBuilder {

    private boolean weatherCycle;
    private boolean storming;
    private boolean thundering;
    private boolean flammable;
    private World.Environment environment = World.Environment.NORMAL;
    private AtmosphericEffect[] effects = new AtmosphericEffect[0];
    private final Map<Gas, Double> composition = new EnumMap<>(Gas.class);

    public AtmosphereBuilder setNether() {
        this.environment = World.Environment.NETHER;
        return this;
    }

    public AtmosphereBuilder setEnd() {
        this.environment = World.Environment.THE_END;
        return this;
    }

    public AtmosphereBuilder addEffects(@Nonnull AtmosphericEffect... effects) {
        Validate.notNull(effects);
        this.effects = effects;
        return this;
    }

    public AtmosphereBuilder add(Gas gas, double percentage) {
        Validate.isTrue(percentage > 0 && percentage <= 100);
        this.composition.put(gas, percentage);
        return this;
    }

    public AtmosphereBuilder enableWeather() {
        this.weatherCycle = true;
        return this;
    }

    public AtmosphereBuilder addStorm() {
        this.storming = true;
        return this;
    }

    public AtmosphereBuilder addThunder() {
        this.thundering = true;
        return this;
    }

    public AtmosphereBuilder enableFire() {
        this.flammable = true;
        return this;
    }
    
    @Nonnull
    public Atmosphere build() {
        double percent = 0;
        
        for (Double decimal : this.composition.values()) {
            percent += decimal;
        }

        // account for rounding and slight impression
        Validate.isTrue(percent < 101, "Percentage cannot be more than 100%!");
        
        if (percent != 0) {
            this.composition.put(Gas.OTHER, this.composition.getOrDefault(Gas.OTHER, 0.0) + 100 - percent);
        }

        return new Atmosphere(this.weatherCycle, this.storming, this.thundering,
                this.flammable, this.environment, this.composition, this.effects);
    }
    
}
