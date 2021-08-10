package io.github.addoncommunity.galactifun.api.worlds;

import java.util.Random;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
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

/**
 * A simple alien world
 *
 * @author Mooy1
 * @see Mars
 */
public abstract class SimpleAlienWorld extends AlienWorld {

    private SimplexOctaveGenerator generator;

    public SimpleAlienWorld(String name, PlanetaryType type, Orbit orbit, StarSystem orbiting, ItemStack baseItem,
                            DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    public SimpleAlienWorld(String name, PlanetaryType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                            DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Override
    protected final void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid,
                                       @Nonnull Random random, @Nonnull World world, int chunkX, int chunkZ) {

        if (this.generator == null) {
            this.generator = new SimplexOctaveGenerator(world, getOctaves());
            this.generator.setScale(getScale());
        }

        for (int x = 0, realX = chunkX << 4; x < 16; x++, realX++) {
            for (int z = 0, realZ = chunkZ << 4; z < 16; z++, realZ++) {

                double noise = generator.noise(realX, realZ, getFrequency(), getAmplitude(), true);

                if (smoothenTerrain()) {
                    noise *= noise;
                }

                // find max height
                double temp = getAverageHeight() + getMaxDeviation() * noise;
                int height = temp >= 0 ? (int) temp : (int) temp - 1;

                // y = 0, add bedrock and biome
                chunk.setBlock(x, 0, z, Material.BEDROCK);
                grid.setBiome(x, 0, z, getBiome());

                // y = 1 to height, generate and add biome
                int y = 1;
                while (y <= height) {
                    chunk.setBlock(x, y++, z, generateMaterial(random, x, y, z, height));
                    grid.setBiome(x, y, z, getBiome());
                }

                // y = height to 256, just add biome
                while (y < 256) {
                    grid.setBiome(x, y++, z, getBiome());
                }

                // more
                generateMore(chunk, generator, random, realX, realZ, x, z, height);
            }
        }
    }

    @Nonnull
    protected abstract Material generateMaterial(@Nonnull Random random, int x, int y, int z, int top);

    @Nonnull
    protected abstract Biome getBiome();

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
