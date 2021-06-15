package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth;

import java.util.Objects;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.CelestialWorld;

/**
 * A class to connect the default earth world into the api
 *
 * @author Mooy1
 */
public final class Earth extends CelestialWorld {
    
    private static final World WORLD = getMainWorld();
    
    @Nonnull
    private static World getMainWorld() {
        // TODO test
        String name = Galactifun.inst().getConfig().getString("worlds.earth-name", "world");
        World world = new WorldCreator(Objects.requireNonNull(name)).createWorld(); // this will load the world as only the default world loads on startup
        if (world == null) {
            throw new IllegalStateException("Failed to read earth world name from config; no default world found!");
        } else {
            return world;
        }
    }
    
    public Earth() {
        super("Earth", Orbit.kilometers(149_600_000L, 1D), PlanetaryType.TERRESTRIAL, new ItemChoice(Material.GRASS_BLOCK));
    }

    @Nonnull
    @Override
    public World loadWorld() {
        return WORLD;
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return DayCycle.EARTH_LIKE;
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return Atmosphere.EARTH_LIKE;
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.EARTH_LIKE;
    }
}
