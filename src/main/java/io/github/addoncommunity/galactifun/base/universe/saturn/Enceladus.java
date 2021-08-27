package io.github.addoncommunity.galactifun.base.universe.saturn;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.structures.Structure;
import io.github.addoncommunity.galactifun.api.structures.StructureRotation;
import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.util.Sphere;

/**
 * Class for the Saturnian moon Enceladus
 *
 * @author Seggan
 */
public final class Enceladus extends AlienWorld {

    private final Structure cryoVolcano = Structure.get(Galactifun.instance(), "cryovolcano");
    private final Sphere waterPocket = new Sphere(Material.WATER);

    public Enceladus(String name, PlanetaryType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                     DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Override
    protected void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid,
                                 @Nonnull Random random, @Nonnull World world, int chunkX, int chunkZ) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

                chunk.setBlock(x, 0, z, Material.BEDROCK);
                grid.setBiome(x, 0, z, Biome.FROZEN_OCEAN);

                int y = 1;
                while (y <= 30) {
                    chunk.setBlock(x, y++, z, Material.PACKED_ICE);
                    grid.setBiome(x, y, z, Biome.FROZEN_OCEAN);
                }

                while (y <= 60) {
                    chunk.setBlock(x, y++, z, Material.BLUE_ICE);
                    grid.setBiome(x, y, z, Biome.FROZEN_OCEAN);
                }

                while (y < 256) {
                    grid.setBiome(x, y++, z, Biome.FROZEN_OCEAN);
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
                        Enceladus.this.cryoVolcano.paste(source.getBlock(4, 61, 4), StructureRotation.DEFAULT);
                    } else {
                        Enceladus.this.waterPocket.generate(source.getBlock(8, random.nextInt(40) + 5, 8), 3, 3);
                    }
                }
            }
        });
    }

    @Override
    public boolean canSpawnVanillaMobs() {
        return true;
    }

}
