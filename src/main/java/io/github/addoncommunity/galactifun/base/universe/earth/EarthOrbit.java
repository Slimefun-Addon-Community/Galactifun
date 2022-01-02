package io.github.addoncommunity.galactifun.base.universe.earth;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.util.GenUtils;
import io.github.addoncommunity.galactifun.util.Sphere;

public final class EarthOrbit extends AlienWorld {

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
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                if (random.nextInt(10) == 0) {
                    int x = random.nextInt(2) + 7;
                    int y = random.nextInt(224) + 16;
                    int z = random.nextInt(2) + 7;
                    switch (random.nextInt(3)) {
                        case 0 -> EarthOrbit.this.asteroid.generate(chunk.getBlock(x, y, z), 5, 2);
                        case 1 -> EarthOrbit.this.comet.generate(chunk.getBlock(x, y, z), 5, 2);
                        case 2 -> chunk.getBlock(x, y, z).setType(Material.IRON_BLOCK);
                    }
                }
            }
        });
    }

}
