package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.EarthOrbit;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import io.github.thebusybiscuit.slimefun4.api.events.WaypointCreateEvent;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Any alien world
 *
 * @author Seggan
 * @author Mooy1
 * @see EarthOrbit
 */
public abstract class AlienWorld extends CelestialWorld {

    /**
     * All enabled alien worlds
     */
    private static final Map<World, AlienWorld> WORLDS = new HashMap<>();

    @Nullable
    public static AlienWorld getByWorld(@Nonnull World world) {
        return WORLDS.get(world);
    }

    @Nonnull
    public static Collection<AlienWorld> getEnabled() {
        return WORLDS.values();
    }
    
    private static final int MAX_ALIENS = Galactifun.inst().getConfigInt("aliens.max-per-player", 4, 64);

    /**
     * All alien species that can spawn on this planet. Is {@link List} for shuffling purposes
     */
    @Nonnull
    private final List<Alien> species = new ArrayList<>(4);

    /**
     * This world, only null if disabled
     */
    @Getter
    private World world;

    public AlienWorld(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull CelestialType type, @Nonnull ItemChoice choice) {
        super(name, orbit, type, choice);
    }

    @Nullable
    @Override
    protected World loadWorld() {
        String worldName = "world_galactifun_" + this.name.toLowerCase(Locale.ROOT).replace(' ', '_');

        // TODO implement disabling
        if (!enabledByDefault()) {
            return null;
        }

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
                        List<BlockPopulator> list = new ArrayList<>(0);
                        getPopulators(list);
                        return list;
                    }

                })
                .environment(this.atmosphere.getEnvironment())
                .createWorld();
        
        Validate.notNull(world, "There was an error loading the world for " + worldName);
        
        if (world.getEnvironment() == World.Environment.THE_END) {
            // Prevents ender dragon spawn using portal, surrounds portal with bedrock
            world.getBlockAt(0, 0, 0).setType(Material.END_PORTAL);
            world.getBlockAt(0, 1, 0).setType(Material.BEDROCK);
            world.getBlockAt(1, 0, 0).setType(Material.BEDROCK);
            world.getBlockAt(-1, 0, 0).setType(Material.BEDROCK);
            world.getBlockAt(0, 0, 1).setType(Material.BEDROCK);
            world.getBlockAt(0, 0, -1).setType(Material.BEDROCK);
        }
        
        // load effects
        this.dayCycle.applyEffects(world);
        this.atmosphere.applyEffects(world);

        // register
        WORLDS.put(world, this);

        // after
        afterWorldLoad(world);

        return this.world = world;
    }

    /**
     * Called before the world is loaded, while this is being registered
     *
     * use this to validate or initialize anything that is used in the chunk generator
     */
    protected void beforeWorldLoad() {
        // can be overridden
    }

    /**
     * Called after the world is loaded, while this is being registered
     *
     * use this to add any custom world settings you want
     */
    protected void afterWorldLoad(@Nonnull World world) {
        // can be overridden
    }

    protected boolean canSpawnVanillaMobs() {
        return false;
    }

    /**
     * Override and set to false if your world is not required/needed for your plugin to work.
     */
    protected boolean enabledByDefault() {
        return true;
    }

    /**
     * Generate a chunk
     */
    protected abstract void generateChunk(@Nonnull ChunkGenerator.ChunkData chunk, @Nonnull ChunkGenerator.BiomeGrid grid,
                                          @Nonnull Random random, @Nonnull World world, int chunkX, int chunkZ);

    /**
     * Add all chunk populators to this list
     */
    protected abstract void getPopulators(@Nonnull List<BlockPopulator> populators);

    /**
     * Adds alien species to this world
     */
    public final void addSpecies(@Nonnull Alien... aliens) {
        this.species.addAll(Arrays.asList(aliens));
    }

    /**
     * Ticks the world every 5 seconds
     */
    private void tickWorld() {
        // player effects
        for (Player p : this.world.getPlayers()) {
            applyEffects(p);
        }
        
        // time
        this.dayCycle.tick(this.world);

        // mob spawns
        if (!this.species.isEmpty()) {
            // shuffles the list so each alien has a fair chance of being first
            Collections.shuffle(this.species);

            Random rand = ThreadLocalRandom.current();
            int players = this.world.getPlayers().size();
            int mobs = this.world.getLivingEntities().size() - players;
            int max = players * MAX_ALIENS;

            for (Alien alien : this.species) {
                if (mobs > max) {
                    break;
                }
                int spawned = 0;
                for (Chunk chunk : this.world.getLoadedChunks()) {
                    if (rand.nextInt(100) > alien.getSpawnChance() || spawned >= alien.getMaxAliensPerGroup()) {
                        continue;
                    }

                    Block b = this.world.getHighestBlockAt(rand.nextInt(16) + chunk.getX() << 4, rand.nextInt(16) + chunk.getZ() << 4).getRelative(0, 1, 0);

                    // currently doesn't allow for aquatic aliens
                    if (b.getType().isAir() && alien.getSpawnInLightLevel(b.getLightLevel())) {
                        alien.spawn(b.getLocation().add(alien.getSpawnOffset()), this.world);
                        spawned++;
                    }
                }
            }
        }
    }
    
    private void applyEffects(@Nonnull Player p) {
        this.gravity.applyGravity(p);
        this.atmosphere.applyEffects(p);
    }

    static {

        // world ticker
        Galactifun.inst().scheduleRepeatingSync(() -> {
            for (AlienWorld world : WORLDS.values()) {
                world.tickWorld();
            }
        }, 100);

        // alien ticker
        Galactifun.inst().scheduleRepeatingSync(() -> {
            for (Alien alien : Alien.ALIENS.values()) {
                alien.onUniqueTick();
            }

            for (World world : WORLDS.keySet()) {
                for (LivingEntity entity : world.getLivingEntities()) {
                    Alien alien = Alien.getByEntity(entity);
                    if (alien != null) {
                        alien.onMobTick(entity);
                    }
                }
            }
        }, Galactifun.inst().getConfigInt("aliens.tick-interval", 1, 20));

        // world listener
        Galactifun.inst().registerListener(new Listener() {

            // remove old effects and apply new effects
            @EventHandler
            public void onPlanetChange(@Nonnull PlayerChangedWorldEvent e){
                AlienWorld object = getByWorld(e.getFrom());
                if (object != null) {
                    object.getGravity().removeGravity(e.getPlayer());
                }
                object = getByWorld(e.getPlayer().getWorld());
                if (object != null) {
                    object.applyEffects(e.getPlayer());
                }
            }

            // apply effects
            @EventHandler
            public void onPlanetJoin(@Nonnull PlayerJoinEvent e) {
                AlienWorld object = getByWorld(e.getPlayer().getWorld());
                if (object != null) {
                    object.applyEffects(e.getPlayer());
                }
            }

            // don't allow teleportation by any means other than this addon
            @EventHandler
            public void onPlayerTeleport(@Nonnull PlayerTeleportEvent e) {
                if (!e.getPlayer().hasPermission("galactifun.admin") && e.getTo() != null && e.getTo().getWorld() != null) {
                    AlienWorld world = getByWorld(e.getTo().getWorld());
                    if (world != null) {
                        e.setCancelled(true);
                        // TODO we should add ways to 'fast travel' to worlds that are super expensive so that people can build bases there
                    }
                }
            }

            // cancel natural mobs
            @EventHandler
            public void onCreatureSpawn(@Nonnull CreatureSpawnEvent e) {
                if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
                    AlienWorld world = getByWorld(e.getEntity().getWorld());
                    if (world != null && !world.canSpawnVanillaMobs()) {
                        e.setCancelled(true);
                    }
                }
            }

            // cancel waypoints
            @EventHandler
            public void onWaypointCreate(@Nonnull WaypointCreateEvent e) {
                if (WORLDS.containsKey(e.getPlayer().getWorld())) {
                    e.setCancelled(true);
                }
            }

            // crops
            @EventHandler
            public void onCropGrow(@Nonnull BlockGrowEvent e) {
                Block block = e.getBlock();
                AlienWorld world = getByWorld(block.getWorld());
                if (world != null) {
                    int attempts = world.getAtmosphere().getGrowthAttempts();
                    if (attempts != 0 && SlimefunTag.CROPS.isTagged(block.getType())) {
                        BlockData data = block.getBlockData();
                        if (data instanceof Ageable ageable) {
                            ageable.setAge(ageable.getAge() + attempts);
                            block.setBlockData(ageable);
                        }
                    }
                }
            }
            
        });
    }

}
