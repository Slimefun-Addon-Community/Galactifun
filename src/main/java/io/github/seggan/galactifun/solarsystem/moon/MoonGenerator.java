package io.github.seggan.galactifun.solarsystem.moon;

import lombok.NonNull;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class MoonGenerator extends ChunkGenerator {

    @Nonnull
    @NonNull
    @Override
    public ChunkData generateChunk(World world, Random seedRandom, ChunkData chunk, int chunkX, int chunkZ, BiomeGrid biome) {
        return null;
    }

    @Nonnull
    @NonNull
    @Override
    public World.Environment getEnvironment() {
        return null;
    }
}
