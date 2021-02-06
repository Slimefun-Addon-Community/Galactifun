package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.world.Terrain;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

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
                CelestialType.TERRESTRIAL, 75, 20, Terrain.FLAT);
    }

    @Nonnull
    @Override
    public Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        if (y >= 60) {
            return Material.ICE;
        } else if (y >= 30) {
            return Material.PACKED_ICE;
        } else {
            return Material.BLUE_ICE;
        }
    }

    @Nonnull
    @Override
    public Biome generateBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.FROZEN_OCEAN;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {

    }

    @Nonnull
    @Override
    protected ItemChoice getBaseItem() {
        return new ItemChoice(Material.ICE);
    }

}
