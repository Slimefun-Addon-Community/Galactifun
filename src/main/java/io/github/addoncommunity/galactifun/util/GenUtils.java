package io.github.addoncommunity.galactifun.util;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.experimental.UtilityClass;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.RegionAccessor;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

@UtilityClass
public final class GenUtils {

    @ParametersAreNonnullByDefault
    public static void generateSquare(RegionAccessor accessor, Location center, Material material, int radius) {
        int startX = center.getBlockX();
        int startZ = center.getBlockZ();
        for (int x = startX - radius; x <= startX + radius; x++) {
            for (int z = startZ - radius; z <= startZ + radius; z++) {
                accessor.setType(x, center.getBlockY(), z, material);
            }
        }
    }

    public static void generatePlus(@Nonnull RegionAccessor accessor, @Nonnull Location center, @Nonnull Material material) {
        accessor.setType(center.getBlockX(), center.getBlockY(), center.getBlockZ(), material);
        accessor.setType(center.getBlockX() + 1, center.getBlockY(), center.getBlockZ(), material);
        accessor.setType(center.getBlockX() - 1, center.getBlockY(), center.getBlockZ(), material);
        accessor.setType(center.getBlockX(), center.getBlockY(), center.getBlockZ() + 1, material);
        accessor.setType(center.getBlockX(), center.getBlockY(), center.getBlockZ() - 1, material);
    }

    /**
     * Generates an oak tree top
     *
     * @param topLog the location of the last long. <b>This is modified</b>
     * @param leaves the material of the leaves
     */
    public static void generateOakTop(@Nonnull RegionAccessor accessor, @Nonnull Location topLog, @Nonnull Material leaves) {
        generateSquare(accessor, topLog, leaves, 1);
        generatePlus(accessor, topLog.add(0, 1, 0), leaves);
        generateSquare(accessor, topLog.subtract(0, 2, 0), leaves, 2);
        generateSquare(accessor, topLog.subtract(0, 1, 0), leaves, 2);
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
