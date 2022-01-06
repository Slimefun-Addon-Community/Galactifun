package io.github.addoncommunity.galactifun.base.universe.saturn;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.api.worlds.populators.LakePopulator;
import io.github.addoncommunity.galactifun.api.worlds.populators.OrePopulator;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.addoncommunity.galactifun.util.Util;

/**
 * Class for the Saturnian moon Titan
 *
 * @author Seggan
 */ // TODO clean it up a bit
public final class Titan extends AlienWorld {

    /*
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
     */

    private volatile SimplexOctaveGenerator generator;
    private volatile TitanBiomeProvider provider;

    public Titan(String name, PlanetaryType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                 DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Override
    protected void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull Random random, @Nonnull WorldInfo world, int chunkX, int chunkZ) {
        init(world);
        for (int x = 0, realX = chunkX << 4; x < 16; x++, realX++) {
            for (int z = 0, realZ = chunkZ << 4; z < 16; z++, realZ++) {

                int height = getHeight(realX, realZ);

                for (int y = 0; y < height; y++) {
                    if (random.nextBoolean()) {
                        chunk.setBlock(x, y, z, Material.STONE);
                    } else {
                        chunk.setBlock(x, y, z, Material.COAL_ORE);
                    }
                }
            }
        }
    }

    @Override
    protected void generateSurface(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull Random random, @Nonnull WorldInfo world, int chunkX, int chunkZ) {
        init(world);
        for (int x = 0, realX = chunkX << 4; x < 16; x++, realX++) {
            for (int z = 0, realZ = chunkZ << 4; z < 16; z++, realZ++) {

                int height = getHeight(realX, realZ);
                TitanBiome biome = this.provider.getBiome(world, realX, realZ);

                Material material = height < 57 ? Material.BLUE_ICE : switch (biome) {
                    case FOREST -> random.nextBoolean() ? Material.WARPED_NYLIUM : Material.CRIMSON_NYLIUM;
                    case FROZEN_FOREST -> Material.WARPED_NYLIUM;
                    case WASTELAND -> Material.SAND;
                    case DRY_ICE_FLATS -> Material.PACKED_ICE;
                    case CARBON_FOREST, FROZEN_CARBON_FOREST -> Material.COAL_BLOCK;
                };

                chunk.setBlock(x, height, z, material);

                // carbon forest/frozen carbon forest
                if (height > 56) {
                    if (biome == TitanBiome.CARBON_FOREST) {
                        if (random.nextDouble() < 0.1) {
                            for (int y = height + random.nextInt(4); y > height; y--) {
                                chunk.setBlock(x, y, z, Material.COAL_BLOCK);
                            }
                        }
                    } else if (biome == TitanBiome.FROZEN_CARBON_FOREST) {
                        if (random.nextDouble() < 0.1) {
                            for (int y = height + random.nextInt(4); y > height; y--) {
                                if (random.nextBoolean()) {
                                    chunk.setBlock(x, y, z, Material.PACKED_ICE);
                                } else {
                                    chunk.setBlock(x, y, z, Material.COAL_BLOCK);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    protected BiomeProvider getBiomeProvider(@Nonnull WorldInfo info) {
        init(info);
        return this.provider;
    }

    private void init(@Nonnull WorldInfo info) {
        if (this.generator == null) {
            this.generator = new SimplexOctaveGenerator(info.getSeed(), 8);
            this.generator.setScale(0.004);
        }
        if (this.provider == null) {
            this.provider = new TitanBiomeProvider();
        }
    }

    int getHeight(int x, int z) {
        // find max height
        double startHeight = generator.noise(x, z, 0.5, 0.5, true);
        return (int) (55 + 30 * (startHeight * startHeight));
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        // TODO add more vegetation
        populators.add(new BlockPopulator() {

            @Override
            public void populate(@Nonnull WorldInfo worldInfo, @Nonnull Random random, int cx, int cz, @Nonnull LimitedRegion region) {
                int amount = random.nextInt(2) + 1;
                for (int i = 0; i < amount; i++) {
                    int x = (cx << 4) + random.nextInt(16);
                    int z = (cz << 4) + random.nextInt(16);

                    if (region.getBiome(x, 1, z) == TitanBiome.FOREST.biome()) {
                        Location l = Util.getHighestBlockAt(region, x, z);
                        if (region.getType(l) == Material.WARPED_NYLIUM) {
                            region.generateTree(l.add(0, 1, 0), random, TreeType.WARPED_FUNGUS);
                        } else {
                            region.generateTree(l.add(0, 1, 0), random, TreeType.CRIMSON_FUNGUS);
                        }
                    }
                }
            }
        });

        populators.add(new BlockPopulator() {

            @Override
            public void populate(@Nonnull WorldInfo worldInfo, @Nonnull Random random, int cx, int cz, @Nonnull LimitedRegion region) {
                if (random.nextDouble() < 0.25) {
                    int x = (cx << 4) + random.nextInt(16);
                    int z = (cz << 4) + random.nextInt(16);

                    if (region.getBiome(x, 1, z) == TitanBiome.FROZEN_FOREST.biome()) {
                        Location l = Util.getHighestBlockAt(region, x, z).add(0, 1, 0);
                        region.generateTree(l, random, TreeType.WARPED_FUNGUS);
                    }
                }
            }
        });

        populators.add(new OrePopulator(
                1,
                50,
                1,
                40,
                2,
                6,
                BaseMats.LASERITE_ORE,
                Material.STONE, Material.COAL_ORE
        ));

        populators.add(new LakePopulator(58, Material.WATER));
    }

}
