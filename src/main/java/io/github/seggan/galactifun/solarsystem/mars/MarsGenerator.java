package io.github.seggan.galactifun.solarsystem.mars;

import io.github.seggan.galactifun.api.CelestialGenerator;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MarsGenerator extends CelestialGenerator {
    int currentHeight = 50;

    @Override
    @NonNull
    public ChunkData generateChunk(World world, Random seedRandom, ChunkData chunk, int chunkX, int chunkZ, BiomeGrid biome) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
        generator.setScale(0.01D);

        if (chunkX == 0) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 1, z, Material.RED_SAND);
                    chunk.setBlock(x, 0, z, Material.BEDROCK);
                }
            }
        } else if (chunkX == 1) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 1, z, Material.RED_SAND);
                    chunk.setBlock(x, 0, z, Material.BEDROCK);
                }
            }

            for (int z = 0; z < 16; z++) {
                chunk.setBlock(15, 2, z, Material.RED_SAND);
            }
        } else if (chunkX == -1) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 1, z, Material.RED_SAND);
                    chunk.setBlock(x, 0, z, Material.BEDROCK);
                }
            }

            for (int z = 0; z < 16; z++) {
                chunk.setBlock(0, 2, z, Material.RED_SAND);
            }
        } else {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    currentHeight = (int) ((generator.noise(
                        chunkX * 16 + x,
                        chunkZ * 16 + z,
                        0.5D,
                        0.5D,
                        true) + 1) * 40D + 35D);

                    chunk.setBlock(x, currentHeight, z, Material.RED_SAND);
                    chunk.setBlock(x, currentHeight - 1, z, Material.RED_SAND);

                    for (int i = currentHeight - 2; i > 0; i--) {
                        if (seedRandom.nextDouble() > 0.2) {
                            chunk.setBlock(x, i, z, Material.RED_SANDSTONE);
                        } else {
                            if (i > 15) {
                                chunk.setBlock(x, i, z, Material.BLUE_ICE);
                            } else {
                                chunk.setBlock(x, i, z, Material.IRON_ORE);
                            }
                        }
                    }

                    chunk.setBlock(x, 0, z, Material.BEDROCK);
                }
            }
        }

        biome.setBiome(15, 255, 15, Biome.NETHER_WASTES);

        return chunk;
    }

    @Override
    public @NonNull World.Environment getEnvironment() {
        return World.Environment.NETHER;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Collections.singletonList(new MarsBoulderPopulator());
    }

    @Override
    public boolean shouldGenerateCaves() {
        return true;
    }
}
