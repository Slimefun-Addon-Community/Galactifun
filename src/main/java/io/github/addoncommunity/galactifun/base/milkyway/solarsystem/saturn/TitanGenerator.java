package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.block.Biome;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import java.util.Random;

final class TitanGenerator {

    @Getter
    private final long seed;
    private final int seaLevel;
    private final int deviation;

    private final NoiseGenerator MAIN_GENERATOR;
    private final NoiseGenerator MOISTURE_GENERATOR;
    private final NoiseGenerator TEMPERATURE_GENERATOR;

    public TitanGenerator(long seed, int seaLevel, int deviation) {
        this.seed = seed;
        Random random = new Random(seed);

        this.seaLevel = seaLevel;
        this.deviation = deviation;

        this.MAIN_GENERATOR = new SimplexNoiseGenerator(this.seed);
        this.MOISTURE_GENERATOR = new SimplexNoiseGenerator(random.nextLong());
        this.TEMPERATURE_GENERATOR = new SimplexNoiseGenerator(random.nextLong());
    }

    @Getter
    @AllArgsConstructor
    enum TitanBiome {
        BEACH(Biome.BEACH),
        OCEAN(Biome.OCEAN),
        DENSE_FOREST(Biome.DARK_FOREST),
        FOREST(Biome.FOREST),
        FROZEN_FOREST(Biome.SNOWY_TUNDRA),
        PILLARS(Biome.SAVANNA),
        ICY_PILLARS(Biome.ICE_SPIKES),
        WASTELAND(Biome.BADLANDS),
        DESERT(Biome.DESERT),
        FROZEN_DESERT(Biome.SNOWY_BEACH),
        ;

        private final Biome correspondingBiome;
    }

    int getHeight(int x, int z) {
        double height = 1 + MAIN_GENERATOR.noise(x, z, 8,0.2, 0.6, true);

        height = seaLevel + deviation * height;
        height = height * height * height;

        return (int) height;
    }

    TitanBiome getBiome(int x, int z, int height) {
        double moisture = MOISTURE_GENERATOR.noise(x, z, 4, 0.1, 0.1, true);
        double temp = TEMPERATURE_GENERATOR.noise(x, z, 4, 0.1, 0.1, true);

        if (height < seaLevel + 5) return TitanBiome.OCEAN;
        if (height < seaLevel + 7) return TitanBiome.BEACH;

        if (temp > 0.4) {
            if (moisture > 0.6) return TitanBiome.DENSE_FOREST;
            if (moisture > -0.3) return TitanBiome.FOREST;
            return TitanBiome.FROZEN_FOREST;
        }

        if (temp > 0.2) {
            if (moisture >= 0) return TitanBiome.PILLARS;
            return TitanBiome.ICY_PILLARS;
        }

        if (moisture > 0.5) return TitanBiome.WASTELAND;
        if (moisture > -0.1) return TitanBiome.FROZEN_DESERT;
        return TitanBiome.DESERT;
    }
}
