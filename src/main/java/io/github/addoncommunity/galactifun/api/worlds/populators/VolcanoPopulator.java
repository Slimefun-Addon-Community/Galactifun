package io.github.addoncommunity.galactifun.api.worlds.populators;

import java.util.Random;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

/**
 * Volcano populator
 *
 * @author Seggan
 * @author Mooy1
 */
@AllArgsConstructor
public class VolcanoPopulator extends BlockPopulator {

    private final int minY;
    private final Material belowLiquid;
    private final Material liquid;

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

        if (highestBlock.getY() >= this.minY) {
            highestBlock.getRelative(BlockFace.UP).setType(this.liquid);
            for (int i = 0; i < 7; i++) {
                highestBlock.setType(this.belowLiquid, false);
                highestBlock = highestBlock.getRelative(BlockFace.DOWN);
            }
        }
    }

}
