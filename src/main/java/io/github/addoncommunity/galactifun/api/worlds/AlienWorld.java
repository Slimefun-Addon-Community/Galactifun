package io.github.addoncommunity.galactifun.api.worlds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.addoncommunity.galactifun.api.aliens.AlienManager;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.EarthOrbit;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.apache.commons.lang.Validate;

/**
 * Any alien world
 *
 * @author Seggan
 * @author Mooy1
 *
 * @see EarthOrbit
 */
public abstract class AlienWorld extends CelestialWorld {

    /**
     * All alien species that can spawn on this planet. Is {@link List} for shuffling purposes
     */
    @Nonnull
    private final List<Alien<?>> species = new ArrayList<>(4);

    @Nonnull
    protected final Map<Material, SlimefunItemStack> blockMappings = new EnumMap<>(Material.class);

    public AlienWorld(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull PlanetaryType type, @Nonnull ItemChoice choice) {
        super(name, orbit, type, choice);
    }

    @Nullable
    @Override
    protected World loadWorld() {
        Galactifun.inst().log(Level.INFO, "Loading planet " + this.name);
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

                    @Override
                    public Location getFixedSpawnLocation(@Nonnull World world, @Nonnull Random random) {
                        Block b = world.getHighestBlockAt(random.nextInt(), random.nextInt());
                        return b.getLocation();
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

        Galactifun.inst().registerListener(new Listener() {
            @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
            public void onBreak(BlockBreakEvent e) {
                Block b = e.getBlock();
                if (!blockMappings.containsKey(b.getType())) return;
                if (!b.getWorld().getUID().equals(world.getUID())) return;

                if (!Boolean.parseBoolean(BlockStorage.getLocationInfo(b.getLocation(), "placed"))) {
                    e.setDropItems(false);
                    b.getWorld().dropItemNaturally(
                            b.getLocation().add(0.5, 0.5, 0.5),
                            blockMappings.get(b.getType()).clone()
                    );
                }
            }

            @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
            public void onPlace(BlockPlaceEvent e) {
                Block b = e.getBlock();
                if (!blockMappings.containsKey(b.getType())) return;
                if (!b.getWorld().getUID().equals(world.getUID())) return;

                BlockStorage.addBlockInfo(b, "placed", Boolean.toString(true));
            }
        });

        // after
        afterWorldLoad(world);

        return world;
    }

    /**
     * To allow worlds to be made of {@link SlimefunItem}s without using huge amounts of {@link BlockStorage},
     * I invented block mappings. Simply pass a {@link Material} and a {@link SlimefunItemStack}, and any
     * generated {@code vanillaItem}s will drop {@code slimefunItem}s
     *
     * @implNote they work by not adding block data to <i>generated</i> blocks, but to <i>placed</i>
     * blocks in that world. Therefore, any block that doesn't have data is the Slimefun one, and
     * those that do are vanilla
     */
    public final void addBlockMapping(@Nonnull Material vanillaItem, @Nonnull SlimefunItemStack slimefunItem) {
        blockMappings.put(vanillaItem, slimefunItem);
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
    public final void addSpecies(@Nonnull Alien<?>... aliens) {
        this.species.addAll(Arrays.asList(aliens));
    }

    /**
     * Ticks the world every 5 seconds
     */
    void tickWorld(AlienManager manager) {
        // player effects
        for (Player p : getWorld().getPlayers()) {
            applyEffects(p);
        }
        
        // time
        this.dayCycle.tick(getWorld());

        // mob spawns
        if (!this.species.isEmpty()) {
            // shuffles the list so each alien has a fair chance of being first
            Collections.shuffle(this.species);

            Random rand = ThreadLocalRandom.current();
            int players = getWorld().getPlayers().size();
            int mobs = getWorld().getLivingEntities().size() - players;
            int max = players * manager.getMaxAliensPerPlayer();

            for (Alien<?> alien : this.species) {
                if ((mobs += alien.attemptSpawn(rand, getWorld())) > max) {
                    break;
                }
            }
        }
    }
    
    protected final void applyEffects(@Nonnull Player p) {
        this.gravity.applyGravity(p);
        this.atmosphere.applyEffects(p);
    }

}
