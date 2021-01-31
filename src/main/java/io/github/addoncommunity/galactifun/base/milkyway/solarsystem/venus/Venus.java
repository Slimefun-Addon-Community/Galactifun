package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.venus;

import io.github.addoncommunity.galactifun.api.universe.Planet;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * Class for Venus
 *
 * @author Seggan
 */
public class Venus extends Planet {

    private static final double MAX_DEVIATION = 50;
    private static final double MIN_HEIGHT = 50;

    public Venus() {
        super("Venus", 67_621_000L, 177_700_000L, new Gravity(0), DayCycle.ETERNAL_DAY, new Atmosphere(
            0,
            false,
            true,
            true,
            true,
            World.Environment.NETHER,
            new PotionEffectType[0],
            new PotionEffectType[]{PotionEffectType.WITHER}
        ), new Terrain(50, 50, 8, 0.02, 0.5, 0.3));
    }

    @Nonnull
    @Override
    protected Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        if (y > 75) {
            return Material.BLACKSTONE;
        } else if (y > 10) {
            return Material.BASALT;
        } else if (y > 8){
            return Material.YELLOW_TERRACOTTA;
        } else if (y > 0) {
            return Material.BASALT;
        }

        throw new IllegalArgumentException(String.valueOf(y));
    }

    @Nonnull
    @Override
    protected Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.BASALT_DELTAS;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new VenusVolcanoLavaPopulator());
        populators.add(new VenusLavaLakePopulator());
    }
}