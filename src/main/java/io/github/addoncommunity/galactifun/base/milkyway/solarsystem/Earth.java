package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.Moon;
import io.github.addoncommunity.galactifun.api.Planet;
import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.attributes.Terrain;
import io.github.addoncommunity.galactifun.base.BaseRegistry;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * A class to connect the default earth world into the api
 *
 * @author Mooy1
 * 
 */
public final class Earth extends Planet {
    
    public Earth(@Nonnull Moon theMoon) {
        super("Earth", 91_565_000, 196_900_000, Gravity.EARTH_LIKE, DayCycle.NORMAL, Atmosphere.EARTH_LIKE, Terrain.HILLY_CAVES, theMoon);
    }

    @Nonnull
    @Override
    protected World setupWorld() {
        return BaseRegistry.EARTH_WORLD;
    }

    @Nonnull
    @Override
    protected Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        throw new IllegalStateException("Earth shouldn't be generating blocks!");
    }

    @Nonnull
    @Override
    protected Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        throw new IllegalStateException("Earth shouldn't be generating biomes!");
    }

    @Override
    protected void getPopulators(@Nonnull List<BlockPopulator> populators) {
        throw new IllegalStateException("Earth shouldn't be generating populators!");
    }

}
