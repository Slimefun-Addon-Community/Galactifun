package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.api.CelestialObject;
import io.github.addoncommunity.galactifun.api.Galaxy;
import io.github.addoncommunity.galactifun.api.StarSystem;
import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public final class Registry {

    private static final Map<String, Galaxy> GALAXIES = new HashMap<>();
    private static final Map<String, StarSystem> STAR_SYSTEMS = new HashMap<>();
    private static final Map<String, CelestialObject> CELESTIAL_OBJECTS = new HashMap<>();
    
    public static void register(@Nonnull Galaxy galaxy) {
        GALAXIES.put(galaxy.getName(), galaxy);
    }
    
    public static void register(@Nonnull StarSystem system) {
        STAR_SYSTEMS.put(system.getName(), system);
    }
    
    public static void register(@Nonnull CelestialObject object) {
        CELESTIAL_OBJECTS.put(object.getWorld().getName(), object);
    }

    @Nonnull
    public static Galaxy getGalaxy(@Nonnull String name) {
        return GALAXIES.get(name);
    }

    @Nonnull
    public static StarSystem getStarSystem(@Nonnull String name) {
        return STAR_SYSTEMS.get(name);
    }  
    
    @Nonnull
    public static CelestialObject getCelestialObject(@Nonnull String worldName) {
        return CELESTIAL_OBJECTS.get(worldName);
    }
    
}
