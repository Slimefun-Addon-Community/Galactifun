package io.github.addoncommunity.galactifun.base.universe.saturn;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.structures.Structure;
import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.FlatWorld;
import io.github.addoncommunity.galactifun.util.Sphere;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;

/**
 * Class for the Saturnian moon Enceladus
 *
 * @author Seggan
 */
public final class Enceladus extends FlatWorld {

    private final Structure cryovolcano = Structure.get(Galactifun.instance(), "cryovolcano");
    private final Sphere waterPocket = new Sphere(Material.WATER);

    public Enceladus(String name, PlanetaryType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                     DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Nonnull
    @Override
    protected Int2ObjectSortedMap<Material> getLayers() {
        return new Int2ObjectLinkedOpenHashMap<>() {{
            put(30, Material.PACKED_ICE);
            put(60, Material.BLUE_ICE);
        }};
    }

    @Nonnull
    @Override
    protected Biome getBiome() {
        return Biome.FROZEN_OCEAN;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        /*
        populators.add(new BlockPopulator() {
            @Override
            public void populate(@Nonnull WorldInfo worldInfo, @Nonnull Random random, int x, int z, @Nonnull LimitedRegion region) {
                double rand = random.nextDouble();
                if (rand < .03) {
                    int chunkX = region.getCenterChunkX() << 4;
                    int chunkZ = region.getCenterChunkZ() << 4;

                    if (random.nextDouble() < .75) {
                        Enceladus.this.cryovolcano.paste(source.getBlock(4, 61, 4), StructureRotation.DEFAULT);
                    } else {
                        Enceladus.this.waterPocket.generate(source.getBlock(8, random.nextInt(40) + 5, 8), 3, 3);
                    }
                }
            }
        });
         */
    }

    @Override
    public boolean canSpawnVanillaMobs() {
        return true;
    }

}
