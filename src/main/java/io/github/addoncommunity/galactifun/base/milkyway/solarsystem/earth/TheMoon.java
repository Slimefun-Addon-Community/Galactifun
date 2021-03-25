package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.world.SimpleAlienWorld;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * The moon
 *
 * @author Seggan
 * @author Mooy1
 */
public final class TheMoon extends SimpleAlienWorld {
    
    public TheMoon() {
        super("The Moon", Orbit.kilometers(382_500L), CelestialType.TERRESTRIAL, new ItemChoice(Material.ANDESITE));
    }
    
    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return DayCycle.EARTH_LIKE;
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return Atmosphere.NONE;
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.MOON_LIKE;
    }

    @Nonnull
    @Override
    protected Material generateMaterial(@Nonnull Random random, int x, int y, int z, int top) {
        if (random.nextFloat() > .02) {
            return Material.ANDESITE;
        } else {
            return Material.GOLD_ORE;
        }
    }

    @Nonnull
    @Override
    protected Biome getBiome() {
        return Biome.BADLANDS;
    }

    @Override
    protected int getAverageHeight() {
        return 50;
    }

}
