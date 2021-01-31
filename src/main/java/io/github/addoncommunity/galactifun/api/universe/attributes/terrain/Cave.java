package io.github.addoncommunity.galactifun.api.universe.attributes.terrain;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

/**
 * A cave.
 * 
 * @author Seggan
 * @author Mooy1
 */
public class Cave implements TerrainFeature {

    /**
     * Ratio of caves from 0 - 1, will be spread to [-1, 1]
     */
    private final double ratio;

    /**
     * cave noise amplitude
     */
    private final double amplitude;

    /**
     * cave noise frequency
     */
    private final double frequency;
    
    public Cave(double ratio, double amplitude, double frequency) {
        this.ratio = ratio * 2 - 1;
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    @Override
    public void generate(SimplexOctaveGenerator generator, ChunkGenerator.ChunkData chunk, int realX, int realZ, int x, int z, int height) {
        for (int y = 1 ; y < height ; y++) {
            double density = generator.noise(
                    realX,
                    y,
                    realZ,
                    this.frequency,
                    this.amplitude,
                    true
            );

            // Choose a narrow selection of blocks
            if (density <= this.ratio) {
                chunk.setBlock(x, y, z, Material.CAVE_AIR);
            }
        }
    }

}
