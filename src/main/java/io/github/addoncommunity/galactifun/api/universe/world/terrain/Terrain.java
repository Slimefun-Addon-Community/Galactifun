package io.github.addoncommunity.galactifun.api.universe.world.terrain;

import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.world.terrain.features.TerrainFeature;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Defines the terrain of a celestial world, default implementation
 * 
 * @author Mooy1
 * @author Seggan
 * 
 */
public abstract class Terrain extends AbstractTerrain {

    public static final Terrain HILLY_CAVERNS = new Terrain( "Hilly Caverns",
            40, 8, 0.01, .5, .5, TerrainFeature.CAVERNS
    );
    public static final Terrain SMOOTH = new Terrain( "Smooth",
            15, 8,0.01, .5, .5
    );

    /**
     * Maximum y deviation
     */
    protected final double maxDeviation;

    /**
     * Octave generator octaves
     */
    protected final int octaves;

    /**
     * Octave generator scale
     */
    protected final double scale;

    /**
     * noise amplitude
     */
    protected final double amplitude;
    
    /**
     * noise frequency
     */
    protected final double frequency;


    public Terrain(@Nonnull String name, int maxDeviation, int octaves, double scale, double amplitude,
                   double frequency) {
        super(name);
        this.maxDeviation = maxDeviation;
        this.octaves = octaves;
        this.scale = scale;
        this.amplitude = amplitude;
        this.frequency = frequency;
    }
    
    @Override
    protected void generateChunk(@Nonnull CelestialWorld celestialWorld, int chunkX, int chunkZ, @Nonnull Random random,
                                 @Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull World world) {
        
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, this.octaves);
        generator.setScale(this.scale);
        
        int height;
        int realX;
        int realZ;
        int x;
        int y;
        int z;

        for (x = 0, realX = chunkX << 4; x < 16; x++, realX++) {
            for (z = 0, realZ = chunkZ << 4; z < 16; z++, realZ++) {
                
                // find max height
                height = (int) Math.floor(celestialWorld.getAvgHeight() + this.maxDeviation * 
                        generator.noise(realX, realZ, this.frequency, this.amplitude, true)
                );
                
                // features
                for (TerrainFeature feature : this.features) {
                    feature.generate(generator, chunk, realX, realZ, x, z, height);
                }
                
                // y = 0, add bedrock and biome
                chunk.setBlock(x, 0, z, Material.BEDROCK);
                celestialWorld.generateBiome(grid, x, 0, z);
                
                // y = 1 to height, generate and add biome
                for (y = 1 ; y <= height ; y++) {
                    if (chunk.getType(x, y, z) == Material.AIR) {
                        chunk.setBlock(x, y, z, celestialWorld.generate(random, , x, y, z, height, ));
                    }
                    celestialWorld.generateBiome(grid, x, y, z);
                }

                // y = height to 256, just add biome
                for (; y < 256 ; y++) {
                    celestialWorld.generateBiome(grid, x, y, z);
                }
            }
        }
    }
    
    protected abstract void generate(@Nonnull Chunk chunk, @Nonnull ChunkGenerator.BiomeGrid grid, int x, int y, 
                                     @Nonnull CelestialWorld world, @Nonnull SimplexOctaveGenerator generator);

}
