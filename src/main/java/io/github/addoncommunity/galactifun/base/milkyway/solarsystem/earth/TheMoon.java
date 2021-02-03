package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth;

import io.github.addoncommunity.galactifun.api.universe.Moon;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.terrain.Terrain;
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
public final class TheMoon extends Moon {
    
    public TheMoon() {
        super("The Moon", 382_500L, 14_600_000L, Gravity.MOON_LIKE,
                Material.ANDESITE, DayCycle.EARTH_LIKE, Atmosphere.NONE, Terrain.SMOOTH);
    }
    
    @Nonnull
    @Override
    protected Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        if (random.nextFloat() > .03) {
            return Material.ANDESITE;
        } else {
            return Material.GOLD_ORE;
        }
    }

    @Nonnull
    @Override
    protected Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.BADLANDS;
    }

    @Override
    protected void getPopulators(@Nonnull List<BlockPopulator> populators) {
        
    }

}
