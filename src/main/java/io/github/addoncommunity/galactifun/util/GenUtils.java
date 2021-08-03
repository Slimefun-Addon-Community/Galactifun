package io.github.addoncommunity.galactifun.util;

import javax.annotation.Nonnull;

import lombok.experimental.UtilityClass;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@UtilityClass
public final class GenUtils {

    public static void generateSquare(@Nonnull Location center, @Nonnull Material material, int radius) {
        int startX = center.getBlockX();
        int startZ = center.getBlockZ();
        for (int x = startX - radius; x <= startX + radius; x++) {
            for (int z = startZ - radius; z <= startZ + radius; z++) {
                center.getWorld().getBlockAt(x, center.getBlockY(), z).setType(material, false);
            }
        }
    }

    public static void generatePlus(@Nonnull Location center, @Nonnull Material material) {
        Block b = center.getBlock();
        b.setType(material, false);
        b.getRelative(1, 0, 0).setType(material, false);
        b.getRelative(-1, 0, 0).setType(material, false);
        b.getRelative(0, 0, 1).setType(material, false);
        b.getRelative(0, 0, -1).setType(material, false);
    }

    /**
     * Generates an oak tree top
     *
     * @param topLog the location of the last long. <b>This is modified</b>
     * @param leaves the material of the leaves
     */
    public static void generateOakTop(@Nonnull Location topLog, @Nonnull Material leaves) {
        generateSquare(topLog, leaves, 1);
        generatePlus(topLog.add(0, 1, 0), leaves);
        generateSquare(topLog.subtract(0, 2, 0), leaves, 2);
        generateSquare(topLog.subtract(0, 1, 0), leaves, 2);
    }

}
