package io.github.seggan.galactifun.solarsystem.moon;

import io.github.seggan.galactifun.api.CelestialGenerator;
import lombok.NonNull;
import org.bukkit.World;

import java.util.Random;

public class MoonGenerator extends CelestialGenerator {

    @NonNull
    @Override
    public ChunkData generateChunk(World world, Random seedRandom, ChunkData chunk, int chunkX, int chunkZ, BiomeGrid biome) {
        return null;
    }

    @NonNull
    @Override
    public World.Environment getEnvironment() {
        return null;
    }
}
