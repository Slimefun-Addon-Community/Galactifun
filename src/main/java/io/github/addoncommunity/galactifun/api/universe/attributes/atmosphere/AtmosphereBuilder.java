package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.World;

/**
 * Utility class for making atmospheres
 *
 * @author Mooy1
 */
@Nonnull
public final class AtmosphereBuilder {

    private final Map<AtmosphericEffect, Integer> effects = new HashMap<>();
    private final Map<Gas, Double> composition = new EnumMap<>(Gas.class);
    private boolean weatherCycle;
    private boolean storming;
    private boolean thundering;
    private World.Environment environment = World.Environment.NORMAL;
    private double pressure = 1;

    public AtmosphereBuilder setNether() {
        this.environment = World.Environment.NETHER;
        return this;
    }

    public AtmosphereBuilder setEnd() {
        this.environment = World.Environment.THE_END;
        return this;
    }

    public AtmosphereBuilder addEffect(@Nonnull AtmosphericEffect effect, int level) {
        if (level > 0) {
            this.effects.put(effect, level);
        }
        return this;
    }

    public AtmosphereBuilder add(@Nonnull Gas gas, double percentage) {
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

    public AtmosphereBuilder setPressure(double pressureInAtm) {
        Validate.isTrue(pressureInAtm >= 0, "pressureInAtm is negative!");
        this.pressure = pressureInAtm;
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
            this.composition.put(Gas.OTHER, this.composition.getOrDefault(Gas.OTHER, 0.0) + (100 - percent));
        }

        return new Atmosphere(this.weatherCycle, this.storming, this.thundering,
                this.environment, this.composition, this.pressure, this.effects);
    }

}
