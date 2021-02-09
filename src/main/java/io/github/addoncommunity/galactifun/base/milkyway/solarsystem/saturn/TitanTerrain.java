package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import io.github.addoncommunity.galactifun.api.universe.world.AWorldTerrain;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * A class representing Titan's terrain
 *
 * @author Seggan
 * @author GallowsDove
 */
class TitanTerrain extends AWorldTerrain {

    int height = 50;

    public TitanTerrain() {
        super("Titan");
    }

    @Override
    protected void generateChunk(@Nonnull CelestialWorld celestialWorld, int chunkX, int chunkZ, @Nonnull Random random, @Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull World world) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, 8);
        generator.setScale(0.004);

        int startX = chunkX << 4;
        int startZ = chunkZ << 4;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlock(x, 0, z, Material.BEDROCK);

                int realX = startX + x;
                int realZ = startZ + z;

                // find max height
                height = (int) (celestialWorld.getAvgHeight() + 30 * (1 + generator.noise(
                    realX, realZ, 0.5, 0.5, true))
                );

                Biome biome = grid.getBiome(x, height, z);

                if (biome == Biome.RIVER || biome == Biome.FROZEN_RIVER) {
                    biome = removeRiver(grid, x, z, height);
                }

                switch (biome) {
                    case BADLANDS:
                    case MODIFIED_BADLANDS_PLATEAU:
                    case MODIFIED_WOODED_BADLANDS_PLATEAU:
                    case WOODED_BADLANDS_PLATEAU:
                    case BADLANDS_PLATEAU:
                    case ERODED_BADLANDS:
                        if (random.nextDouble() < 0.1) {
                            for (int y = height + random.nextInt(4); y > height; y--) {
                                chunk.setBlock(x, y, z, Material.COAL_BLOCK);
                            }
                        }
                        generateRest(height, chunk, random, x, z);
                        break;
                    case BIRCH_FOREST:
                    case WOODED_HILLS:
                    case WOODED_MOUNTAINS:
                    case FLOWER_FOREST:
                    case BIRCH_FOREST_HILLS:
                    case TALL_BIRCH_FOREST:
                    case TALL_BIRCH_HILLS:
                    case FOREST:
                        if (random.nextBoolean()) {
                            chunk.setBlock(x, height + 1, z, Material.WARPED_NYLIUM);
                        } else {
                            chunk.setBlock(x, height + 1, z, Material.CRIMSON_NYLIUM);
                        }
                        generateRest(height, chunk, random, x, z);
                        break;
                    case COLD_OCEAN:
                    case DEEP_LUKEWARM_OCEAN:
                    case DEEP_OCEAN:
                    case DEEP_WARM_OCEAN:
                    case LUKEWARM_OCEAN:
                    case DEEP_COLD_OCEAN:
                    case FROZEN_OCEAN:
                    case DEEP_FROZEN_OCEAN:
                    case WARM_OCEAN:
                    case OCEAN:
                        chunk.setBlock(x, height + 1, z, Material.WATER);
                        generateRest(height, chunk, random, x, z);
                        break;
                    default:
                        chunk.setBlock(x, height + 1, z, Material.SAND);
                        generateRest(height, chunk, random, x, z);
                }
            }
        }
    }

    /**
     * Replaces river with the closest biome it finds
     */
    private static Biome removeRiver(ChunkGenerator.BiomeGrid grid, int x, int z, int height) {
        int dev = 1;
        Biome biome = grid.getBiome(x, height, z);
        while (dev < 16) {
            if (x - dev >= 0 && (grid.getBiome(x - dev, height, z) != Biome.RIVER && grid.getBiome(x - dev, height, z) != Biome.FROZEN_RIVER)) {
                biome = grid.getBiome(x - dev, height, z);
                for (int y = 0; y < 256; y++) {
                    grid.setBiome(x, y, z, biome);
                }
                return biome;
            }
            else if (x + dev <= 16 && (grid.getBiome(x + dev, height, z) != Biome.RIVER && grid.getBiome(x + dev, height, z) != Biome.FROZEN_RIVER)) {
                biome = grid.getBiome(x + dev, height, z);
                for (int y = 0; y < 256; y++) {
                    grid.setBiome(x, y, z, biome);
                }
                return biome;
            }
            else if (z - dev >= 0 && (grid.getBiome(x, height, z - dev) != Biome.RIVER && grid.getBiome(x, height, z - dev) != Biome.FROZEN_RIVER)) {
                biome = grid.getBiome(x, height, z - dev);
                for (int y = 0; y < 256; y++) {
                    grid.setBiome(x, y, z, biome);
                }
                return biome;
            }
            else if (z + dev <= 16 && (grid.getBiome(x, height, z + dev) != Biome.RIVER && grid.getBiome(x, height, z + dev) != Biome.FROZEN_RIVER)) {
                biome = grid.getBiome(x, height, z + dev);
                for (int y = 0; y < 256; y++) {
                    grid.setBiome(x, y, z, biome);
                }
                return biome;
            }
            dev++;
        }
        return biome;
    }

    private static void generateRest(int height, ChunkGenerator.ChunkData chunk, Random random, int x, int z) {
        for (int y = height; y > 0; y--) {
            if (random.nextBoolean()) {
                chunk.setBlock(x, y, z, Material.STONE);
            } else {
                chunk.setBlock(x, y, z, Material.COAL_ORE);
            }
        }
    }
}
