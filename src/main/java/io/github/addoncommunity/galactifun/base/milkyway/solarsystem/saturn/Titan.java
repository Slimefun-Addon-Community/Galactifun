package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;

/**
 * Class for the Saturnian moon Titan
 *
 * @author Seggan
 */ // TODO clean it up a bit
public final class Titan extends AlienWorld {

    private static final Set<Biome> forests = EnumSet.of(
        Biome.FOREST,
        Biome.BIRCH_FOREST,
        Biome.TALL_BIRCH_FOREST,
        Biome.WOODED_HILLS,
        Biome.BIRCH_FOREST_HILLS,
        Biome.FLOWER_FOREST,
        Biome.TALL_BIRCH_FOREST,
        Biome.WOODED_HILLS,
        Biome.WOODED_MOUNTAINS
    );

    public Titan() {
        super("&6Titan", Orbit.kilometers(1_200_000L, 16), PlanetaryType.TERRESTRIAL, new ItemChoice(Material.SAND));
    }

    @Override
    protected void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid,
                                 @Nonnull Random random, @Nonnull World world, int chunkX, int chunkZ) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, 8);
        generator.setScale(0.004);

        int height;
        int x;
        int z;
        int realX;
        int realZ;

        for (x = 0, realX = chunkX << 4; x < 16; x++, realX++) {
            for (z = 0, realZ = chunkZ << 4; z < 16; z++, realZ++) {

                chunk.setBlock(x, 0, z, Material.BEDROCK);

                // find max height
                double startHeight = generator.noise(realX, realZ, 0.5, 0.5, true);
                height = (int) (55 + 30 * + (startHeight * startHeight));

                Biome biome = grid.getBiome(x, height, z);

                if (biome == Biome.RIVER || biome == Biome.FROZEN_RIVER) {
                    biome = removeRiver(grid, x, z, height);
                }

                switch (biome) {
                    case BADLANDS, MODIFIED_BADLANDS_PLATEAU, MODIFIED_WOODED_BADLANDS_PLATEAU, ERODED_BADLANDS, DESERT -> {
                        if (random.nextDouble() < 0.1) {
                            for (int y = height + random.nextInt(4); y > height; y--) {
                                chunk.setBlock(x, y, z, Material.COAL_BLOCK);
                            }
                        }
                        chunk.setBlock(x, height + 1, z, Material.COAL_BLOCK);
                        generateRest(height, chunk, random, x, z);
                    }
                    case WOODED_BADLANDS_PLATEAU, BADLANDS_PLATEAU, DESERT_HILLS, DESERT_LAKES -> {
                        if (random.nextDouble() < 0.1) {
                            for (int y = height + random.nextInt(4); y > height; y--) {
                                if (random.nextBoolean()) {
                                    chunk.setBlock(x, y, z, Material.COAL_BLOCK);
                                } else {
                                    chunk.setBlock(x, y, z, Material.BLUE_ICE);
                                }
                            }
                        }
                        chunk.setBlock(x, height + 1, z, Material.COAL_BLOCK);
                        generateRest(height, chunk, random, x, z);
                    }
                    case BIRCH_FOREST, WOODED_HILLS, WOODED_MOUNTAINS, FLOWER_FOREST, BIRCH_FOREST_HILLS, TALL_BIRCH_FOREST, TALL_BIRCH_HILLS, FOREST -> {
                        if (random.nextBoolean()) {
                            chunk.setBlock(x, height + 1, z, Material.WARPED_NYLIUM);
                        } else {
                            chunk.setBlock(x, height + 1, z, Material.CRIMSON_NYLIUM);
                        }
                        generateRest(height, chunk, random, x, z);
                    }
                    case SNOWY_TAIGA, SNOWY_TAIGA_HILLS, SNOWY_TAIGA_MOUNTAINS -> {
                        chunk.setBlock(x, height + 1, z, Material.WARPED_NYLIUM);
                        generateRest(height, chunk, random, x, z);
                    }
                    case COLD_OCEAN, DEEP_LUKEWARM_OCEAN, DEEP_OCEAN, DEEP_WARM_OCEAN, LUKEWARM_OCEAN, DEEP_COLD_OCEAN, FROZEN_OCEAN, DEEP_FROZEN_OCEAN, WARM_OCEAN, OCEAN, BEACH, SNOWY_BEACH -> {
                        if (height <= 58) {
                            for (int i = 58; i > height; i--) {
                                chunk.setBlock(x, i, z, Material.WATER);
                            }
                        }
                        chunk.setBlock(x, height + 1, z, Material.SAND);
                        generateRest(height, chunk, random, x, z);
                    }
                    default -> {
                        chunk.setBlock(x, height + 1, z, Material.BLUE_ICE);
                        generateRest(height, chunk, random, x, z);
                    }
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
            } else if (x + dev <= 16 && (grid.getBiome(x + dev, height, z) != Biome.RIVER && grid.getBiome(x + dev, height, z) != Biome.FROZEN_RIVER)) {
                biome = grid.getBiome(x + dev, height, z);
                for (int y = 0; y < 256; y++) {
                    grid.setBiome(x, y, z, biome);
                }
                return biome;
            } else if (z - dev >= 0 && (grid.getBiome(x, height, z - dev) != Biome.RIVER && grid.getBiome(x, height, z - dev) != Biome.FROZEN_RIVER)) {
                biome = grid.getBiome(x, height, z - dev);
                for (int y = 0; y < 256; y++) {
                    grid.setBiome(x, y, z, biome);
                }
                return biome;
            } else if (z + dev <= 16 && (grid.getBiome(x, height, z + dev) != Biome.RIVER && grid.getBiome(x, height, z + dev) != Biome.FROZEN_RIVER)) {
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

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        // TODO add more vegetation
        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                int amount = random.nextInt(2) + 1;
                for (int i = 1; i < amount; i++) {
                    int x = random.nextInt(15);
                    int z = random.nextInt(15);
                    Block b = world.getHighestBlockAt((chunk.getX() << 4) + x, (chunk.getZ() << 4) + z);
                    if (forests.contains(b.getBiome())) {
                        if (random.nextBoolean()) {
                            world.generateTree(b.getLocation(), TreeType.WARPED_FUNGUS);
                        } else {
                            world.generateTree(b.getLocation(), TreeType.CRIMSON_FUNGUS);
                        }
                    }
                }
            }
        });

        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                if (random.nextDouble() < 0.25) {
                    int x = random.nextInt(15);
                    int z = random.nextInt(15);
                    Block b = world.getHighestBlockAt((chunk.getX() << 4) + x, (chunk.getZ() << 4) + z);
                    if (b.getBiome() == Biome.SNOWY_TAIGA || b.getBiome() == Biome.SNOWY_TAIGA_HILLS || b.getBiome() == Biome.SNOWY_TAIGA_MOUNTAINS) {
                        world.generateTree(b.getLocation(), TreeType.WARPED_FUNGUS);
                    }
                }
            }
        });
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return DayCycle.EARTH_LIKE;
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return new AtmosphereBuilder().enableWeather().enableFire()
            .add(Gas.NITROGEN, 97)
            .add(Gas.METHANE, 2.7)
            .add(Gas.HYDROCARBONS, 0.2)
            .add(Gas.HYDROGEN, 0.1)
            .build();
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.metersPerSec(1.352);
    }

}
