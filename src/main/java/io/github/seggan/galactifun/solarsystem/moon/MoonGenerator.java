package io.github.seggan.galactifun.solarsystem.moon;

import io.github.seggan.galactifun.api.CelestialGenerator;
import org.bukkit.World;

import java.util.Random;

class MoonGenerator extends CelestialGenerator {

    @Override
    public ChunkData generateChunk(World world, Random seedRandom, ChunkData chunk, int chunkX, int chunkZ, BiomeGrid biome) {
        return null;
    }

    @Override
    public World.Environment getEnvironment() {
        return null;
    }
}
