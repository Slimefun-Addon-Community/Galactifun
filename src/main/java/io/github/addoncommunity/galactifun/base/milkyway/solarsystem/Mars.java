package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.Planet;
import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mars extends Planet {
    
    // MIN_HEIGHT + MAX_DEVIATION is the max height the world would generate
    private static final double MAX_DEVIATION = 40;
    
    // The minimum height for the noise generator
    private static final double MIN_HEIGHT = 35;
    
    public Mars() {
        super("Mars", 144_610_000L, -1, 55_910_000L, SolarType.NORMAL, Atmosphere.MARS_LIKE);
    }
    
    @Override
    protected void generateChunk(@Nonnull ChunkData chunk, @Nonnull Random random, @Nonnull BiomeGrid biome, int chunkX, int chunkZ) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(random, 8);
        // The higher the scale, the more extreme the terrain
        generator.setScale(0.01D);

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
            int currentHeight;
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    currentHeight = (int) ((generator.noise(
                            chunkX << 4 + x,
                            chunkZ << 4 + z,
                            0.5D,
                            0.5D,
                            true) + 1) * MAX_DEVIATION + MIN_HEIGHT);

                    // Set top 2 blocks to red sand
                    chunk.setBlock(x, currentHeight, z, Material.RED_SAND);
                    chunk.setBlock(x, currentHeight - 1, z, Material.RED_SAND);

                    // For every remaining block...
                    for (int y = currentHeight - 2; y > 0; y--) {
                        if (random.nextDouble() > 0.2) {
                            // 4/5 blocks are red sandstone
                            chunk.setBlock(x, y, z, Material.RED_SANDSTONE);
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

                    // And bedrock bottom
                    chunk.setBlock(x, 0, z, Material.BEDROCK);
                }
            }
        }

        // Set Biome
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    biome.setBiome(x, y, z, Biome.NETHER_WASTES);
                }
            }
        }
    }

    @Nonnull
    @Override
    public List<BlockPopulator> getDefaultPopulators(@Nonnull World world) {
        return Collections.singletonList(new BlockPopulator() {
            
            // boulder populator
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                if (random.nextBoolean()) {
                    int x = random.nextInt(16);
                    int z = random.nextInt(16);

                    Block b = world.getHighestBlockAt(x * chunk.getX(), z * chunk.getZ());

                    if (b.getType() == Material.GRANITE) return;

                    b.getRelative(BlockFace.UP).setType(Material.GRANITE);

                }
            }
        });
    }

    @Override
    public boolean shouldGenerateCaves() {
        return true;
    }
    
}
