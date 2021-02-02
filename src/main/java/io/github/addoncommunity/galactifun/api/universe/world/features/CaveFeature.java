package io.github.addoncommunity.galactifun.api.universe.world.features;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

/**
 * A cave.
 * 
 * @author Seggan
 * @author Mooy1
 *
 */
@AllArgsConstructor
public class CaveFeature implements TerrainFeature {
    
    public static final TerrainFeature CAVERNS = new CaveFeature(.3, .5, .3, 10);

    /**
     * Density threshold 0-1
     */
    private final double densityThreshold;

    /**
     * cave noise amplitude
     */
    private final double amplitude;

    /**
     * cave noise frequency
     */
    private final double frequency;

    private final int blocksBetweenSurface;

    @Override
    public void generate(SimplexOctaveGenerator generator, ChunkGenerator.ChunkData chunk, int realX, int realZ, int x, int z, int height) {
        for (int y = 1 ; y < height - this.blocksBetweenSurface ; y++) {
            double density = generator.noise(
                    realX,
                    y,
                    realZ,
                    this.frequency,
                    this.amplitude,
                    true
            );

            // Choose a narrow selection of blocks
            if (density > this.densityThreshold) {
                chunk.setBlock(x, y, z, Material.CAVE_AIR);
            }
        }
    }

}
