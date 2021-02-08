package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.world.Terrain;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import io.github.addoncommunity.galactifun.core.util.Sphere;
import io.github.addoncommunity.galactifun.core.util.Util;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public final class EarthOrbit extends CelestialWorld {

    private static final Sphere COMET = new Sphere(Material.ICE, Material.PACKED_ICE, Material.BLUE_ICE);
    private static final Sphere ASTEROID = new Sphere(Material.STONE, Material.COBBLESTONE, Material.ANDESITE);
    
    public EarthOrbit() {
        super("Earth Orbit", new Orbit(37000), 1_000_000_000, Gravity.ZERO, Atmosphere.NONE, DayCycle.ETERNAL_NIGHT,
                CelestialType.SPACE, 0, Terrain.VOID, new ItemChoice(Material.BLACK_STAINED_GLASS)
        );
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

}
