package io.github.addoncommunity.galactifun.api.universe.world.populators;

import io.github.addoncommunity.galactifun.util.GenUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import javax.annotation.Nonnull;

/**
 * Class for a custom tree. Left subclassable
 */
@AllArgsConstructor
@Getter
@SuppressWarnings("ClassCanBeRecord")
public class CustomTree {

    @Nonnull
    private final Material log;
    @Nonnull
    private final Material leaves;
    private final int trunkHeight;

    private static final BlockFace[] SURROUNDING_ADJACENT_FACES = new BlockFace[]{
        BlockFace.NORTH,
        BlockFace.SOUTH,
        BlockFace.EAST,
        BlockFace.WEST
    };

    /**
     * Generates this tree
     * @param location the location of the bottom log. <b>This is modified</b>
     */
    public void generate(@Nonnull Location location) {
        generateOakTop(location.clone(), leaves);

        for (int y = 0; y < trunkHeight; y++) {
            location.add(0, 1, 0);
            location.getBlock().setType(log, false);
        }
    }

    /**
     * Generates an oak tree top
     * @param topLog the location of the last long. <b>This is modified</b>
     * @param leaves the material of the leaves
     */
    public static void generateOakTop(@Nonnull Location topLog, @Nonnull Material leaves) {
        GenUtils.generateSquare(topLog, leaves, 1);

        Block top = topLog.add(0, 1, 0).getBlock();
        top.setType(leaves, false);
        for (BlockFace face : SURROUNDING_ADJACENT_FACES) {
            top.getRelative(face).setType(leaves, false);
        }

        GenUtils.generateSquare(topLog.subtract(0, 2, 0), leaves, 2);
        GenUtils.generateSquare(topLog.subtract(0, 1, 0), leaves, 2);
    }
}
