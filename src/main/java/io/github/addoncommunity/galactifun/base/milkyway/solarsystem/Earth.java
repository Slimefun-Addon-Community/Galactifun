package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.Moon;
import io.github.addoncommunity.galactifun.api.Planet;
import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.GenerationType;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

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

    public Earth(@Nonnull Moon theMoon) {
        super("Earth", 91_565_000, 0, 196_900_000,
                SolarType.NORMAL, Atmosphere.EARTH_LIKE, GenerationType.FLAT_NO_CAVE, theMoon);
    }

    @Nonnull
    @Override
    protected World setupWorld() {
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

    @Nonnull
    @Override
    protected Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        return Material.STONE; // wont get called
    }

    @Nonnull
    @Override
    protected Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.THE_VOID; // wont get called
    }

}
