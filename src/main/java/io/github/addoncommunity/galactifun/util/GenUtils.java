package io.github.addoncommunity.galactifun.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;

import javax.annotation.Nonnull;

@UtilityClass
public class GenUtils {

    public static void generateSquare(@Nonnull Location center, @Nonnull Material material, int radius) {
        assert center.getWorld() != null;

        int startX = center.getBlockX();
        int startZ = center.getBlockZ();
        for (int x = startX - radius; x <= startX + radius; x++) {
            for (int z = startZ - radius; z <= startZ + radius; z++) {
                center.getWorld().getBlockAt(x, center.getBlockY(), z).setType(material, false);
            }
        }
    }
}
