package io.github.addoncommunity.galactifun.base.universe.saturn;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;

final class TitanBiomeProvider extends BiomeProvider {

    private SimplexOctaveGenerator heat;
    private SimplexOctaveGenerator humidity;

    private final Titan titan;

    private final Map<IntIntPair, TitanBiome> cachedData = Collections.synchronizedMap(new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<IntIntPair, TitanBiome> eldest) {
            return size() > 200;
        }
    });

    private final List<Biome> allBiomes = Arrays.stream(TitanBiome.values()).map(TitanBiome::biome).collect(Collectors.toList());

    TitanBiomeProvider(Titan titan) {
        this.titan = titan;
    }

    enum TitanBiome {
        FOREST(Biome.FOREST),
        FROZEN_FOREST(Biome.SNOWY_TAIGA),
        WASTELAND(Biome.DESERT),
        DRY_ICE_FLATS(Biome.ICE_SPIKES),
        CARBON_FOREST(Biome.DARK_FOREST),
        FROZEN_CARBON_FOREST(Biome.BIRCH_FOREST),
        LAKES(Biome.OCEAN);

        @Getter
        private final Biome biome;

        TitanBiome(Biome biome) {
            this.biome = biome;
        }
    }

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
        } else if (humidity > 0.1) {
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
        this.heat = new SimplexOctaveGenerator(worldInfo.getSeed(), 8);
        this.heat.setScale(0.001);
        this.humidity = new SimplexOctaveGenerator(worldInfo.getSeed(), 8);
        this.humidity.setScale(0.001);
    }

}
