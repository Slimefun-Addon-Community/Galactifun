package io.github.addoncommunity.galactifun.api.attributes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenerationType {

    public static final GenerationType HILLY_CAVES = new GenerationType(
            40, 35, 8, 0.01, .5, .5, .375
    );
    public static final GenerationType FLAT_NO_CAVE = new GenerationType(
            20, 45, 8,0.01, .5, .5, 0
    );

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
    
}
