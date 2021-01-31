package io.github.addoncommunity.galactifun.api.universe.attributes;

import io.github.addoncommunity.galactifun.api.universe.attributes.terrainfeatures.TerrainFeature;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Defines the terrain of a celestial object
 * 
 * @author Mooy1
 * 
 */
public final class Terrain {

    public static final Terrain HILLY_CAVERNS = new Terrain(
            40, 35, 8, 0.01, .5, .5, TerrainFeature.CAVERNS
    );
    
    public static final Terrain SMOOTH = new Terrain(
            20, 45, 8,0.01, .5, .5
    );
    
    /**
     * Maximum y deviation
     */
    private final int maxDeviation;

    /**
     * Minimum y value
     */
    private final int minHeight;

    /**
     * Octave generator octaves
     */
    private final int octaves;

    /**
     * Octave generator scale
     */
    private final double scale;

    /**
     * noise amplitude
     */
    private final double amplitude;
    
    /**
     * noise frequency
     */
    private final double frequency;

    /**
     * Features
     */
    @Nonnull private final TerrainFeature[] features;

    public Terrain(int maxDeviation, int minHeight, int octaves, double scale, double amplitude, double frequency, @Nonnull TerrainFeature... features) {
        this.maxDeviation = maxDeviation;
        this.minHeight = minHeight;
        this.octaves = octaves;
        this.scale = scale;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.features = features;
    }
    
    public void generateChunkData(@Nonnull World world, @Nonnull Random random, int chunkX, int chunkZ,
                                  @Nonnull BiomeSupplier supplier, @Nonnull MaterialSupplier material,
                                  @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull ChunkGenerator.ChunkData chunk) {
        
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, this.octaves);
        generator.setScale(this.scale);

        int height;
        int startX = chunkX << 4;
        int startZ = chunkZ << 4;
        
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlock(x, 0, z, Material.BEDROCK);

                int realX = startX + x;
                int realZ = startZ + z;
                
                // find max height
                height = (int) (this.minHeight + this.maxDeviation * (1 + generator.noise(
                        realX,
                        realZ,
                        this.frequency,
                        this.amplitude,
                        true)
                ));

                // features
                for (TerrainFeature feature : this.features) {
                    feature.generate(generator, chunk, realX, realZ, x, z, height);
                }
                
                // generate the rest
                for (int y = 1 ; y < height ; y++) {
                    if (chunk.getType(x, y, z) == Material.AIR) {
                        chunk.setBlock(x, y, z, material.get(random, height, x, y, z));
                    }
                }

                // set biome
                Biome biome = supplier.get(random, chunkX, chunkZ);
                for (int y = 0 ; y < 256 ; y++) {
                    grid.setBiome(x, y, z, biome);
                }
            }
        }
    }
    
    @FunctionalInterface
    public interface MaterialSupplier {
        @Nonnull Material get(@Nonnull Random random, int top, int x, int y, int z);
    }

    @FunctionalInterface
    public interface BiomeSupplier {
        @Nonnull Biome get(@Nonnull Random random, int chunkX, int chunkZ);
    }
    
}
