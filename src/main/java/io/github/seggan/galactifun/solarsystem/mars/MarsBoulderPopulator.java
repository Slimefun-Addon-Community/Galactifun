package io.github.seggan.galactifun.solarsystem.mars;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

class MarsBoulderPopulator extends BlockPopulator {
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int x = random.nextInt(16);
        int z = random.nextInt(16);

        Block b = world.getHighestBlockAt((chunk.getX() << 4) + x, (chunk.getZ() << 4) + z);
        if (b.getType() == Material.GRANITE) return;

        Block up = b.getRelative(BlockFace.UP);

        if (random.nextBoolean()) {
            up.setType(Material.GRANITE);
        }
    }
}
