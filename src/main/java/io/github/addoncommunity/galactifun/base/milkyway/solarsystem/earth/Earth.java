package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth;

import java.util.Objects;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.api.worlds.WorldManager;

/**
 * A class to connect the default earth world into the api
 *
 * @author Mooy1
 */
public final class Earth extends PlanetaryWorld {
    
    public Earth(StarSystem orbiting) {
        super("Earth", PlanetaryType.TERRESTRIAL, Orbit.kilometers(149_600_000L, 1D), orbiting,
                new ItemStack(Material.GRASS_BLOCK), DayCycle.EARTH_LIKE, Atmosphere.EARTH_LIKE, Gravity.EARTH_LIKE);
    }

    @Nonnull
    @Override
    public World loadWorld(WorldManager worldManager) {
        String name = Galactifun.inst().getConfig().getString("worlds.earth-name", "world");
        World world = new WorldCreator(Objects.requireNonNull(name)).createWorld(); // this will load the world as only the default world loads on startup
        if (world == null) {
            throw new IllegalStateException("Failed to read earth world name from config; no default world found!");
        } else {
            return world;
        }
    }

}
