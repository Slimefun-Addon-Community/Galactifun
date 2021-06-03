package io.github.addoncommunity.galactifun.api.worlds.populators;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import io.github.addoncommunity.galactifun.util.GenUtils;

/**
 * Class for a custom tree. Left subclassable
 */
@Getter
@AllArgsConstructor
public class CustomTree {

    private final Material log;
    private final Material leaves;
    private final int trunkHeight;

    /**
     * Generates this tree
     * @param location the location of the bottom log. <b>This is modified</b>
     */
    public void generate(@Nonnull Location location) {
        generateOakTop(location.clone(), this.leaves);

        for (int y = 0 ; y < this.trunkHeight ; y++) {
            location.add(0, 1, 0);
            location.getBlock().setType(this.log, false);
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
        top.getRelative(1, 0, 0).setType(leaves, false);
        top.getRelative(-1, 0, 0).setType(leaves, false);
        top.getRelative(0, 0, 1).setType(leaves, false);
        top.getRelative(0, 0, -1).setType(leaves, false);

        GenUtils.generateSquare(topLog.subtract(0, 2, 0), leaves, 2);
        GenUtils.generateSquare(topLog.subtract(0, 1, 0), leaves, 2);
    }

}
