package io.github.addoncommunity.galactifun.api.worlds;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.base.universe.Mars;
import io.github.addoncommunity.galactifun.util.GenUtils;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;

/**
 * A simple alien world
 *
 * @author Mooy1
 * @author Seggan
 * @see Mars
 */
public abstract class SimpleAlienWorld extends AlienWorld {

    private volatile SimplexOctaveGenerator generator;

    public SimpleAlienWorld(String name, PlanetaryType type, Orbit orbit, StarSystem orbiting, ItemStack baseItem,
                            DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    public SimpleAlienWorld(String name, PlanetaryType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                            DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Override
    protected final void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull Random random,
                                       @Nonnull WorldInfo world, int chunkX, int chunkZ) {

        ObjectIntPair<Material> top = getTop();
        int heightSub = top == null ? 0 : top.rightInt();

        for (int x = 0, realX = chunkX << 4; x < 16; x++, realX++) {
            for (int z = 0, realZ = chunkZ << 4; z < 16; z++, realZ++) {

                int height = getHeight(world, realX, realZ) - heightSub;

                // y = 1 to height, generate
                int y = 1;
                while (y <= height) {
                    chunk.setBlock(x, y++, z, generateMaterial(random, x, y, z, height));
                }

                // more
                generateMore(chunk, generator, random, realX, realZ, x, z, height);
            }
        }
    }

    @Override
    protected final void generateSurface(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull Random random, @Nonnull WorldInfo world, int chunkX, int chunkZ) {
        ObjectIntPair<Material> top = getTop();
        if (top != null) {
            Material material = top.left();
            int height = top.rightInt();

            for (int x = 0, realX = chunkX << 4; x < 16; x++, realX++) {
                for (int z = 0, realZ = chunkZ << 4; z < 16; z++, realZ++) {

                    int topY = getHeight(world, realX, realZ);

                    for (int y = topY; y > topY - height; y--) {
                        chunk.setBlock(x, y, z, material);
                    }
                }
            }
        }
    }

    private int getHeight(WorldInfo info, int x, int z) {
        if (this.generator == null) {
            this.generator = new SimplexOctaveGenerator(info.getSeed(), getOctaves());
            this.generator.setScale(getScale());
        }

        double noise = generator.noise(x, z, getFrequency(), getAmplitude(), true);

        if (smoothenTerrain()) {
            noise *= noise;
        }

        // find max height
        double temp = getAverageHeight() + getMaxDeviation() * noise;
        return temp >= 0 ? (int) temp : (int) temp - 1;
    }

    @Nonnull
    protected abstract Material generateMaterial(@Nonnull Random random, int x, int y, int z, int top);

    /**
     * Returns an {@link ObjectIntPair} of the top block material and the number of top blocks. Used for the
     * {@link #AlienWorld#generateSurface(ChunkGenerator.ChunkData, Random, WorldInfo, int, int)} method
     *
     * @return an {@link ObjectIntPair} of the top block material and the number of top blocks, or null if
     * there is none
     */
    @Nullable
    protected ObjectIntPair<Material> getTop() {
        return null;
    }

    @Nonnull
    protected abstract Biome getBiome();

    @Nonnull
    @Override
    protected final BiomeProvider getBiomeProvider() {
        return new GenUtils.SingleBiomeProvider(getBiome());
    }

    /**
     * Generate additional things after the main materials and biomes have been generated
     */
    protected void generateMore(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull SimplexOctaveGenerator generator,
                                @Nonnull Random random, int realX, int realZ, int x, int z, int height) {

    }

    /**
     * Average block height of the world, similar to sea level
     */
    protected int getAverageHeight() {
        return 75;
    }

    /**
     * Maximum y deviation
     */
    protected int getMaxDeviation() {
        return 35;
    }

    /**
     * Octave generator octaves
     */
    protected int getOctaves() {
        return 8;
    }

    /**
     * Octave generator scale
     */
    protected double getScale() {
        return 0.01;
    }

    /**
     * Octave generator amplitude
     */
    protected double getAmplitude() {
        return 0.5;
    }

    /**
     * Octave generator frequency
     */
    protected double getFrequency() {
        return 0.5;
    }

    /**
     * Square the noise before calculating height. This makes for smoother terrain.
     */
    protected boolean smoothenTerrain() {
        return false;
    }

}
