package io.github.addoncommunity.galactifun.util;

import javax.annotation.Nonnull;

import lombok.experimental.UtilityClass;

import org.bukkit.Location;
import org.bukkit.Material;

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
}
