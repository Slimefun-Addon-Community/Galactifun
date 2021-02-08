package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Any world terrain
 *
 * @author Mooy1
 * @author Seggan
 */
public abstract class AWorldTerrain extends Terrain {

    public static final AWorldTerrain FLAT = new AWorldTerrain("Flat") {
        @Override
        protected void generateChunk(@Nonnull CelestialWorld celestialWorld, int chunkX, int chunkZ, @Nonnull Random random,
                                     @Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull World world) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 0, z, Material.BEDROCK);

                    for (int y = 1; y < celestialWorld.getAvgHeight(); y++) {
                        chunk.setBlock(x, y, z, celestialWorld.generateBlock(random, celestialWorld.getAvgHeight(), x, y, z));
                    }

                    Biome biome = celestialWorld.generateBiome(random, chunkX, chunkZ);
                    for (int y = 0; y < 256; y++) {
                        grid.setBiome(x, y, z, biome);
                    }
                }
            }
        }
    };
    public static final AWorldTerrain VOID = new AWorldTerrain("Void") {
        @Override
        protected void generateChunk(@Nonnull CelestialWorld celestialWorld, int chunkX, int chunkZ, @Nonnull Random random,
                                     @Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull World world) {
            // add nothing
        }
    };

    public AWorldTerrain(@Nonnull String name) {
        super(name);
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
                generateChunk(celestialWorld, chunkX, chunkZ, random, chunk, grid, world);
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
    protected abstract void generateChunk(@Nonnull CelestialWorld celestialWorld, int chunkX, int chunkZ, @Nonnull Random random,
                                          @Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull World world);

}
