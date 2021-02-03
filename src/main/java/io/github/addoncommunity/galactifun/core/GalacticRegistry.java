package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.api.mob.Alien;
import io.github.addoncommunity.galactifun.api.universe.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.Galaxy;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * A galactic registry utility class
 */
@UtilityClass
public final class GalacticRegistry {

    static final Map<String, Galaxy> GALAXIES = new HashMap<>();
    static final Map<String, StarSystem> STAR_SYSTEMS = new HashMap<>();
    static final Map<World, CelestialWorld> CELESTIAL_WORLDS = new HashMap<>();

    public static final Map<String, Alien> ALIENS = new HashMap<>();
    
    public static void register(@Nonnull Galaxy galaxy) {
        GALAXIES.put(galaxy.getName(), galaxy);
    }
    
    public static void register(@Nonnull StarSystem system) {
        STAR_SYSTEMS.put(system.getName(), system);
    }
    
    public static void register(@Nonnull World world, @Nonnull CelestialWorld object) {
        CELESTIAL_WORLDS.put(world, object);
    }

    public static void register(@Nonnull String id, @Nonnull Alien alien) {
        ALIENS.put(id, alien);
    }

    @Nullable
    public static Galaxy getGalaxy(@Nonnull String name) {
        return GALAXIES.get(name);
    }

    @Nullable
    public static StarSystem getStarSystem(@Nonnull String name) {
        return STAR_SYSTEMS.get(name);
    }

    @Nullable
    public static CelestialWorld getCelestialWorld(@Nonnull World world) {
        return CELESTIAL_WORLDS.get(world);
    }

    @Nullable
    public static CelestialWorld getCelestialWorld(@Nonnull String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        } else {
            return getCelestialWorld(world);
        }
    }

    @Nullable
    public static Alien getAlien(@Nonnull String id) {
        return ALIENS.get(id);
    }

    @Nullable
    public static Alien getAlien(@Nonnull Entity entity) {
        String id = PersistentDataAPI.getString(entity, Alien.KEY);
        return id == null ? null : getAlien(id);
    }
    
}
