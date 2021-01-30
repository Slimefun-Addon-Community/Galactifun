package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.Galaxy;
import io.github.addoncommunity.galactifun.api.Moon;
import io.github.addoncommunity.galactifun.api.Planet;
import io.github.addoncommunity.galactifun.api.StarSystem;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Earth;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.Mars;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.TheMoon;
import io.github.mooy1.infinitylib.PluginUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.util.logging.Level;

/**
 * Registry of constants for the base celestial objects
 */
@UtilityClass
public final class BaseRegistry {

    public static final World EARTH_WORLD = getMainWorld();
    
    public static final Moon THE_MOON = new TheMoon();

    public static final Planet MARS = new Mars();
    public static final Planet EARTH = new Earth(THE_MOON);
    
    public static final StarSystem SOLAR_SYSTEM = new StarSystem("Solar System", EARTH, MARS);

    public static final Galaxy MILKY_WAY = new Galaxy("Milky Way", SOLAR_SYSTEM);
    
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

    public static void setup() {
        // just loading static fields for now
    }

}
