package io.github.addoncommunity.galactifun.api.universe.attributes.terrainfeatures;

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
public class Cave implements TerrainFeature {

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
            if (density > this.densityThreshold) {
                chunk.setBlock(x, y, z, Material.CAVE_AIR);
            }
        }
    }

}
