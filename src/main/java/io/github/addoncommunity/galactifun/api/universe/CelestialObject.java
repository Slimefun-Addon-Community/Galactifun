package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
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
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.potion.PotionEffectType;

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
public abstract class CelestialObject extends ChunkGenerator {

    /**
     * Ratio of earth's border to it's surface area, used to calculate other celestial object's borders relative to earth
     */
    private static final double EARTH_BORDER_RATIO = BaseRegistry.EARTH_WORLD.getWorldBorder().getSize() / 196_900_000;

    /**
     * Minimum border size
     */
    private static final double MIN_BORDER = 600D;
    
    @Getter @Nonnull private final String name;
    @Getter @Nonnull private final World world;
    @Nonnull private final DayCycle solarType;
    @Nonnull private final Atmosphere atmosphere;
    @Nonnull private final Terrain terrain;
    @Nonnull private final Gravity gravity;

    /**
     * Distance in miles from the object that this it orbiting
     */
    @Getter private final long distance;

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
        
    }

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

        GalacticRegistry.register(this);
        
        return world;
    }
    
    public void tickWorld() {

        // time
        
        
        // player effects
        for (Player p : this.world.getPlayers()) {
            applyEffects(p);
        }

        // other stuff?
    }
    
    public void applyEffects(@Nonnull Player p) {
        // apply gravity
        this.gravity.applyGravity(p);

        // apply atmospheric effects TODO needs to be improved a lot
        for (PotionEffectType effectType : this.atmosphere.getNormalEffects()) {
            effectType.createEffect(120, 0).apply(p);
        }

        // other stuff?
    }

    @Nonnull
    @Override
    public final ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int chunkX, int chunkZ, @Nonnull BiomeGrid grid) {
        ChunkData chunk = createChunkData(world);
        this.terrain.generateChunkData(world, random, chunkX, chunkZ, this::getBiome, this::generateBlock, grid, chunk);
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
