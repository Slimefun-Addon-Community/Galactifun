package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.world.WorldTerrain;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public final class EarthOrbit extends CelestialWorld {

    public EarthOrbit() {
        super("Earth Orbit", 0, 0, Gravity.ZERO, Material.BLACK_STAINED_GLASS_PANE,
                DayCycle.ETERNAL_NIGHT, WorldTerrain.VOID, Atmosphere.NONE, 0, avgHeight);
    }

    @Nonnull
    @Override
    public Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        return Material.AIR; // probably won't be called
    }

    @Nonnull
    @Override
    public Biome generateBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.THE_VOID; // probably won't be called
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        // TODO add comet and asteroid populators, maybe rocket debris
    }

}
