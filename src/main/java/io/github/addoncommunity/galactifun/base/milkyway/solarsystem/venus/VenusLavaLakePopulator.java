package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.venus;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.Random;

class VenusLavaLakePopulator extends BlockPopulator {
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
}
