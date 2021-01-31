package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.Moon;
import io.github.addoncommunity.galactifun.api.universe.Planet;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.terrain.Terrain;
import io.github.mooy1.infinitylib.PluginUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

/**
 * A class to connect the default earth world into the api
 *
 * @author Mooy1
 * 
 */
public final class Earth extends Planet {

    /**
     * The world used for earth
     */
    public static final World WORLD = getMainWorld();
    
    @Nonnull
    private static World getMainWorld() {
        World world;

        String worldName = Galactifun.getInstance().getConfig().getString("earth-world-name");

        if (worldName != null) {
            world = Bukkit.getWorld(worldName);

            if (world != null) {
                return world;
            }
        }

        PluginUtils.log(Level.WARNING, "Failed to read earth world name from config!");

        world = Bukkit.getWorld("world");

        if (world == null) {
            Bukkit.getServer().getPluginManager().disablePlugin(Galactifun.getInstance());
            throw new IllegalStateException("Failed to read earth world name from config, no default world found!");
        } else {
            PluginUtils.log(Level.WARNING, "Defaulting to world 'world'");
            return world;
        }
    }
    
    public Earth(@Nonnull TheMoon theMoon) {
        super("Earth", 91_565_000, 196_900_000, Gravity.EARTH_LIKE, Material.GRASS_BLOCK,
                DayCycle.EARTH_LIKE, Atmosphere.EARTH_LIKE, Terrain.HILLY_CAVERNS, theMoon);
    }

    @Nonnull
    @Override
    protected World setupWorld() {
        return WORLD;
    }

    @Nonnull
    @Override
    protected Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        throw new IllegalStateException("Earth shouldn't be generating blocks!");
    }

    @Nonnull
    @Override
    protected Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        throw new IllegalStateException("Earth shouldn't be generating biomes!");
    }

    @Override
    protected void getPopulators(@Nonnull List<BlockPopulator> populators) {
        throw new IllegalStateException("Earth shouldn't be generating populators!");
    }

}
