package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.EarthOrbit;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import io.github.mooy1.infinitylib.config.ConfigUtils;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.RandomizedSet;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.entity.LivingEntity;
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

/**
 * Any alien world
 * 
 * @see EarthOrbit
 *
 * @author Seggan
 * @author Mooy1
 */
public abstract class AlienWorld extends CelestialWorld {

    /**
     * All alien worlds
     */
    private static final Map<World, AlienWorld> WORLDS = new HashMap<>();

    @Nullable
    public static AlienWorld getByWorld(@Nonnull World world) {
        return WORLDS.get(world);
    }

    @Nullable
    public static AlienWorld getByName(@Nonnull String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        } else {
            return getByWorld(world);
        }
    }
    
    public static void tickWorlds() {
        for (AlienWorld world : WORLDS.values()) {
            world.tickWorld();
        }
    }
    
    public static void tickAliens() {
        for (World world : WORLDS.keySet()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                Alien alien = Alien.getByEntity(entity);
                if (alien != null) {
                    alien.onMobTick(entity);
                }
            }
        }
    }
    
    @Nonnull
    public static Collection<AlienWorld> getEnabled() {
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
     * This world, only null if disabled
     */
    @Getter
    protected final World world;

    /**
     * Configuration
     */
    @Nonnull
    protected final WorldConfig config;
    
    public AlienWorld(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull CelestialType type,
                      @Nonnull ItemChoice choice, @Nonnull CelestialBody... celestialBodies) {
        
        super(name, orbit, type, choice, celestialBodies);
        
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
        
        // before
        beforeWorldLoad();
        
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

        // register
        WORLDS.put(world, this);
        
        // after
        afterWorldLoad(world);
        
        return world;
    }

    /**
     * Called before the world is loaded
     * 
     * use this to validate or initialize anything that is used in the chunk generator
     */
    protected void beforeWorldLoad() {
        // can be overridden
    }
    
    /**
     * Called after the world is loaded
     * 
     * use this to add any custom world settings you want
     */
    protected void afterWorldLoad(@Nonnull World world) {
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
