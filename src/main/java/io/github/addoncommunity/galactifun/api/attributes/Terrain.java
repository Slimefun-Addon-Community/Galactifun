package io.github.addoncommunity.galactifun.api.attributes;

import lombok.AllArgsConstructor;

/**
 * Defines the terrain of a celestial object
 * 
 * @author Mooy1
 * 
 */
@AllArgsConstructor
public final class Terrain {

    public static final Terrain HILLY_CAVES = new Terrain(
            40, 35, 8, 0.01, .5, .5, .16, .3, .3
    );
    
    public static final Terrain FLAT_NO_CAVE = new Terrain(
            20, 45, 8,0.01, .5, .5
    );

    public Terrain(int maxDeviation, int minHeight, int octaves, double scale, double amplitude, double frequency) {
        this(maxDeviation, minHeight, octaves, scale, amplitude, frequency, 0, 0, 0);
    }
    
    /**
     * Maximum y deviation
     */
    public final int maxDeviation;

    /**
     * Minimum y value
     */
    public final int minHeight;

    /**
     * Octave generator octaves
     */
    public final int octaves;

    /**
     * Octave generator scale
     */
    public final double scale;

    /**
     * noise amplitude
     */
    public final double amplitude;
    
    /**
     * noise frequency
     */
    public final double frequency;
    
    /**
     * Ratio of caves from 0 - 1
     */
    public final double caveRatio;

    /**
     * cave noise amplitude
     */
    public final double caveAmplitude;

    /**
     * cave noise frequency
     */
    public final double caveFrequency;
    
}
