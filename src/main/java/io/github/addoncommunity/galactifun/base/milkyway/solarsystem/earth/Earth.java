package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import io.github.mooy1.infinitylib.core.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import javax.annotation.Nonnull;

/**
 * A class to connect the default earth world into the api
 *
 * @author Mooy1
 */
public final class Earth extends CelestialWorld {
    
    private static final World WORLD = getMainWorld();
    
    @Nonnull
    private static World getMainWorld() {
        World world = Bukkit.getWorld(ConfigUtils.getString("worlds.earth-name", "world"));
        if (world == null) {
            throw new IllegalStateException("Failed to read earth world name from config; no default world found!");
        } else {
            return world;
        }
    }
    
    public Earth() {
        super("Earth", Orbit.kilometers(149_600_000L), CelestialType.TERRESTRIAL, new ItemChoice(Material.GRASS_BLOCK));
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

    @Override
    protected long createSurfaceArea() {
        return 509_600_000;
    }

}
