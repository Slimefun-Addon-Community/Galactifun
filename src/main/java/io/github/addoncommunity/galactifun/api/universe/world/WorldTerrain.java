package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import io.github.addoncommunity.galactifun.api.universe.world.features.CaveFeature;
import io.github.addoncommunity.galactifun.api.universe.world.features.TerrainFeature;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Defines the terrain of a celestial world
 * 
 * @author Mooy1
 * 
 */
public class WorldTerrain extends Terrain {

    public static final WorldTerrain HILLY_CAVERNS = new WorldTerrain( "Hilly Caverns",
            40, 35, 8, 0.01, .5, .5, CaveFeature.CAVERNS
    );
    
    public static final WorldTerrain SMOOTH = new WorldTerrain( "Smooth",
            20, 45, 8,0.01, .5, .5
    );

    /**
     * Maximum y deviation
     */
    protected final int maxDeviation;

    /**
     * Minimum y value
     */
    protected final int minHeight;

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

    /**
     * Features
     */
    @Nonnull
    protected final TerrainFeature[] features;

    public WorldTerrain(@Nonnull String name, int maxDeviation, int minHeight, int octaves, double scale, double amplitude, double frequency, @Nonnull TerrainFeature... features) {
        super(name);
        this.maxDeviation = maxDeviation;
        this.minHeight = minHeight;
        this.octaves = octaves;
        this.scale = scale;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.features = features;
    }

    /**
     * Creates a new ChunkGenerator based on this terrain
     */
    @Nonnull
    public final ChunkGenerator createGenerator(@Nonnull CelestialWorld celestialWorld) {
        return new ChunkGenerator() {
            @Nonnull
            @Override
            public ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int chunkX, int chunkZ, @Nonnull BiomeGrid grid) {
                ChunkData chunk = createChunkData(world);
                SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, WorldTerrain.this.octaves);
                generator.setScale(WorldTerrain.this.scale);
                generateChunk(celestialWorld, chunkX, chunkZ, chunk, generator, random, grid);
                return chunk;
            }
            @Nonnull
            @Override
            public List<BlockPopulator> getDefaultPopulators(@Nonnull World world) {
                List<BlockPopulator> list = new ArrayList<>(4);
                celestialWorld.getPopulators(list);
                return list;
            }
        };
    }

    /**
     * Generate a chunk
     */
    protected void generateChunk(@Nonnull CelestialWorld celestialWorld, int chunkX, int chunkZ,
                                 @Nonnull ChunkGenerator.ChunkData chunk, @Nonnull SimplexOctaveGenerator generator,
                                 @Nonnull Random random, @Nonnull ChunkGenerator.BiomeGrid grid) {
        
        int startX = chunkX << 4;
        int startZ = chunkZ << 4;
        int height;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlock(x, 0, z, Material.BEDROCK);

                int realX = startX + x;
                int realZ = startZ + z;

                // find max height
                height = (int) (this.minHeight + this.maxDeviation * (1 + generator.noise(
                        realX, realZ, this.frequency, this.amplitude, true)
                ));

                // features
                for (TerrainFeature feature : this.features) {
                    feature.generate(generator, chunk, realX, realZ, x, z, height);
                }

                // generate the rest
                for (int y = 1 ; y < height ; y++) {
                    if (chunk.getType(x, y, z) == Material.AIR) {
                        chunk.setBlock(x, y, z, celestialWorld.generateBlock(random, height, x, y, z));
                    }
                }

                // set biome
                Biome biome = celestialWorld.getBiome(random, chunkX, chunkZ);
                for (int y = 0 ; y < 256 ; y++) {
                    grid.setBiome(x, y, z, biome);
                }
            }
        }
    }
    
    @FunctionalInterface
    public interface PopulatorSupplier {
        void get(@Nonnull List<BlockPopulator> populators);
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
