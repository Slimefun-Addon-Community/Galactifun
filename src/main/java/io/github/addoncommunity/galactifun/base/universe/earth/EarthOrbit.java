package io.github.addoncommunity.galactifun.base.universe.earth;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.api.worlds.OrbitWorld;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.base.BaseUniverse;
import io.github.addoncommunity.galactifun.util.GenUtils;
import io.github.addoncommunity.galactifun.util.Sphere;

public final class EarthOrbit extends AlienWorld implements OrbitWorld {

    private final Sphere comet = new Sphere(Material.ICE, Material.PACKED_ICE, Material.BLUE_ICE);
    private final Sphere asteroid = new Sphere(Material.STONE, Material.COBBLESTONE, Material.ANDESITE);

    public EarthOrbit(String name, PlanetaryType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                      DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Override
    protected void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull Random random, @Nonnull WorldInfo world, int chunkX, int chunkZ) {
        // nop
    }

    @Nonnull
    @Override
    protected ChunkGenerator replaceChunkGenerator(@Nonnull ChunkGenerator defaultGenerator) {
        return new ChunkGenerator() {
            @Nonnull
            @Override
            public BiomeProvider getDefaultBiomeProvider(@Nonnull WorldInfo worldInfo) {
                return new GenUtils.SingleBiomeProvider(Biome.THE_VOID);
            }
        };
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull WorldInfo world, @Nonnull Random random, int cx, int cz, @Nonnull LimitedRegion region) {
                if (random.nextInt(10) == 0) {
                    int x = random.nextInt(2) + 7;
                    int y = random.nextInt(224) + 16;
                    int z = random.nextInt(2) + 7;
                    Location l = new Location(
                            region.getWorld(),
                            (region.getCenterChunkX() << 4) + x,
                            y,
                            (region.getCenterChunkZ() << 4) + z
                    );
                    switch (random.nextInt(3)) {
                        case 0 -> EarthOrbit.this.asteroid.generate(l, region, 5, 2);
                        case 1 -> EarthOrbit.this.comet.generate(l, region, 5, 2);
                        case 2 -> region.setType(l, Material.IRON_BLOCK);
                    }
                }
            }
        });
    }

    @Nonnull
    @Override
    public PlanetaryWorld getPlanet() {
        return BaseUniverse.EARTH;
    }

}
