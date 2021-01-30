package io.github.addoncommunity.galactifun.api.universe.attributes;

import lombok.AllArgsConstructor;
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
     * Ratio of caves from 0 - 1
     */
    private final double caveRatio;

    /**
     * cave noise amplitude
     */
    private final double caveAmplitude;

    /**
     * cave noise frequency
     */
    private final double caveFrequency;

    public void generateChunkData(@Nonnull World world, @Nonnull Random random, int chunkX, int chunkZ,
                                  @Nonnull BiomeSupplier supplier, @Nonnull MaterialSupplier material,
                                  @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull ChunkGenerator.ChunkData chunk) {
        
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, this.octaves);
        generator.setScale(this.scale);

        int height;
        int startX = chunkX << 4;
        int startZ = chunkZ << 4;

        if (this.caveRatio == 0) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 0, z, Material.BEDROCK);

                    // find max height
                    height = (int) (this.minHeight + this.maxDeviation * (1 + generator.noise(
                            startX + x,
                            startZ + z,
                            this.frequency,
                            this.amplitude,
                            true)
                    ));

                    // generate the rest
                    for (int y = 1 ; y < height ; y++) {
                        chunk.setBlock(x, y, z, material.get(random, height, x, y, z));
                    }

                    // set biome
                    Biome biome = supplier.get(random, chunkX, chunkZ);
                    for (int y = 0 ; y < 256 ; y++) {
                        grid.setBiome(x, y, z, biome);
                    }
                }
            }
        } else {
            double caveRatio = this.caveRatio * 2;

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 0, z, Material.BEDROCK);

                    // find max height
                    height = (int) (this.minHeight + this.maxDeviation * (1 + generator.noise(
                            startX + x, startZ + z, this.frequency, this.amplitude, true)
                    ));

                    int top = height - 1;

                    // generate caves
                    for (int y = 1 ; y < height ; y++) {
                        double density = 1 + generator.noise(
                                startX + x,
                                y,
                                startZ + z,
                                this.caveFrequency,
                                this.caveAmplitude,
                                true
                        );

                        // Choose a narrow selection of blocks
                        if (density <= caveRatio) {
                            chunk.setBlock(x, y, z, Material.CAVE_AIR);
                        } else {
                            chunk.setBlock(x, y, z, material.get(random, top, x, y, z));
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
