package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.bukkit.block.Biome;
import org.bukkit.util.noise.OctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Random;

final class TitanGenerator {

    @Getter
    private final long seed;
    private final int seaLevel;
    private final int deviation;

    private final OctaveGenerator MAIN_GENERATOR;
    private final OctaveGenerator RUGGEDNESS_GENERATOR;
    private final OctaveGenerator MOISTURE_GENERATOR;
    private final OctaveGenerator TEMPERATURE_GENERATOR;

    public TitanGenerator(long seed, int seaLevel, int deviation) {
        this.seed = seed;
        Random random = new Random(seed);

        this.seaLevel = seaLevel;
        this.deviation = deviation;

        this.MAIN_GENERATOR = new SimplexOctaveGenerator(this.seed, 7);
        this.MAIN_GENERATOR.setScale(0.004);
        this.RUGGEDNESS_GENERATOR = new SimplexOctaveGenerator(random.nextLong(), 12);
        this.RUGGEDNESS_GENERATOR.setScale(0.01);
        this.MOISTURE_GENERATOR = new SimplexOctaveGenerator(random.nextLong(), 5);
        this.MOISTURE_GENERATOR.setScale(0.0001);
        this.TEMPERATURE_GENERATOR = new SimplexOctaveGenerator(random.nextLong(), 5);
        this.TEMPERATURE_GENERATOR.setScale(0.0001);
    }

    @Builder(access = AccessLevel.PRIVATE)
    @Data
    static final class GeneratedData {
        private final int height;
        private final TitanBiome biome;
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

    GeneratedData getData(int x, int z) {
        GeneratedData.GeneratedDataBuilder data = new GeneratedData.GeneratedDataBuilder();

        double height = 1 + MAIN_GENERATOR.noise(x, z, 0.2, 0.6, true);
        height += RUGGEDNESS_GENERATOR.noise(x, z, 1, 0.25, true);

        height = Math.pow(seaLevel + deviation * height, 2.5);
        data.height((int) height);

        data.biome(getBiome(x, z, (int) height));



        return data.build();
    }

    private TitanBiome getBiome(int x, int z, int height) {
        double moisture = MOISTURE_GENERATOR.noise(x, z, 0.1, 0.1, true);
        double temp = TEMPERATURE_GENERATOR.noise(x, z, 0.1, 0.1, true);

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
