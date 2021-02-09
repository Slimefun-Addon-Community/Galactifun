package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth;

import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.type.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.world.terrain.Terrain;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * The moon
 *
 * @author Seggan
 * @author Mooy1
 */
public final class TheMoon extends CelestialWorld {
    
    public TheMoon() {
        super("The Moon", new Orbit(382_500L), 14_600_000L, Gravity.MOON_LIKE,
                Atmosphere.NONE, DayCycle.EARTH_LIKE, CelestialType.TERRESTRIAL, 80, 
                Terrain.SMOOTH, new ItemChoice(Material.ANDESITE)
        );
    }
    
    @Nonnull
    @Override
    public Material generate(@Nonnull Random random, @Nonnull ChunkGenerator.BiomeGrid biomeGrid, int x, int y, int z, int top) {
        if (random.nextFloat() > .03) {
            return Material.ANDESITE;
        } else {
            return Material.GOLD_ORE;
        }
    }

    @Override
    public void generateBiome(@Nonnull ChunkGenerator.BiomeGrid grid, int x, int y, int z) {
        grid.setBiome(x, y, z, Biome.BADLANDS);
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        
    }

}
