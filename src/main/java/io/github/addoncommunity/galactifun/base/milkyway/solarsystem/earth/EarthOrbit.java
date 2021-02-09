package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.type.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import io.github.addoncommunity.galactifun.core.util.Sphere;
import io.github.addoncommunity.galactifun.core.util.Util;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public final class EarthOrbit extends AlienWorld {

    private static final Sphere COMET = new Sphere(Material.ICE, Material.PACKED_ICE, Material.BLUE_ICE);
    private static final Sphere ASTEROID = new Sphere(Material.STONE, Material.COBBLESTONE, Material.ANDESITE);
    
    public EarthOrbit() {
        super("Earth Orbit", new Orbit(24000), CelestialType.SPACE, new ItemChoice(Material.BLACK_STAINED_GLASS));
    }
    
    @Override
    protected void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid,
                                 @Nonnull Random random, @Nonnull World world, int chunkX, int chunkZ) {
        int x;
        int y;
        int z;
        for (x = 0 ; x < 16 ; x++) {
            for (y = 0 ; y < 256 ; y++) {
                for (z = 0 ; z < 16 ; z++) {
                    grid.setBiome(x, y, z, Biome.THE_VOID);
                }
            }
        }
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                int rand = random.nextInt(50);
                if (rand < 5) { // 10 % to gen
                    int x = Util.randomFrom(7, 8, random);
                    int y = Util.randomFrom(14, 251, random);
                    int z = Util.randomFrom(7, 8, random);
                    if (rand == 0) { // 2 % debris
                        chunk.getBlock(x, y, z).setType(Material.IRON_BLOCK);
                    } else if (rand == 1) { // 2 % comet
                        COMET.generate(random ,chunk, x, y, z, 5, 2);
                    } else { // 6 % asteroid
                        ASTEROID.generate(random ,chunk, x, y, z, 5, 2);
                    }
                }
            }
        });
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return DayCycle.EARTH_LIKE;
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return Atmosphere.NONE;
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.ZERO;
    }

    @Override
    protected long createSurfaceArea() {
        return Earth.SURFACE_AREA * 20;
    }

}
