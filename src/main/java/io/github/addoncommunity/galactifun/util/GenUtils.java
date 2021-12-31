package io.github.addoncommunity.galactifun.util;

import java.util.List;

import javax.annotation.Nonnull;

import lombok.experimental.UtilityClass;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

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

    public static final class SingleBiomeProvider extends BiomeProvider {

        private final Biome biome;
        private final List<Biome> singletonBiome;

        public SingleBiomeProvider(Biome biome) {
            this.biome = biome;
            this.singletonBiome = List.of(biome);
        }

        @Nonnull
        @Override
        public Biome getBiome(@Nonnull WorldInfo worldInfo, int x, int y, int z) {
            return biome;
        }

        @Nonnull
        @Override
        public List<Biome> getBiomes(@Nonnull WorldInfo worldInfo) {
            return singletonBiome;
        }

    }
}
