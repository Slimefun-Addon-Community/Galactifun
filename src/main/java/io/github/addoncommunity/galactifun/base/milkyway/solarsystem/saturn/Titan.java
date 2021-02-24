package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericComponent;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * Class for the Saturnian moon Titan
 *
 * @author Seggan
 */
public final class Titan extends AlienWorld {

    private TitanGenerator generator = null;
    private long seed = -1;


    public Titan() {
        super("&6Titan", new Orbit(1_200_000L), CelestialType.TERRESTRIAL, new ItemChoice(Material.SAND));
    }

    @Override
    protected void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid,
                                 @Nonnull Random random, @Nonnull World world, int chunkX, int chunkZ) {

        if (generator == null) {
            if (seed == -1) {
                seed = world.getSeed();
            }

            generator = new TitanGenerator(seed, 50, 45);
        }

        int height;
        int x;
        int z;
        int realX;
        int realZ;
        TitanGenerator.TitanBiome biome;

        for (x = 0, realX = chunkX << 4; x < 16; x++, realX++) {
            for (z = 0, realZ = chunkZ << 4; z < 16; z++, realZ++) {
                chunk.setBlock(x, 0, z, Material.BEDROCK);

                TitanGenerator.GeneratedData data = generator.getData(realX, realZ);

                height = data.getHeight();
                biome = data.getBiome();

                PluginUtils.log("b");
                switch (biome) {
                    case FOREST:
                    case DENSE_FOREST:
                        if (random.nextBoolean()) {
                            chunk.setBlock(x, height, z, Material.CRIMSON_NYLIUM);
                        } else {
                            chunk.setBlock(x, height, z, Material.WARPED_NYLIUM);
                        }
                        break;
                    case FROZEN_FOREST:
                        if (random.nextDouble() < 0.2) {
                            chunk.setBlock(x, height, z, Material.ICE);
                        } else {
                            chunk.setBlock(x, height, z, Material.WARPED_NYLIUM);
                        }
                        break;
                    case PILLARS:
                        chunk.setBlock(x, height, z, Material.COAL_BLOCK);
                        break;
                    case ICY_PILLARS:
                        if (random.nextDouble() < 0.2) {
                            chunk.setBlock(x, height, z, Material.ICE);
                        } else {
                            chunk.setBlock(x, height, z, Material.COAL_BLOCK);
                        }
                        break;
                    case OCEAN:
                        if (height <= 55) {
                            for (int y = 55; y >= height; y--) {
                                chunk.setBlock(x, y, z, Material.WATER);
                            }
                        }
                        break;
                    case BEACH:
                        if (height <= 57) {
                            for (int y = 57; y >= height; y--) {
                                chunk.setBlock(x, y, z, Material.SAND);
                            }
                        }
                        break;
                    case DESERT:
                        chunk.setBlock(x, height, z, Material.SAND);
                        break;
                    case FROZEN_DESERT:
                        if (random.nextDouble() < 0.2) {
                            chunk.setBlock(x, height, z, Material.ICE);
                        } else {
                            chunk.setBlock(x, height, z, Material.SAND);
                        }
                        break;
                    case WASTELAND:
                        chunk.setBlock(x, height, z, Material.BLUE_ICE);
                        break;
                    default:
                        throw new IllegalStateException("Biome " + biome.name() + " not recognized!");
                }

                PluginUtils.log("rest");
                for (int y = 0; y < height; y++) {
                    if (random.nextDouble() < generator.getCoalDistribution(realX, y, realZ)) {
                        chunk.setBlock(x, y, z, Material.COAL_ORE);
                    } else {
                        chunk.setBlock(x, y, z, Material.STONE);
                    }
                    grid.setBiome(x, y, z, biome.getCorrespondingBiome());
                }
            }
        }
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                int amount = random.nextInt(3) + 1;
                for (int i = 1; i < amount; i++) {
                    int x = random.nextInt(15);
                    int z = random.nextInt(15);
                    Block b = world.getHighestBlockAt((chunk.getX() << 4) + x, (chunk.getZ() << 4) + z);
                    if (b.getBiome() == Biome.DARK_FOREST) {
                        if (random.nextBoolean()) {
                            world.generateTree(b.getLocation(), TreeType.WARPED_FUNGUS);
                        } else {
                            world.generateTree(b.getLocation(), TreeType.CRIMSON_FUNGUS);
                        }
                    } else if (b.getBiome() == Biome.FOREST) {
                        if (random.nextBoolean()) {
                            if (random.nextBoolean()) {
                                world.generateTree(b.getLocation(), TreeType.WARPED_FUNGUS);
                            } else {
                                world.generateTree(b.getLocation(), TreeType.CRIMSON_FUNGUS);
                            }
                        }
                    } else if (b.getBiome() == Biome.SNOWY_TUNDRA) {
                        if (random.nextDouble() < 0.2) {
                            PluginUtils.log("test1");
                            world.generateTree(b.getLocation(), TreeType.WARPED_FUNGUS, new BlockChangeDelegate() {
                                @Override
                                public boolean setBlockData(int x, int y, int z, @Nonnull BlockData blockData) {
                                    world.getBlockAt(x, y, z).setType(Material.BLUE_ICE, false);
                                    return true;
                                }

                                @Nonnull
                                @Override
                                public BlockData getBlockData(int x, int y, int z) {
                                    return world.getBlockAt(x, y, z).getBlockData();
                                }

                                @Override
                                public int getHeight() {
                                    return 255;
                                }

                                @Override
                                public boolean isEmpty(int x, int y, int z) {
                                    return world.getBlockAt(x, y, z).isEmpty();
                                }
                            });
                        }
                    }
                }
            }
        });

        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                for (int n = 0; n < 3; n++) {
                    int x = random.nextInt(15);
                    int z = random.nextInt(15);
                    int height = world.getHighestBlockYAt((chunk.getX() << 4) + x, (chunk.getZ() << 4) + z);
                    Block b = chunk.getBlock(x, height, z);
                    if (b.getBiome() == Biome.SAVANNA || b.getBiome() == Biome.ICE_SPIKES) {
                        for (int y = height + random.nextInt(4); y > height; y--) {
                            PluginUtils.log("spiky");
                            if (b.getBiome() == Biome.SAVANNA) {
                                chunk.getBlock(x, y, z).setType(Material.COAL_BLOCK, false);
                            } else {
                                if (random.nextBoolean()) {
                                    chunk.getBlock(x, y, z).setType(Material.COAL_BLOCK, false);
                                } else {
                                    chunk.getBlock(x, y, z).setType(Material.ICE, false);
                                }
                            }
                        }
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
            .addNitrogen(97)
            .addComponent(AtmosphericComponent.METHANE, 2.7)
            .addComponent(AtmosphericComponent.HYDROCARBONS, 0.2)
            .addComponent(AtmosphericComponent.HYDROGEN, 0.1)
            .build();
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.metersPerSec(1.352);
    }

    @Override
    protected long createSurfaceArea() {
        return 83_305_418L;
    }

    @Override
    protected void afterWorldLoad(@Nonnull World world) {
        if (seed == -1) {
            seed = world.getSeed();
        }
    }

    @Override
    protected boolean shouldGenerateCaves() {
        return true;
    }
}
