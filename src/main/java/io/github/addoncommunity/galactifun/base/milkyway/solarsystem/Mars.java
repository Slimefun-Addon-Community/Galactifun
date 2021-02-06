package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.world.Terrain;
import io.github.addoncommunity.galactifun.api.universe.world.populators.BoulderPopulator;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * Class for Mars
 *
 * @author Seggan
 * @author Mooy1
 *
 */
public final class Mars extends CelestialWorld {

    public Mars(@Nonnull CelestialBody... celestialBodies) {
        super("&cMars", new Orbit(233_500_000L), 55_910_000L, new Gravity(3.711),
                new Atmosphere(0, false, false, false, false, World.Environment.NETHER),
                new DayCycle(1.03), CelestialType.TERRESTRIAL, 75, 50, Terrain.HILLY_CAVERNS,
                new ItemChoice(Material.RED_SAND), celestialBodies);
    }

    @Nonnull
    @Override
    public Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        // top 4 blocks
        if (y > top - 4) {
            return Material.RED_SAND;
        }
        if (random.nextDouble() > 0.1 || y > 40) {
            // 90% of blocks are terracotta
            return Material.TERRACOTTA;
        } else {
            if (y > 15) {
                // Blue ice is the other 10% or if y > 15
                return Material.BLUE_ICE;
            } else {
                // Otherwise iron ore
                return Material.IRON_ORE;
            }
        }
    }

    @Nonnull
    @Override
    public Biome generateBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.NETHER_WASTES;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new BoulderPopulator(2, 20, Material.GRANITE, Material.RED_SAND));
    }

}
