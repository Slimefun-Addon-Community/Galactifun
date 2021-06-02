package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.core.structures.GalacticStructure;
import io.github.addoncommunity.galactifun.core.structures.StructureRegistry;
import io.github.addoncommunity.galactifun.core.structures.StructureRotation;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import io.github.addoncommunity.galactifun.util.Sphere;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * Class for the Saturnian moon Enceladus
 *
 * @author Seggan
 */
public final class Enceladus extends AlienWorld {

    private static final GalacticStructure CRYOVOLCANO = StructureRegistry.getGalactifunStructure("cryovolcano");
    private static final Sphere WATER_POCKET = new Sphere(Material.WATER);

    public Enceladus() {
        super("&bEnceladus", Orbit.kilometers(237_948L, 1), CelestialType.FROZEN, new ItemChoice(Material.ICE));
    }

    @Override
    protected void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid,
                                 @Nonnull Random random, @Nonnull World world, int chunkX, int chunkZ) {
        int x;
        int y;
        int z;
        for (x = 0; x < 16; x++) {
            for (z = 0; z < 16; z++) {

                chunk.setBlock(x, 0, z, Material.BEDROCK);
                grid.setBiome(x, 0, z, Biome.FROZEN_OCEAN);

                for (y = 1; y <= 30; y++) {
                    chunk.setBlock(x, y, z, Material.PACKED_ICE);
                    grid.setBiome(x, y, z, Biome.FROZEN_OCEAN);
                }

                for (; y <= 60; y++) {
                    chunk.setBlock(x, y, z, Material.BLUE_ICE);
                    grid.setBiome(x, y, z, Biome.FROZEN_OCEAN);
                }

                for (; y < 256; y++) {
                    grid.setBiome(x, y, z, Biome.FROZEN_OCEAN);
                }
            }
        }
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new BlockPopulator() {

            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk source) {
                double rand = random.nextDouble();
                if (rand < .03) {
                    if (rand < .15) {
                        CRYOVOLCANO.paste(source.getBlock(4, 61, 4), StructureRotation.DEFAULT);
                    } else {
                        WATER_POCKET.generate(source.getBlock(8, random.nextInt(40) + 5, 8), 3, 3);
                    }
                }
            }
        });
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
        return Gravity.relativeToEarth(0.0113);
    }

    @Override
    public boolean canSpawnVanillaMobs() {
        return true;
    }
}
