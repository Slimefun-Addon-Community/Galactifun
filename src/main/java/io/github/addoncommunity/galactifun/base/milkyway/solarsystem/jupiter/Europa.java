package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.jupiter;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public final class Europa extends AlienWorld {

    public Europa() {
        super("&bEuropa", new Orbit(671_100), CelestialType.FROZEN, new ItemChoice(Material.ICE));
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return DayCycle.ETERNAL_NIGHT;
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return Atmosphere.NONE;
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.metersPerSec(1.315);
    }

    @Override
    protected long createSurfaceArea() {
        return 30_612_893;
    }

    @Override
    protected void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, @Nonnull Random random, @Nonnull World world, int chunkX, int chunkZ) {
        int x;
        int y;
        int z;
        for (x = 0; x < 16; x++) {
            for (z = 0; z < 16; z++) {

                chunk.setBlock(x, 0, z, Material.BEDROCK);
                grid.setBiome(x, 0, z, Biome.FROZEN_OCEAN);

                for (y = 1 ; y <= 30 ; y++) {
                    chunk.setBlock(x, y, z, Material.PACKED_ICE);
                    grid.setBiome(x, y, z, Biome.FROZEN_OCEAN);
                }

                for (; y <= 60 ; y++) {
                    chunk.setBlock(x, y, z, Material.ICE);
                    grid.setBiome(x, y, z, Biome.FROZEN_OCEAN);
                }

                for (; y < 256 ; y++) {
                    grid.setBiome(x, y, z, Biome.FROZEN_OCEAN);
                }
            }
        }
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {

    }
}
