package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.Planet;
import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;
import org.bukkit.World;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.Random;

public class Venus extends Planet {

    private static final double MAX_DEVIATION = 40;
    private static final double MIN_HEIGHT = 35;

    public Venus() {
        super("Venus", 67_621_000L, 0, 177_700_000L, SolarType.NORMAL, new Atmosphere(
            0,
            false,
            true,
            true,
            true,
            World.Environment.NORMAL,
            new PotionEffectType[0],
            new PotionEffectType[]{PotionEffectType.WITHER}
        ));
    }

    @Override
    protected void generateChunk(@Nonnull World world, @Nonnull ChunkData chunk, @Nonnull Random random, @Nonnull BiomeGrid biome, int chunkX, int chunkZ) {

    }
}
