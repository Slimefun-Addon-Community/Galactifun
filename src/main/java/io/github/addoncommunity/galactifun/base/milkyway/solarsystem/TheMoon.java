package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.Moon;
import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.Terrain;
import io.github.addoncommunity.galactifun.api.attributes.DayCycle;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public final class TheMoon extends Moon {
    
    public TheMoon() {
        super("The Moon", 238_900, -3, 14_600_000L, DayCycle.NORMAL, Atmosphere.MOON_LIKE, Terrain.FLAT_NO_CAVE);
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
