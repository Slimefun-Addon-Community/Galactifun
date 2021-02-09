package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Class for the Saturnian moon Titan
 *
 * @author Seggan
 */
public class Titan extends CelestialWorld {

    private static final Set<Biome> forests = EnumSet.of(
        Biome.FOREST,
        Biome.BIRCH_FOREST,
        Biome.TALL_BIRCH_FOREST,
        Biome.WOODED_HILLS,
        Biome.BIRCH_FOREST_HILLS,
        Biome.FLOWER_FOREST,
        Biome.TALL_BIRCH_FOREST
    );

    public Titan() {
        super("Titan", 1_200_000L, 31_944_800L, new Gravity(1.352), Material.SAND, DayCycle.EARTH_LIKE, new TitanTerrain(), new Atmosphere(
            0, true, false, false, true, World.Environment.NORMAL
        ), 30, 55);
    }

    // Unused
    @Nonnull
    @Override
    public Material generate(@Nonnull Random random, @Nonnull ChunkGenerator.BiomeGrid biomeGrid, int x, int y, int z, int top) {
        return Material.AIR;
    }

    // Unused
    @Nonnull
    @Override
    public Biome generateBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.PLAINS;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                if (random.nextBoolean()) {
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
            }
        });
    }
}
