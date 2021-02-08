package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.type.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.world.Terrain;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * Class for the Saturnian moon Enceladus
 *
 * @author Seggan
 */
public class Enceladus extends CelestialWorld {

    public Enceladus() {
        super("Enceladus", new Orbit(237_948L), 308_359L, new Gravity(0.0113), Atmosphere.NONE, DayCycle.ETERNAL_NIGHT,
                CelestialType.FROZEN, 60,  Terrain.FLAT, new ItemChoice(Material.ICE));
    }

    @Nonnull
    @Override
    public Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        if (y >= 30) {
            return Material.PACKED_ICE;
        } else {
            return Material.BLUE_ICE;
        }
    }

    @Override
    public void generateBiome(@Nonnull ChunkGenerator.BiomeGrid grid, int x, int y, int z) {
        grid.setBiome(x, y, z, Biome.FROZEN_OCEAN);
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {

    }

}
