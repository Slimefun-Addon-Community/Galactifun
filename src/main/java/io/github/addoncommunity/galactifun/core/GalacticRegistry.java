package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.api.universe.CelestialObject;
import io.github.addoncommunity.galactifun.api.universe.Galaxy;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import lombok.experimental.UtilityClass;
import org.bukkit.World;
import org.bukkit.event.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public final class GalacticRegistry {

    static final Map<String, Galaxy> GALAXIES = new HashMap<>();
    static final Map<String, StarSystem> STAR_SYSTEMS = new HashMap<>();
    static final Map<String, CelestialObject> CELESTIAL_OBJECTS = new HashMap<>();
    
    public static void register(@Nonnull Galaxy galaxy) {
        GALAXIES.put(galaxy.getName(), galaxy);
    }
    
    public static void register(@Nonnull StarSystem system) {
        STAR_SYSTEMS.put(system.getName(), system);
    }
    
    public static void register(@Nonnull CelestialObject object) {
        CELESTIAL_OBJECTS.put(object.getWorld().getName(), object);
    }

    @Nullable
    public static Galaxy getGalaxy(@Nonnull String name) {
        return GALAXIES.get(name);
    }

    @Nullable
    public static CelestialObject getCelestialObject(@Nonnull String worldName) {
        return CELESTIAL_OBJECTS.get(worldName);
    }

    @Nullable
    public static StarSystem getStarSystem(@Nonnull String name) {
        return STAR_SYSTEMS.get(name);
    }

    @Nullable
    public static CelestialObject getCelestialObject(@Nonnull World world) {
        return getCelestialObject(world.getName());
    }

    @Nullable
    public static CelestialObject getCelestialObject(@Nonnull PlayerEvent e) {
        return getCelestialObject(e.getPlayer().getWorld().getName());
    }
    
}
