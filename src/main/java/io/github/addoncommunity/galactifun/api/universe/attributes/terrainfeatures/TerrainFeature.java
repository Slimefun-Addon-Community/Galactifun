package io.github.addoncommunity.galactifun.api.universe.attributes.terrainfeatures;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

/**
 * Represents something that can add features to terrains
 * 
 * @author Mooy1
 *
 */
public interface TerrainFeature {

    TerrainFeature CAVERNS = new Cave(.3, .5, .3, 65);

    /**
     * Generate the feature in the chunk, x and z are the cords within the chunk.
     */
    void generate(SimplexOctaveGenerator generator, ChunkGenerator.ChunkData chunk, int realX, int realZ, int x, int z, int height);
    
}
