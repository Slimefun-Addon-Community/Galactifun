package io.github.addoncommunity.galactifun.api.worlds;

import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.base.universe.jupiter.Europa;
import io.github.addoncommunity.galactifun.util.GenUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMaps;
import it.unimi.dsi.fastutil.ints.IntIterator;

/**
 * An {@link AlienWorld} that {@literal is...} flat
 *
 * @author Seggan
 * @see Europa
 */
public abstract class FlatWorld extends AlienWorld {

    private volatile Int2ObjectMap<Material> top = null;
    private volatile Int2ObjectMap<Material> bottom = null;

    public FlatWorld(String name, PlanetaryType type, Orbit orbit, StarSystem orbiting, ItemStack baseItem, DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    public FlatWorld(String name, PlanetaryType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem, DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    /**
     * Gets the layers of this world. The returned value is a sorted map of ints and {@link Material},
     * with each int being the top bound (inclusive) of the material's generation. See the {@link Europa}
     * implementation for an example. Or, if you need one in Javadoc,
     * <pre>
     *     protected Int2ObjectSortedMap<Material> getLayers() {
     *         return new Int2ObjectLinkedOpenHashMap<>() {{
     *             put(30, Material.PACKED_ICE);
     *             put(60, Material.ICE);
     *         }};
     *     }
     * </pre>
     *
     * @return the layers of this world
     */
    @Nonnull
    protected abstract Int2ObjectSortedMap<Material> getLayers();

    /**
     * Gets the biome of this flat world
     *
     * @return the biome of this world
     */
    @Nonnull
    protected abstract Biome getBiome();

    @Override
    protected final void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull Random random, @Nonnull WorldInfo world, int chunkX, int chunkZ) {
        apportionLayers();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int y = getBedrockLayer() + 1;
                IntIterator heights = this.top.keySet().intIterator();
                while (heights.hasNext() && y < world.getMaxHeight()) {
                    int height = heights.nextInt();
                    Material material = this.top.get(height);
                    while (y <= height) {
                        chunk.setBlock(x, y, z, material);
                        y++;
                    }
                }
            }
        }
    }

    @Override
    protected final void generateSurface(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull Random random, @Nonnull WorldInfo world, int chunkX, int chunkZ) {
        apportionLayers();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int y = getBedrockLayer() + 1;
                IntIterator heights = this.bottom.keySet().intIterator();
                while (heights.hasNext() && y < world.getMaxHeight()) {
                    int height = heights.nextInt();
                    Material material = this.bottom.get(height);
                    while (y <= height) {
                        chunk.setBlock(x, y, z, material);
                        y++;
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    protected final BiomeProvider getBiomeProvider() {
        return new GenUtils.SingleBiomeProvider(getBiome());
    }

    private void apportionLayers() {
        if (top == null) {
            Int2ObjectSortedMap<Material> layers = getLayers();

            Int2ObjectSortedMap<Material> newTop = new Int2ObjectLinkedOpenHashMap<>();
            IntIterator iter = layers.keySet().intIterator();

            iter.skip(layers.size() / 2 + 1);
            iter.forEachRemaining(i -> newTop.put(i, layers.get(i)));

            this.top = Int2ObjectSortedMaps.unmodifiable(newTop);
        }
        if (bottom == null) {
            Int2ObjectSortedMap<Material> layers = getLayers();

            Int2ObjectSortedMap<Material> newBottom = new Int2ObjectLinkedOpenHashMap<>();
            IntIterator iter = layers.keySet().intIterator();

            int amount = layers.size() / 2 + 1;
            for (int i = 0; i < amount; i++) {
                int layer = iter.nextInt();
                newBottom.put(layer, layers.get(layer));
            }

            this.bottom = Int2ObjectSortedMaps.unmodifiable(newBottom);
        }
    }

}
