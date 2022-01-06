package io.github.addoncommunity.galactifun.base.universe.saturn;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;

final class TitanBiomeProvider extends BiomeProvider {

    private volatile SimplexOctaveGenerator heat;
    private volatile SimplexOctaveGenerator humidity;

    private final Map<IntIntPair, TitanBiome> cachedData = Collections.synchronizedMap(new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<IntIntPair, TitanBiome> eldest) {
            return size() > 200;
        }
    });

    private final List<Biome> allBiomes = Arrays.stream(TitanBiome.values()).map(TitanBiome::biome).collect(Collectors.toList());

    @Nonnull
    @Override
    public Biome getBiome(@Nonnull WorldInfo worldInfo, int x, int y, int z) {
        return getBiome(worldInfo, x, z).biome();
    }

    @Nonnull
    TitanBiome getBiome(WorldInfo info, int x, int z) {
        init(info);

        TitanBiome cached = cachedData.get(new IntIntImmutablePair(x, z));
        if (cached != null) {
            return cached;
        }

        double heat = this.heat.noise(x, z, 0.01, 0.03, true);
        double humidity = this.humidity.noise(x, z, 0.01, 0.03, true);

        TitanBiome biome;
        if (humidity > 0.2) {
            if (heat > 0.2) {
                biome = TitanBiome.FOREST;
            } else if (heat > 0) {
                biome = TitanBiome.FROZEN_FOREST;
            } else {
                biome = TitanBiome.DRY_ICE_FLATS;
            }
        } else if (humidity > -0.1) {
            if (heat > 0.2) {
                biome = TitanBiome.CARBON_FOREST;
            } else {
                biome = TitanBiome.FROZEN_CARBON_FOREST;
            }
        } else {
            biome = TitanBiome.WASTELAND;
        }

        cachedData.put(new IntIntImmutablePair(x, z), biome);
        return biome;
    }

    @Nonnull
    @Override
    public List<Biome> getBiomes(@Nonnull WorldInfo worldInfo) {
        return allBiomes;
    }

    private void init(WorldInfo worldInfo) {
        if (this.heat == null) {
            this.heat = new SimplexOctaveGenerator(worldInfo.getSeed(), 8);
            this.heat.setScale(0.001);
        }
        if (this.humidity == null) {
            this.humidity = new SimplexOctaveGenerator(new Random(worldInfo.getSeed()).nextLong(), 8);
            this.humidity.setScale(0.003);
        }
    }

}
