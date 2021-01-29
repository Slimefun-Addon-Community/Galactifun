package io.github.addoncommunity.galactifun.api;

import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;
import io.github.addoncommunity.galactifun.core.Registry;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.apache.commons.lang.Validate;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
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
    @Nonnull private final SolarType type;
    @Nonnull private final Atmosphere atmosphere;

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
                           @Nonnull SolarType type, @Nonnull Atmosphere atmosphere) {
        
        Validate.notNull(name);
        Validate.notNull(type);
        Validate.notNull(atmosphere);
        
        this.name = name;
        this.distance = distance;
        this.gravity = gravity;
        this.surfaceArea = surfaceArea;
        this.type = type;
        this.atmosphere = atmosphere;
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

        this.type.applyEffects(world);
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
    public final ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int x, int z, @Nonnull BiomeGrid biome) {
        ChunkData data = createChunkData(world);
        generateChunk(world, data, random, biome, x, z);
        return data;
    }
    
    protected abstract void generateChunk(@Nonnull World world, @Nonnull ChunkData chunk, @Nonnull Random random, @Nonnull BiomeGrid biome, int chunkX, int chunkZ);

    @Override
    public final int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof CelestialObject && ((CelestialObject) obj).name.equals(this.name);
    }

}
