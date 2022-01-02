package io.github.addoncommunity.galactifun.base.universe;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
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
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;

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
        if (random.nextDouble() <= 0.1 && y <= 15) {
            // 10% of blocks under y 15 are iron ore
            return Material.IRON_ORE;
        }
        // 90% of blocks are terracotta
        return Material.TERRACOTTA;
    }

    @Nonnull
    @Override
    protected ObjectIntPair<Material> getTop() {
        // top 4 blocks
        return new ObjectIntImmutablePair<>(Material.RED_SAND, 4);
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
        return Biome.DESERT;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull WorldInfo worldInfo, @Nonnull Random random, int cx, int cz, @Nonnull LimitedRegion region) {
                if (random.nextInt(100) < 1) {
                    int x = random.nextInt(16) + (region.getCenterChunkX() << 4);
                    int z = random.nextInt(16) + (region.getCenterChunkZ() << 4);

                    for (int y = 0; y < worldInfo.getMaxHeight(); y++) {
                        if (region.getType(x, y, z).isAir()) {
                            region.setType(x, y, z, Material.ANCIENT_DEBRIS);
                            break;
                        }
                    }
                }
            }
        });
        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull WorldInfo worldInfo, @Nonnull Random random, int cx, int cz, @Nonnull LimitedRegion region) {
                if (random.nextDouble() < 0.5) {

                    int x = random.nextInt(16) + (region.getCenterChunkX() << 4);
                    int z = random.nextInt(16) + (region.getCenterChunkZ() << 4);
                    int y = random.nextInt(30) + 1;

                    GenUtils.generateSquare(
                            region,
                            new Location(null, x, y, z),
                            Material.PACKED_ICE,
                            random.nextInt(4) + 1
                    );
                }
            }
        });
    }

}
