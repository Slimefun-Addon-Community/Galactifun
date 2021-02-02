package io.github.addoncommunity.galactifun.api.universe.attributes.terrain;

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
 * An example of how to override default terrain gen. When you don't have access to the api internals
 * just make an inner class. This terrain generator is intended to be used on worlds like
 * Enceladus and Europa
 *
 * @author Seggan
 */
public class FlatTerrain extends Terrain {

    public FlatTerrain(int height) {
        super("Flat", 0, height, 0, 0, 0, 0);
    }

    @Nonnull
    @Override
    public ChunkGenerator createGenerator(@Nonnull BiomeSupplier biomeSupplier, @Nonnull MaterialSupplier materialSupplier, @Nonnull PopulatorSupplier populatorSupplier) {
        return new ChunkGenerator() {
            @Nonnull
            @Override
            public ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int chunkX, int chunkZ, @Nonnull BiomeGrid grid) {
                ChunkData chunk = createChunkData(world);

                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        chunk.setBlock(x, 0, z, Material.BEDROCK);

                        for (int y = 1 ; y < minHeight ; y++) {
                            if (chunk.getType(x, y, z) == Material.AIR) {
                                chunk.setBlock(x, y, z, materialSupplier.get(random, minHeight, x, y, z));
                            }
                        }

                        Biome biome = biomeSupplier.get(random, chunkX, chunkZ);
                        for (int y = 0 ; y < 256 ; y++) {
                            grid.setBiome(x, y, z, biome);
                        }
                    }
                }

                return chunk;
            }

            @Nonnull
            @Override
            public List<BlockPopulator> getDefaultPopulators(@Nonnull World world) {
                List<BlockPopulator> list = new ArrayList<>(4);
                populatorSupplier.get(list);
                return list;
            }
        };
    }
}
