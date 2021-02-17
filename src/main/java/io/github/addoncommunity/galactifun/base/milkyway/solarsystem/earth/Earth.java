package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import io.github.mooy1.infinitylib.ConfigUtils;
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
    public static final long SURFACE_AREA = 509_600_000;
    public static final double BORDER_SURFACE_RATIO =  WORLD.getWorldBorder().getSize() / Math.sqrt(SURFACE_AREA);
    
    @Nonnull
    private static World getMainWorld() {
        World world = Bukkit.getWorld(ConfigUtils.getString("worlds.earth-name"));
        if (world == null) {
            throw new IllegalStateException("Failed to read earth world name from config, no default world found!");
        } else {
            return world;
        }
    }
    
    public Earth() {
        super("Earth", Orbit.kilometers(149_600_000L), CelestialType.TERRESTRIAL, new ItemChoice(Material.GRASS_BLOCK));
    }

    @Nonnull
    @Override
    public World getWorld() {
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
        return SURFACE_AREA;
    }

}
