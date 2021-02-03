package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.world.AWorldTerrain;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public final class Space extends CelestialWorld {

    public Space() {
        super("Space", 0, 0, Gravity.ZERO, Material.BLACK_STAINED_GLASS_PANE,
                DayCycle.ETERNAL_NIGHT, new AWorldTerrain("Void") {
                    @Override
                    protected void generateChunk(@Nonnull CelestialWorld celestialWorld, int chunkX, int chunkZ, @Nonnull Random random, @Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull World world) {
                        // add nothing
                    }
                }, Atmosphere.NONE);
    }

    @Nonnull
    @Override
    public Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        return Material.AIR;
    }

    @Nonnull
    @Override
    public Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.THE_VOID;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        // TODO add comet and asteroid populators
    }

}
