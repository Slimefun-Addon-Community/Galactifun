package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericGas;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.world.SimpleAlienWorld;
import io.github.addoncommunity.galactifun.api.universe.world.populators.BoulderPopulator;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

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
public final class Mars extends SimpleAlienWorld {

    public Mars() {
        super("&cMars", Orbit.kilometers(227_943_824L), CelestialType.TERRESTRIAL, new ItemChoice(Material.RED_SAND));
    }
    
    @Nonnull
    @Override
    protected Material generateMaterial(@Nonnull Random random, int x, int y, int z, int top) {
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

    @Override
    protected void generateMore(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull SimplexOctaveGenerator generator,
                                @Nonnull Random random, int realX, int realZ, int x, int z, int height) {
            // generate caves
            for (int y = 1 ; y <= height - 16 ; y++) {
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
        populators.add(new BoulderPopulator(2, 20, Material.GRANITE, Material.RED_SAND));
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return DayCycle.of(1, 1);
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return new AtmosphereBuilder()
            .addCarbonDioxide(94.9)
            .addNitrogen(2.6)
            .addComponent(AtmosphericGas.ARGON, 1.9)
            .build();
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.metersPerSec(3.711);
    }

    @Override
    protected long createSurfaceArea() {
        return 144_371_391L;
    }

}
