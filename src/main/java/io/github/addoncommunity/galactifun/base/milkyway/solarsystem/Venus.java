package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.world.WorldTerrain;
import io.github.addoncommunity.galactifun.api.universe.world.populators.LakePopulator;
import io.github.addoncommunity.galactifun.api.universe.world.populators.VolcanoPopulator;
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
public class Venus extends CelestialWorld {

    public Venus() {
        super("Venus", 67_621_000L, 177_700_000L, new Gravity(1.105), Material.RED_STAINED_GLASS,
                        new DayCycle(116.75), new WorldTerrain("Volcanic", 50, 50, 8, 0.02, 0.5, 0.3), new Atmosphere(
                    0,
                    false,
                    true,
                    true,
                    true,
                    World.Environment.NETHER,
                    new PotionEffectType[0],
                    new PotionEffectType[]{PotionEffectType.WITHER}
                ));
    }

    @Nonnull
    @Override
    public Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
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
    public Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.BASALT_DELTAS;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new VolcanoPopulator(115, Material.OBSIDIAN, Material.LAVA));
        populators.add(new LakePopulator(75, Material.LAVA));
    }
    
}
