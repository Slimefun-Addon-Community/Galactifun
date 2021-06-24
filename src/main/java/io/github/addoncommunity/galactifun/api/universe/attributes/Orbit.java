package io.github.addoncommunity.galactifun.api.universe.attributes;

import javax.annotation.Nonnull;

import io.github.addoncommunity.galactifun.util.Util;
import org.apache.commons.lang.Validate;

/**
 * Represents an orbit of a celestial object
 *
 * @author Mooy1
 */
public final class Orbit {

    /**
     * An orbit of 0, should only be used in special cases
     */
    public static final Orbit NONE = new Orbit(0, 0);

    private static final long EARTH_YEAR = 35;

    private final long year;

    /**
     * @param kilometers the orbital distance
     * @param year the time it takes to orbit in earth years
     */
    @Nonnull
    public static Orbit kilometers(double kilometers, double year) {
        return new Orbit(kilometers / Util.KM_PER_LY, year);
    }

    /**
     * @param kilometers the orbital distance
     * @param days the time it takes to orbit in earth days
     */
    @Nonnull
    public static Orbit kilometers(double kilometers, long days) {
        return new Orbit(kilometers / Util.KM_PER_LY,  365D / days);
    }

    @Nonnull
    public static Orbit lightYears(double lightYears, double year) {
        return new Orbit(lightYears, year);
    }

    private final double distance;

    private Orbit(double distance, double year) {
        Validate.isTrue(distance >= 0, "Orbits must be positive!");

        this.distance = distance;
        this.year = (long) (EARTH_YEAR * year) * 1200000;
    }

    public double getCurrentDistance() {
        return this.distance;
    }

    public double getOrbitPos() {
        if (this.year == 0) return 0;
        return ((System.currentTimeMillis() % this.year) * Math.PI * 2) / this.year;
    }
    
}
