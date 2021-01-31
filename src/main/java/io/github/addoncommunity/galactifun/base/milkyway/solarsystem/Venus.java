package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.Planet;
import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Venus extends Planet {

    private static final double MAX_DEVIATION = 50;
    private static final double MIN_HEIGHT = 50;

    public Venus() {
        super("Venus", 67_621_000L, new Gravity(0), 177_700_000L, SolarType.ETERNAL_DAY, new Atmosphere(
            0,
            false,
            true,
            true,
            true,
            World.Environment.NORMAL,
            new PotionEffectType[0],
            new PotionEffectType[]{PotionEffectType.WITHER}
        ));
    }

    @Override
    protected void generateChunk(@Nonnull World world, @Nonnull ChunkData chunk, @Nonnull Random random, @Nonnull BiomeGrid biome, int chunkX, int chunkZ) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, 8);
        generator.setScale(0.02D);

        int currentHeight;
        int startX = chunkX << 4;
        int startZ = chunkZ << 4;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                currentHeight = (int) ((generator.noise(
                    startX + x,
                    startZ + z,
                    0.3D,
                    0.5D,
                    true) + 1) * MAX_DEVIATION + MIN_HEIGHT);

                for (int y = currentHeight; y > 75; y--) {
                    chunk.setBlock(x, y, z, Material.BLACKSTONE);
                }

                for (int y = 75; y > 10; y--) {
                    if (chunk.getType(x, y, z) != Material.AIR) {
                        chunk.setBlock(x, y, z, Material.BASALT);
                    } else {
                        chunk.setBlock(x, y, z, Material.LAVA);
                    }
                }

                for (int y = 10; y > 8; y--) {
                    chunk.setBlock(x, y, z, Material.YELLOW_TERRACOTTA);
                }

                for (int y = 8; y > 0; y--) {
                    chunk.setBlock(x, y, z, Material.BASALT);
                }

                chunk.setBlock(x, 0, z, Material.BEDROCK);

                for (int y = 0; y < 256; y++) {
                    biome.setBiome(x, y, z, Biome.DESERT);
                }
            }
        }
    }

    @Nonnull
    @Override
    public List<BlockPopulator> getDefaultPopulators(@Nonnull World world) {
        return Collections.singletonList(new BlockPopulator() {
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                final int startX = chunk.getX() << 4;
                final int startZ = chunk.getZ() << 4;

                Block highestBlock = chunk.getBlock(0, 0, 0);
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        Block b = world.getHighestBlockAt(startX + x, startZ + z);
                        if (b.getY() > highestBlock.getY()) {
                            highestBlock = b;
                        }
                    }
                }

                if (highestBlock.getY() >= 115) {
                    highestBlock.getRelative(BlockFace.UP).setType(Material.LAVA);
                }
            }
        });
    }
}
