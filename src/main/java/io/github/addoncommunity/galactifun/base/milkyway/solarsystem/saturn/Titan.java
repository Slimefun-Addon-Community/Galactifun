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

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class Titan extends CelestialWorld {

    public Titan() {
        super("Titan", 1_200_000L, 31_944_800L, new Gravity(1.352), Material.SAND, DayCycle.EARTH_LIKE, new TitanTerrain(), new Atmosphere(
            0, true, false, false, true, World.Environment.NORMAL
        ), 30, 55);
    }

    // Unused
    @Nonnull
    @Override
    public Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
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
                        int y = world.getHighestBlockYAt(x, z);
                        Block b = chunk.getBlock(x, y, z);
                        if (b.getBiome() == Biome.FOREST) {
                            world.generateTree(b.getLocation(), TreeType.WARPED_FUNGUS);
                        }
                    }
                }
            }
        });
    }
}
