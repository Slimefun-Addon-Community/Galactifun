package io.github.addoncommunity.galactifun.api;

import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.attributes.Terrain;
import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.core.GalacticRegistry;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * A class representing any celestial object (planet, asteroid, moon, etc)
 *
 * @author Seggan
 * @author Mooy1
 *
 */
@Getter
public abstract class CelestialObject extends ChunkGenerator {

    /**
     * Ratio of earth's border to it's surface area, used to calculate other celestial object's borders relative to earth
     */
    private static final double EARTH_BORDER_RATIO = BaseRegistry.EARTH_WORLD.getWorldBorder().getSize() / 196_900_000;

    /**
     * Minimum border size
     */
    private static final double MIN_BORDER = 600D;
    
    @Nonnull private final String name;
    @Nonnull private final World world;
    @Nonnull private final DayCycle solarType;
    @Nonnull private final Atmosphere atmosphere;
    @Nonnull private final Terrain terrain;
    @Nonnull private final Gravity gravity;

    /**
     * Distance in miles from the object that this it orbiting
     */
    private final long distance;

    /**
     * Surface area in square meters
     */
    private final long surfaceArea;
    
    public CelestialObject(@Nonnull String name, long distance, long surfaceArea, @Nonnull Gravity gravity,
                           @Nonnull DayCycle solarType, @Nonnull Atmosphere atmosphere, @Nonnull Terrain terrain) {
        
        Validate.notNull(name);
        Validate.notNull(solarType);
        Validate.notNull(atmosphere);
        Validate.notNull(terrain);
        
        this.name = name;
        this.distance = distance;
        this.gravity = gravity;
        this.surfaceArea = surfaceArea;
        this.solarType = solarType;
        this.atmosphere = atmosphere;
        this.terrain = terrain;
        this.world = setupWorld();
        
        GalacticRegistry.register(this);
    }

    /**
     * Be careful when overriding.
     */
    protected World setupWorld() {
        // will fetch the world if its already been loaded
        World world = new WorldCreator(this.name.toLowerCase(Locale.ROOT).replace(' ', '_'))
                .generator(this)
                .environment(this.atmosphere.getEnvironment())
                .createWorld();

        Validate.notNull(world, "There was an error loading the world for " + this.name);

        this.solarType.applyEffects(world);
        this.atmosphere.applyEffects(world);

        WorldBorder border = world.getWorldBorder();
        border.setSize(Math.max(MIN_BORDER, EARTH_BORDER_RATIO * this.surfaceArea));
        border.setCenter(0, 0);
        // TODO maybe change up border damage stuff?

        if (BlockStorage.getStorage(world) == null) {
            new BlockStorage(world);
        }
        
        return world;
    }

    @Nonnull
    @Override
    public final ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int chunkX, int chunkZ, @Nonnull BiomeGrid grid) {
        ChunkData chunk = createChunkData(world);
        
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, this.terrain.octaves);
        generator.setScale(this.terrain.scale);
        
        int height;
        int startX = chunkX << 4;
        int startZ = chunkZ << 4;

        if (this.terrain.caveRatio == 0) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 0, z, Material.BEDROCK);

                    // find max height
                    height = (int) (this.terrain.minHeight + this.terrain.maxDeviation * (1 + generator.noise(
                            startX + x,
                            startZ + z,
                            this.terrain.frequency,
                            this.terrain.amplitude,
                            true)
                    ));

                    // generate the rest
                    for (int y = 1 ; y < height ; y++) {
                        chunk.setBlock(x, y, z, generateBlock(random, height, x, y, z));
                    }

                    // set biome
                    Biome biome = getBiome(random, chunkX, chunkZ);
                    for (int y = 0 ; y < 256 ; y++) {
                        grid.setBiome(x, y, z, biome);
                    }
                }
            }
        } else {
            double caveRatio = this.terrain.caveRatio * 2;

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 0, z, Material.BEDROCK);

                    // find max height
                    height = (int) (this.terrain.minHeight + this.terrain.maxDeviation * (1 + generator.noise(
                            startX + x, startZ + z, this.terrain.frequency, this.terrain.amplitude, true)
                    ));

                    int top = height - 1;

                    // generate caves
                    for (int y = 1 ; y < height ; y++) {
                        double density = 1 + generator.noise(
                                startX + x,
                                y,
                                startZ + z,
                                this.terrain.caveFrequency,
                                this.terrain.caveAmplitude,
                                true
                        );

                        // Choose a narrow selection of blocks
                        if (density <= caveRatio) {
                            chunk.setBlock(x, y, z, Material.CAVE_AIR);
                        } else {
                            chunk.setBlock(x, y, z, generateBlock(random, top, x, y, z));
                        }
                    }
                    
                    // set biome
                    Biome biome = getBiome(random, chunkX, chunkZ);
                    for (int y = 0 ; y < 256 ; y++) {
                        grid.setBiome(x, y, z, biome);
                    }
                }
            }
        }

        return chunk;
    }
    
    @Nonnull
    protected abstract Material generateBlock(@Nonnull Random random, int top, int x, int y, int z);
    
    @Nonnull
    protected abstract Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ);

    @Nonnull
    @Override
    public final List<BlockPopulator> getDefaultPopulators(@Nonnull World world) {
        List<BlockPopulator> populators = new ArrayList<>(2);
        getPopulators(populators);
        return populators;
    }
    
    protected abstract void getPopulators(@Nonnull List<BlockPopulator> populators);

    @Override
    public final int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof CelestialObject && ((CelestialObject) obj).name.equals(this.name);
    }

}
