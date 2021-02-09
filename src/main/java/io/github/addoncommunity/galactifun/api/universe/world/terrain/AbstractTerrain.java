package io.github.addoncommunity.galactifun.api.universe.world.terrain;

import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
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
            int x;
            int y;
            int z;
            for (x = 0; x < 16; x++) {
                for (z = 0; z < 16; z++) {

                    // y = 0, add bedrock and biome
                    chunk.setBlock(x, 0, z, Material.BEDROCK);
                    celestialWorld.generate(random, grid, x, 0, z, celestialWorld.getAvgHeight());

                    // y = 1 to height, generate and add biome
                    for (y = 1 ; y <= celestialWorld.getAvgHeight() ; y++) {
                        if (chunk.getType(x, y, z) == Material.AIR) {
                            celestialWorld.generate(random, grid, x, y, z, celestialWorld.getAvgHeight());
                        }
                    }

                    // y = height to 256, just add biome
                    for (; y < 256 ; y++) {
                        celestialWorld.generate(random, grid, x, y, z, celestialWorld.getAvgHeight());
                    }
                }
            }
        }
    };

    public static final AbstractTerrain VOID = new AbstractTerrain("Void") {
        @Override
        protected void generateChunk(@Nonnull CelestialWorld celestialWorld, int chunkX, int chunkZ, @Nonnull Random random,
                                     @Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull World world) {
            int x;
            int y;
            int z;
            for (x = 0 ; x < 16 ; x++) {
                for (y = 0 ; y < 256 ; y++) {
                    for (z = 0 ; z < 16 ; z++) {
                        grid.setBiome(x, y, z, Biome.THE_VOID);
                    }
                }
            }
        }
    };
    
}
