package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.type.CelestialType;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * A simple alien world
 * 
 * @author Mooy1
 */
public abstract class SimpleAlienWorld extends AlienWorld {
    
    /**
     * Octave generator for this world, only null for disabled worlds
     */
    protected final SimplexOctaveGenerator generator;
    
    public SimpleAlienWorld(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull CelestialType type,
                            @Nonnull ItemChoice choice, @Nonnull CelestialBody... celestialBodies) {
        
        super(name, orbit, type, choice, celestialBodies);

        Validate.isTrue(getMaxDeviation() >= 0);
        Validate.isTrue(getAverageHeight() >= 0 && getAverageHeight() + getMaxDeviation() <= 256);
        Validate.isTrue(getOctaves() > 0);
        
        // testing
        if (this.world != null) {
            this.generator = new SimplexOctaveGenerator(this.world, getOctaves());
        } else {
            this.generator = null;
        }
    }

    @Override
    protected final void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid,
                                 @Nonnull Random random, @Nonnull World world, int chunkX, int chunkZ) {

        int height;
        int realX;
        int realZ;
        int x;
        int y;
        int z;

        for (x = 0, realX = chunkX << 4; x < 16; x++, realX++) {
            for (z = 0, realZ = chunkZ << 4; z < 16; z++, realZ++) {

                // find max height
                height = (int) Math.floor(getAverageHeight() + getMaxDeviation() *
                        this.generator.noise(realX, realZ, getFrequency(), getAmplitude(), true)
                );

                // y = 0, add bedrock and biome
                chunk.setBlock(x, 0, z, Material.BEDROCK);
                grid.setBiome(x, 0, z, getBiome());

                // y = 1 to height, generate and add biome
                for (y = 1 ; y <= height ; y++) {
                    chunk.setBlock(x, y, z, generateMaterial(random, x, y, z, height));
                    grid.setBiome(x, y, z, getBiome());
                }

                // y = height to 256, just add biome
                for (; y < 256 ; y++) {
                    grid.setBiome(x, y, z, getBiome());
                }
                
                // more
                generateMore(chunk, random, realX, realZ, x, z, height);
                
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
    protected void generateMore(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull Random random,
                                int realX, int realZ, int x, int z, int height) {
        
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
     * noise amplitude
     */
    protected double getAmplitude() {
        return 0.5;
    }

    /**
     * noise frequency
     */
    protected double getFrequency() {
        return 0.5;
    }
    
}
