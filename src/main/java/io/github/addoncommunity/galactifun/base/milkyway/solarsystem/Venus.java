package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.universe.Planet;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class Venus extends Planet {

    private static final double MAX_DEVIATION = 50;
    private static final double MIN_HEIGHT = 50;

    public Venus() {
        super("Venus", 67_621_000L, 177_700_000L, new Gravity(0), DayCycle.ETERNAL_DAY, new Atmosphere(
            0,
            false,
            true,
            true,
            true,
            World.Environment.NORMAL,
            new PotionEffectType[0],
            new PotionEffectType[]{PotionEffectType.WITHER}
        ), new Terrain(50, 50, 8, 0.02, 0.5, 0.3));
    }

    @Nonnull
    @Override
    protected Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        if (y > 75) {
            return Material.BLACKSTONE;
        } else if (y > 10) {
            return Material.BASALT;
        } else if (y > 8){
            return Material.YELLOW_TERRACOTTA;
        } else if (y > 0) {
            return Material.BASALT;
        }

        throw new IllegalArgumentException(String.valueOf(y));
    }

    @Nonnull
    @Override
    protected Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.DESERT;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new BlockPopulator() {
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

        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                final int startX = chunk.getX() << 4;
                final int startZ = chunk.getZ() << 4;

                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        Block b = world.getHighestBlockAt(startX + x, startZ + z);
                        if (b.getY() < 75) {
                            for (int y = 75; y > b.getY(); y--) {
                                chunk.getBlock(x, y, z).setType(Material.LAVA);
                            }
                        }
                    }
                }
            }
        });
    }
}
