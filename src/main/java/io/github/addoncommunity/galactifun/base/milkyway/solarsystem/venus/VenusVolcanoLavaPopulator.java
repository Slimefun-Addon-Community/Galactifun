package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.venus;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.Random;

class VenusVolcanoLavaPopulator extends BlockPopulator {
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
            highestBlock.setType(Material.OBSIDIAN);
            highestBlock.getRelative(BlockFace.UP).setType(Material.LAVA);
        }
    }
}
