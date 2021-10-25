package io.github.addoncommunity.galactifun.api.worlds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.base.universe.earth.EarthOrbit;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

/**
 * Any alien world
 *
 * @author Seggan
 * @author Mooy1
 * @see EarthOrbit
 */
public abstract class AlienWorld extends PlanetaryWorld {

    public static final NamespacedKey CHUNK_VER_KEY = Galactifun.createKey("chunk_version");

    private final Map<Material, SlimefunItemStack> blockMappings = new EnumMap<>(Material.class);
    private final List<Alien<?>> species = new ArrayList<>();

    public AlienWorld(String name, PlanetaryType type, Orbit orbit, StarSystem orbiting, ItemStack baseItem,
                      DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    public AlienWorld(String name, PlanetaryType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                      DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Nullable
    @Override
    protected World loadWorld() {
        if (!getSetting("enabled", Boolean.class, enabledByDefault())) {
            return null;
        }

        Galactifun.log(Level.INFO, "Loading planet " + name());

        String worldName = "world_galactifun_" + this.id;

        // Load or create world
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
                        List<BlockPopulator> list = new ArrayList<>(1);
                        getPopulators(list);
                        list.add(new BlockPopulator() {
                            @Override
                            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk source) {
                                PersistentDataAPI.setString(source, CHUNK_VER_KEY,
                                        String.format("%s v%s", addon().getName(), addon().getPluginVersion()));
                            }
                        });
                        return list;
                    }

                    @Override
                    public Location getFixedSpawnLocation(@Nonnull World world, @Nonnull Random random) {
                        Block b = world.getHighestBlockAt(random.nextInt(), random.nextInt());
                        return b.getLocation();
                    }
                })
                .environment(atmosphere().environment())
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
        dayCycle().applyEffects(world);
        atmosphere().applyEffects(world);

        return world;
    }

    public final void addSpecies(@Nonnull Alien<?>... aliens) {
        for (Alien<?> alien : aliens) {
            if (alien.isRegistered()) {
                this.species.add(alien);
            } else {
                throw new IllegalStateException("You must register an alien before adding it to a world!");
            }
        }
    }

    protected final <T> T getSetting(@Nonnull String path, @Nonnull Class<T> clazz, T defaultValue) {
        return Galactifun.worldManager().getSetting(this, path, clazz, defaultValue);
    }

    /**
     * To allow worlds to be made of {@link SlimefunItem}s without using huge amounts of {@link BlockStorage},
     * I invented block mappings. Simply pass a {@link Material} and a {@link SlimefunItemStack}, and any
     * generated {@code vanillaItem}s will drop {@code slimefunItem}s
     */
    public final void addBlockMapping(@Nonnull Material vanillaItem, @Nonnull SlimefunItemStack slimefunItem) {
        this.blockMappings.put(vanillaItem, slimefunItem);
    }

    @Nullable
    public SlimefunItemStack getMappedItem(Block b) {
        return this.blockMappings.get(b.getType());
    }

    public boolean canSpawnVanillaMobs() {
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

    public final void applyEffects(@Nonnull Player p) {
        atmosphere().applyEffects(p);
    }

    public final void tickWorld() {
        World world = world();

        // time
        dayCycle().tick(world);

        // player effects
        for (Player p : world.getPlayers()) {
            gravity().applyGravity(p);
            if (p.getGameMode() == GameMode.SURVIVAL) {
                applyEffects(p);
            }
        }

        // time
        dayCycle().tick(world);

        // mob spawns
        if (!this.species.isEmpty() && !world.getPlayers().isEmpty()) {
            Random rand = ThreadLocalRandom.current();

            // shuffles the list so each alien has a fair chance of being first
            Collections.shuffle(this.species, rand);

            int players = world.getPlayers().size();
            int mobs = world.getLivingEntities().size() - players;
            int max = players * Galactifun.worldManager().maxAliensPerPlayer();

            for (Alien<?> alien : this.species) {
                if ((mobs += alien.attemptSpawn(rand, world)) > max) {
                    break;
                }
            }
        }
    }

}
