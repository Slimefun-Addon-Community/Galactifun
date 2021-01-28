package io.github.seggan.galactifun.solarsystem.mars;

import io.github.seggan.galactifun.api.CelestialGenerator;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Random;

public class MarsGenerator extends CelestialGenerator {
    int currentHeight = 50;

    @Override
    @NonNull
    public ChunkData generateChunk(World world, Random seedRandom, ChunkData chunk, int chunkX, int chunkZ, BiomeGrid biome) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
        generator.setScale(0.01D);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                currentHeight = (int) ((generator.noise(
                    chunkX * 16 + x,
                    chunkZ * 16 + z,
                    0.5D,
                    0.5D,
                    true) + 1) * 40D + 35D);

                chunk.setBlock(x, currentHeight, z, Material.RED_SAND);
                chunk.setBlock(x, currentHeight - 1, z, Material.RED_SAND);

                for (int i = currentHeight - 2; i > 0; i--) {
                    chunk.setBlock(x, i, z, Material.RED_SANDSTONE);
                }

                chunk.setBlock(x, 0, z, Material.BEDROCK);
            }
        }

        return chunk;
    }
}
