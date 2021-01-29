package io.github.seggan.galactifun.solarsystem.mars;

import io.github.seggan.galactifun.api.CelestialGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

class MarsGenerator extends CelestialGenerator {
    int currentHeight = 50;
    // MIN_HEIGHT + MAX_DEVIATION is the max height the world would generate
    private static final double MAX_DEVIATION = 40;
    // The minimum height for the noise generator
    private static final double MIN_HEIGHT = 35;

    @Override
    public ChunkData generateChunk(World world, Random seedRandom, ChunkData chunk, int chunkX, int chunkZ, BiomeGrid biome) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
        SimplexOctaveGenerator caveGenerator = new SimplexOctaveGenerator(new Random(world.getSeed()), 16);
        // The higher the scale, the more extreme the terrain
        generator.setScale(0.01D);
        caveGenerator.setScale(1D);

        // This stuff is for generating the canyon. The else clause has the real terrain gen code
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
                    for (int y = 1; y < 60; y++) {
                        double density = generator.noise(
                            chunkX * 16 + x,
                            y,
                            chunkZ * 16 + z,
                            0.5D,
                            0.5D,
                            true
                        );
                        if (density > 0.25) {
                            chunk.setBlock(x, y, z, Material.CAVE_AIR);
                        }
                    }
                    currentHeight = (int) ((generator.noise(
                        chunkX * 16 + x,
                        chunkZ * 16 + z,
                        0.5D,
                        0.5D,
                        true) + 1) * MAX_DEVIATION + MIN_HEIGHT);

                    if (chunk.getType(x, currentHeight-1, z) != Material.CAVE_AIR) {
                        // Set top block to red sand
                        chunk.setBlock(x, currentHeight, z, Material.RED_SAND);
                    }

                    // For every remaining block...
                    for (int y = currentHeight - 1; y > 0; y--) {
                        if (chunk.getType(x, y, z) != Material.CAVE_AIR) {
                            if (seedRandom.nextDouble() > 0.2) {
                                // 4/5 blocks are red sandstone
                                chunk.setBlock(x, y, z, Material.TERRACOTTA);
                            } else {
                                if (y > 15) {
                                    // Blue ice is the other 1/5 if y > 15
                                    chunk.setBlock(x, y, z, Material.BLUE_ICE);
                                } else {
                                    // Otherwise iron ore
                                    chunk.setBlock(x, y, z, Material.IRON_ORE);
                                }
                            }
                        }
                    }

                    // And bedrock bottom
                    chunk.setBlock(x, 0, z, Material.BEDROCK);
                }
            }
        }

        // Set biome
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    biome.setBiome(x, y, z, Biome.NETHER_WASTES);
                }
            }
        }

        return chunk;
    }

    @Override
    public World.Environment getEnvironment() {
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
