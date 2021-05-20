package io.github.addoncommunity.galactifun.api.universe.attributes;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.util.Util;
import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;

/**
 * Represents an orbit of a celestial object
 * 
 * @author Mooy1
 */
public final class Orbit {

    /**
     * An orbit of 0, should only be used in special cases
     */
    public static final Orbit ZERO = new Orbit(0);
    
    private static long totalDays;

    static {
        Galactifun.inst().scheduleRepeatingSync(() -> totalDays++, 24000);
    }
    
    @Nonnull
    public static Orbit kilometers(double kilometers) {
        return new Orbit(kilometers / Util.KM_PER_LY);
    }

    @Nonnull
    public static Orbit lightYears(double lightYears) {
        return new Orbit(lightYears);
    }

    @Nonnull
    public static Orbit kilometers(double min, double max, long days) {
        return new Orbit(min / Util.KM_PER_LY, max / Util.KM_PER_LY, days);
    }

    @Nonnull
    public static Orbit lightYears(double min, double max, long days) {
        return new Orbit(min, max, days);
    }
    
    private final double min;
    private final double dev;
    private final long days;
    
    private Orbit(double min, double max, long days) {
        Validate.isTrue(max >= 0, "Orbits must be positive!");
        Validate.isTrue(max > min, "Max orbit must be greater than min orbit!");
        Validate.isTrue(days > 0, "Variable orbits must last greater than 0 days!");
        
        this.min = min;
        // double the days so that distance is smooth over time
        this.days = days << 1;
        // deviation divided by number of days
        this.dev = max - min / days;
    }
    
    private Orbit(double distance) {
        Validate.isTrue(distance >= 0, "Orbits must be positive!");
        
        this.min = distance;
        this.days = 0;
        this.dev = 0;
    }
    
    public double getCurrentDistance() {
        if (this.days == 0) {
            return this.min;
        } else {
            return this.min + this.dev * (totalDays % this.days);
        }
    }
    
}
