package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.Planet;
import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.logging.Level;

/**
 * A class to connect the default earth world into the api
 * 
 * @author Mooy1
 * 
 */
public final class Earth extends Planet {

    public Earth(@Nonnull TheMoon theMoon) {
        super("Earth", 91_565_000, 0, 196_900_000, SolarType.NORMAL, Atmosphere.EARTH_LIKE, theMoon);
    }

    @Nonnull
    @Override
    protected World setupWorld() {
        World world;
        
        String worldName = Galactifun.getInstance().getConfig().getString("earth-world-name");
        
        if (worldName != null) {
            world = Bukkit.getWorld(worldName);
            
            if (world != null) {
                PluginUtils.log("Using world " + worldName + " as earth");
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

    @Override
    protected void generateChunk(@Nonnull World world, @Nonnull ChunkData chunk, @Nonnull Random random, @Nonnull BiomeGrid biome, int chunkX, int chunkZ) {
        // wont be called
    }

}
