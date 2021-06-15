package io.github.addoncommunity.galactifun.api.worlds;

import java.util.ArrayList;
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

import lombok.NonNull;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
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
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * Any alien world
 *
 * @author Seggan
 * @author Mooy1
 *
 * @see EarthOrbit
 */
public abstract class AlienWorld extends PlanetaryWorld {

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
    protected final World loadWorld() {
        Galactifun.inst().log(Level.INFO, "Loading Alien World " + getName());
        String worldName = "world_galactifun_" + getName().toLowerCase(Locale.ROOT).replace(' ', '_');

        // TODO implement disabling
        if (!enabledByDefault()) {
            return null;
        }

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
                .environment(getAtmosphere().getEnvironment())
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
        getDayCycle().applyEffects(world);
        getAtmosphere().applyEffects(world);

        return world;
    }

    public final void addSpecies(@NonNull Alien<?>... aliens) {
        for (Alien<?> alien : aliens) {
            if (alien.isRegistered()) {
                this.species.add(alien);
            } else {
                throw new IllegalStateException("You must register an alien before adding it to a world!");
            }
        }
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
    // TODO improve
    public final void addBlockMapping(@Nonnull Material vanillaItem, @Nonnull SlimefunItemStack slimefunItem) {
        this.blockMappings.put(vanillaItem, slimefunItem);
    }

    // TODO improve
    @Nullable
    SlimefunItemStack getMappedItem(Block b) {
        return this.blockMappings.get(b.getType());
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

    final void applyEffects(@Nonnull Player p) {
        getGravity().applyGravity(p);
        getAtmosphere().applyEffects(p);
    }

    final void tickWorld() {
        World world = getWorld();

        // time
        getDayCycle().tick(world);

        // player effects
        for (Player p : world.getPlayers()) {
            applyEffects(p);
        }

        // mob spawns
        if (!this.species.isEmpty()) {
            // shuffles the list so each alien has a fair chance of being first
            Collections.shuffle(this.species);

            Random rand = ThreadLocalRandom.current();
            int players = world.getPlayers().size();
            int mobs = world.getLivingEntities().size() - players;
            int max = players * getWorldManager().getMaxAliensPerPlayer();

            for (Alien<?> alien : this.species) {
                if ((mobs += alien.attemptSpawn(rand, world)) > max) {
                    break;
                }
            }
        }
    }

}
