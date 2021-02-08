package io.github.addoncommunity.galactifun.api.universe.world;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
@AllArgsConstructor
public abstract class AbstractTerrain {
    
    @Getter
    @Nonnull
    private final String name;
    
    public static final AbstractTerrain FLAT = new AbstractTerrain("Flat") {
        @Override
        protected void generateChunk(@Nonnull CelestialWorld celestialWorld, int chunkX, int chunkZ, @Nonnull Random random,
                                     @Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull World world) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 0, z, Material.BEDROCK);

                    for (int y = 1 ; y <= celestialWorld.getAvgHeight() ; y++) {
                        chunk.setBlock(x, y, z, celestialWorld.generateBlock(random, celestialWorld.getAvgHeight(), x, y, z));
                    }
                    
                    for (int y = 0 ; y < 256 ; y++) {
                        celestialWorld.generateBiome(grid, x, y, y);
                    }
                }
            }
        }
    };
    
    public static final AbstractTerrain VOID = new AbstractTerrain("Void") {
        @Override
        protected void generateChunk(@Nonnull CelestialWorld celestialWorld, int chunkX, int chunkZ, @Nonnull Random random,
                                     @Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull World world) {
            for (int x = 0 ; x < 16 ; x++) {
                for (int y = 0 ; y < 256 ; y++) {
                    for (int z = 0 ; z < 16 ; z++) {
                        grid.setBiome(x, y, z, Biome.THE_VOID);
                    }
                }
            }
        }
    };

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
