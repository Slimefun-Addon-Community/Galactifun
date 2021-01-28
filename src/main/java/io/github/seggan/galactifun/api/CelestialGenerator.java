package io.github.seggan.galactifun.api;

import lombok.NonNull;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public abstract class CelestialGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        if (BlockStorage.getStorage(world) == null) {
            new BlockStorage(world);
        }

        return generateChunk(world, random, createChunkData(world), x, z, biome);
    }

    @NonNull
    public abstract ChunkData generateChunk(World world, Random seedRandom, ChunkData chunk, int chunkX, int chunkZ, BiomeGrid biome);

    @NonNull
    public abstract World.Environment getEnvironment();

}
