package io.github.addoncommunity.galactifun.base.universe;

import java.util.Objects;

import javax.annotation.Nonnull;

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

/**
 * A class to connect the default earth world into the api
 *
 * @author Mooy1
 */
public final class Earth extends PlanetaryWorld {

    public Earth(String name, PlanetaryType type, Orbit orbit, StarSystem orbiting, ItemStack baseItem,
                 DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Nonnull
    @Override
    public World loadWorld() {
        String name = Galactifun.inst().getConfig().getString("worlds.earth-name");
        World world = new WorldCreator(Objects.requireNonNull(name)).createWorld(); // this will load the world as only the default world loads on startup
        if (world == null) {
            throw new IllegalStateException("Failed to read earth world name from config; no default world found!");
        } else {
            return world;
        }
    }

}
