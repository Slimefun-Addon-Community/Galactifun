package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.world.WorldTerrain;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public final class Space extends CelestialWorld {

    public Space() {
        super("Space", 0, 0, new Gravity(0),Material.BLACK_STAINED_GLASS_PANE,
                DayCycle.ETERNAL_NIGHT, WorldTerrain.HILLY_CAVERNS, Atmosphere.NONE);
    }

    @Nonnull
    @Override
    protected Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        return Material.AIR;
    }

    @Nonnull
    @Override
    protected Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.THE_VOID;
    }

    @Override
    protected void getPopulators(@Nonnull List<BlockPopulator> populators) {
        // TODO add comet and asteroid populators
    }

}
