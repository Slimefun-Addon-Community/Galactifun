package io.github.addoncommunity.galactifun.api.universe.attributes;

import io.github.addoncommunity.galactifun.core.util.Util;
import org.apache.commons.lang.Validate;

/**
 * Represents an orbit of a celestial object
 * 
 * @author Mooy1
 */
public final class Orbit {
    
    /**
     * Minimum distance in light years
     */
    private final double min;

    /**
     * Max positive deviation divided by time
     */
    private final double dev;

    /**
     * Time in milliseconds to complete an orbit
     */
    private final long time;

    public Orbit(long minKm, long maxKm, long days) {
        this(minKm / Util.LY_TO_KM, maxKm / Util.LY_TO_KM, days);
    }
    
    public Orbit(long distanceKm) {
        this(distanceKm / Util.LY_TO_KM);
    }
    
    public Orbit(double minLy, double maxLy, long days) {
        Validate.isTrue(minLy >=0);
        Validate.isTrue(maxLy >= minLy);
        Validate.isTrue(days > 0);
        // days to millis
        this.time = days * 86400000;
        this.min = minLy;
        this.dev = (maxLy - minLy) / this.time;
    }
    
    public Orbit(double distanceLy) {
        Validate.isTrue(distanceLy >=0);
        this.min = distanceLy;
        this.dev = 0;
        this.time = 0;
    }
    
    public double getCurrentDistance() {
        if (this.time == 0) {
            return this.min;
        } else {
            return this.min + (long) (this.dev * (System.currentTimeMillis() % this.time));
        }
    }
    
}
