package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.world.ACelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.world.WorldTerrain;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.util.logging.Level;

/**
 * A class to connect the default earth world into the api
 *
 * @author Mooy1
 */
public final class Earth extends ACelestialWorld {
    
    private static final World WORLD = getMainWorld();
    private static final long SURFACE_AREA = 196_900_000;
    public static final double BORDER_SURFACE_RATIO =  WORLD.getWorldBorder().getSize() / Math.sqrt(SURFACE_AREA);
    
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
        super("Earth", 149_600_000L, 196_900_000, Gravity.EARTH_LIKE, Material.GRASS_BLOCK,
                DayCycle.EARTH_LIKE, WorldTerrain.HILLY_CAVERNS, Atmosphere.EARTH_LIKE, theMoon);
    }

    @Nonnull
    @Override
    protected World createWorld() {
        return WORLD;
    }

}
