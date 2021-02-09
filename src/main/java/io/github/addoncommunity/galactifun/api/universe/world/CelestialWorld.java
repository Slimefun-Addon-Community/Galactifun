package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.type.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.world.terrain.AbstractTerrain;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import io.github.mooy1.infinitylib.config.ConfigUtils;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.RandomizedSet;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * A class representing any celestial object with a world
 *
 * @author Seggan
 * @author Mooy1
 */
public abstract class CelestialWorld extends AbstractCelestialWorld {

    /**
     * All celestial worlds
     */
    private static final Map<World, CelestialWorld> WORLDS = new HashMap<>();

    @Nullable
    public static CelestialWorld getByWorld(@Nonnull World world) {
        return WORLDS.get(world);
    }

    @Nullable
    public static CelestialWorld getByName(@Nonnull String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        } else {
            return getByWorld(world);
        }
    }
    
    @Nonnull
    public static Set<World> getWorlds() {
        return WORLDS.keySet();
    }
    
    @Nonnull
    public static Collection<CelestialWorld> getEnabled() {
        return WORLDS.values();
    }
    
    private static final double MIN_BORDER = 600D;
    private static final double MAX_BORDER = 30_000_000D;
    private static final int MAX_ALIENS = ConfigUtils.getInt("aliens.max-per-player", 1, 64, 12);
    
    /**
     * All alien species that can spawn on this planet
     */
    @Nonnull
    private final RandomizedSet<Alien> species = new RandomizedSet<>();
    
    /**
     * Average block height of the world, similar to sea level
     */
    @Getter
    private final int avgHeight;

    /**
     * This world, only null if disabled
     */
    @Getter
    protected final World world;

    /**
     * Configuration
     */
    @Nonnull
    protected final WorldConfig config;
    
    public CelestialWorld(@Nonnull String name, @Nonnull Orbit orbit, long surfaceArea, @Nonnull Gravity gravity,
                          @Nonnull Atmosphere atmosphere, @Nonnull DayCycle dayCycle, @Nonnull CelestialType type,
                          int avgHeight, @Nonnull AbstractTerrain terrain, @Nonnull ItemChoice choice,
                          @Nonnull CelestialBody... celestialBodies) {
        
        super(name, orbit, surfaceArea, gravity, dayCycle, type, atmosphere, choice, celestialBodies);
        
        Validate.isTrue(avgHeight >= 0 && avgHeight <= 256);
        Validate.notNull(terrain);
        
        this.avgHeight = avgHeight;
        this.terrain = terrain;
        
        String worldName = this.name.toLowerCase(Locale.ROOT).replace(' ', '_');
        
        this.config = WorldConfig.loadConfiguration(worldName, enabledByDefault());
        
        if (this.config.isEnabled()) {
            this.world = loadWorld(worldName);
        } else {
            this.world = null;
        }
    }
    
    @Nonnull
    private World loadWorld(@Nonnull String worldName) {
        
        // fetch or create world
        World world = new WorldCreator(worldName)
                .generator(new ChunkGenerator() {
                    @Nonnull
                    @Override
                    public ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int chunkX, int chunkZ, @Nonnull BiomeGrid grid) {
                        ChunkData chunk = createChunkData(world);
                        generateChunk(chunk, grid, random, world, chunkX, chunkZ);
                        return chunk;
                    }
                    @Nonnull
                    @Override
                    public List<BlockPopulator> getDefaultPopulators(@Nonnull World world) {
                        List<BlockPopulator> list = new ArrayList<>(4);
                        getPopulators(list);
                        return list;
                    }
                })
                .environment(this.atmosphere.getEnvironment())
                .createWorld();

        Validate.notNull(world, "There was an error loading the world for " + worldName);

        // border
        WorldBorder border = world.getWorldBorder();
        border.setCenter(0, 0);
        border.setSize(Math.min(MAX_BORDER, Math.max(MIN_BORDER, Math.sqrt(this.surfaceArea) * Earth.BORDER_SURFACE_RATIO)));

        // load effects
        this.dayCycle.applyEffects(world);
        this.atmosphere.applyEffects(world);

        // block storage
        if (BlockStorage.getStorage(world) == null) {
            new BlockStorage(world);
        }
        
        // setup
        setupWorld(world);

        // register
        WORLDS.put(world, this);
        
        return world;
    }
    
    protected void setupWorld(@Nonnull World world) {
        // can be overridden
    }
    
    /**
     * Generate a chunk
     */
    protected abstract void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid, 
                                          @Nonnull Random random, @Nonnull World world, int chunkX, int chunkZ);
    
    /**
     * Add all chunk populators to this list
     */
    public abstract void getPopulators(@Nonnull List<BlockPopulator> populators);

    /**
     * Adds a aliens species to this world
     */
    public final void addSpecies(@Nonnull Alien alien) {
        this.species.add(alien, alien.getChance());
    }
    
    /**
     * Ticks the world
     */
    public final void tickWorld() {
        // time
        this.dayCycle.applyTime(this.world);
        // player effects
        for (Player p : this.world.getPlayers()) {
            applyEffects(p);
        }
        // other stuff?
    }

    /**
     * All effects that should be applied to the player
     */
    public final void applyEffects(@Nonnull Player p) {
        // apply gravity
        this.gravity.applyGravity(p);
        // apply atmospheric effects
        this.atmosphere.applyEffects(p);
        // other stuff?
    }

    /**
     * Spawns aliens
     */
    public final void onMobSpawn(@Nonnull CreatureSpawnEvent e) {
        if (!this.species.isEmpty() && this.world.getLivingEntities().size() < this.world.getPlayers().size() * MAX_ALIENS) {
            this.species.getRandom().spawn(e.getLocation(), this.world);
        }
    }

    /**
     * Override and set to false if your world is not required/needed for your plugin to work.
     */
    protected boolean enabledByDefault() {
        return true;
    }

}
