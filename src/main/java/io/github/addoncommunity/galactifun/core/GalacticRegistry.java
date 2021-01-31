package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.api.universe.CelestialWorld;
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
    static final Map<String, CelestialWorld> CELESTIAL_WORLDS = new HashMap<>();
    
    public static void register(@Nonnull Galaxy galaxy) {
        GALAXIES.put(galaxy.getName(), galaxy);
    }
    
    public static void register(@Nonnull StarSystem system) {
        STAR_SYSTEMS.put(system.getName(), system);
    }
    
    public static void register(@Nonnull World world, @Nonnull CelestialWorld object) {
        CELESTIAL_WORLDS.put(world.getName(), object);
    }

    @Nullable
    public static Galaxy getGalaxy(@Nonnull String name) {
        return GALAXIES.get(name);
    }

    @Nullable
    public static CelestialWorld getCelestialObject(@Nonnull String worldName) {
        return CELESTIAL_WORLDS.get(worldName);
    }

    @Nullable
    public static StarSystem getStarSystem(@Nonnull String name) {
        return STAR_SYSTEMS.get(name);
    }

    @Nullable
    public static CelestialWorld getCelestialObject(@Nonnull World world) {
        return getCelestialObject(world.getName());
    }

    @Nullable
    public static CelestialWorld getCelestialObject(@Nonnull PlayerEvent e) {
        return getCelestialObject(e.getPlayer().getWorld().getName());
    }
    
}
