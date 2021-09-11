package io.github.addoncommunity.galactifun.base.universe;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.SimpleAlienWorld;
import io.github.addoncommunity.galactifun.util.GenUtils;

/**
 * Class for Mars
 *
 * @author Seggan
 * @author Mooy1
 */
public final class Mars extends SimpleAlienWorld {

    public Mars(String name, PlanetaryType type, Orbit orbit, StarSystem orbiting, ItemStack baseItem,
                DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Nonnull
    @Override
    protected Material generateMaterial(@Nonnull Random random, int x, int y, int z, int top) {
        // top 4 blocks
        if (y > top - 4) {
            return Material.RED_SAND;
        }
        if (random.nextDouble() <= 0.1 && y <= 15) {
            // 10% of blocks under y 15 are iron ore
            return Material.IRON_ORE;
        }
        // 90% of blocks are terracotta
        return Material.TERRACOTTA;
    }

    @Override
    protected void generateMore(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull SimplexOctaveGenerator generator,
                                @Nonnull Random random, int realX, int realZ, int x, int z, int height) {
        // generate caves
        for (int y = 1; y <= height - 16; y++) {
            double density = generator.noise(realX, y, realZ, getFrequency(), getAmplitude(), true);

            // Choose a narrow selection of blocks
            if (Math.abs(density) < 0.03) {
                chunk.setBlock(x, y, z, Material.CAVE_AIR);
            }
        }
    }

    @Nonnull
    @Override
    protected Biome getBiome() {
        return Biome.DESERT_HILLS;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new BlockPopulator() {
            @Override
            @ParametersAreNonnullByDefault
            public void populate(World world, Random random, Chunk source) {

                if (random.nextInt(100) < 1) {

                    int x = random.nextInt(16);
                    int z = random.nextInt(16);

                    Block b = world.getHighestBlockAt((source.getX() << 4) + x, (source.getZ() << 4) + z);
                    b.getRelative(BlockFace.UP).setType(Material.ANCIENT_DEBRIS);
                }
            }
        });
        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk source) {
                if (random.nextDouble() < 0.5) {
                    int dist = random.nextInt(4) + 1;

                    int x = random.nextInt(16 - dist * 2) + dist;
                    int z = random.nextInt(16 - dist * 2) + dist;
                    int y = random.nextInt(30) + 1;

                    GenUtils.generateSquare(
                            source.getBlock(x, y, z).getLocation(),
                            Material.PACKED_ICE,
                            dist
                    );
                }
            }
        });
    }

}
