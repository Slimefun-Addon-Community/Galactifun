package io.github.addoncommunity.galactifun.api.universe.attributes.terrain.features;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

/**
 * Represents something that can add features to terrains
 * 
 * @author Mooy1
 *
 */
public interface TerrainFeature {

    TerrainFeature CAVERNS = new CaveFeature(.3, .5, .3, 10);

    /**
     * Generate the feature in the chunk, x and z are the cords within the chunk.
     */
    void generate(SimplexOctaveGenerator generator, ChunkGenerator.ChunkData chunk, int realX, int realZ, int x, int z, int height);
    
}
