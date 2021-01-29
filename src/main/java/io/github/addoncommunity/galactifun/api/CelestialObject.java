package io.github.addoncommunity.galactifun.api;

import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.GenerationType;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;
import io.github.addoncommunity.galactifun.core.Registry;
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

    private static final long MAX_BORDER = 30_000_000L;
    private static final long MIN_BORDER = 600L;
    
    @Nonnull private final String name;
    @Nonnull private final World world;
    @Nonnull private final SolarType solarType;
    @Nonnull private final Atmosphere atmosphere;
    @Nonnull private final GenerationType generationType;
    @Nonnull private final List<BlockPopulator> populators = new ArrayList<>(2);

    /**
     * Distance in miles from the object that this it orbiting
     */
    private final long distance;
    
    /**
     * Gravity where earth is 0, negative for lower, positive for higher
     */
    private final int gravity;
    
    /**
     * Surface area
     */
    private final long surfaceArea;

    public CelestialObject(@Nonnull String name, long distance, int gravity, long surfaceArea,
                           @Nonnull SolarType solarType, @Nonnull Atmosphere atmosphere, @Nonnull GenerationType generationType) {
        
        Validate.notNull(name);
        Validate.notNull(solarType);
        Validate.notNull(atmosphere);
        Validate.notNull(generationType);
        
        this.name = name;
        this.distance = distance;
        this.gravity = gravity;
        this.surfaceArea = surfaceArea;
        this.solarType = solarType;
        this.atmosphere = atmosphere;
        this.generationType = generationType;
        this.world = setupWorld();
        
        Registry.register(this);
    }

    /**
     * Be careful if overriding.
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
        border.setSize(Math.max(MIN_BORDER, Math.min(MAX_BORDER, Math.sqrt(this.surfaceArea))));
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
        
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, this.generationType.octaves);
        generator.setScale(this.generationType.scale);
        
        int height;
        int startX = chunkX << 4;
        int startZ = chunkZ << 4;

        if (this.generationType.caveRatio == 0) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 0, z, Material.BEDROCK);

                    // find max height
                    height = (int) (this.generationType.minHeight + this.generationType.maxDeviation * (1 + generator.noise(
                            startX + x, startZ , this.generationType.frequency+ z, this.generationType.amplitude, true)
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
            double caveRatio = this.generationType.caveRatio / 2;

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlock(x, 0, z, Material.BEDROCK);

                    // find max height
                    height = (int) (this.generationType.minHeight + this.generationType.maxDeviation * (1 + generator.noise(
                            startX + x, startZ , this.generationType.frequency+ z, this.generationType.amplitude, true)
                    ));

                    // generate caves
                    for (int y = 1 ; y < height ; y++) {
                        double density = 1 + generator.noise(
                                startX + x,
                                y,
                                startZ + z,
                                this.generationType.frequency,
                                this.generationType.amplitude,
                                true
                        );

                        // Choose a narrow selection of blocks
                        if (density <= caveRatio) {
                            chunk.setBlock(x, y, z, Material.CAVE_AIR);
                        }
                    }

                    // generate the rest
                    for (int y = 1 ; y < height ; y++) {
                        if (chunk.getType(x, y, z) != Material.CAVE_AIR) {
                            chunk.setBlock(x, y, z, generateBlock(random, height, x, y, z));
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
        return this.populators;
    }
    
    public void addPopulator(@Nonnull BlockPopulator blockPopulator) {
        Validate.notNull(blockPopulator);
        this.populators.add(blockPopulator);
    }

    @Override
    public final int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof CelestialObject && ((CelestialObject) obj).name.equals(this.name);
    }

}
