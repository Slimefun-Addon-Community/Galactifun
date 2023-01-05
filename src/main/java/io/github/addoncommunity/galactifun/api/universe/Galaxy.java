package io.github.addoncommunity.galactifun.api.universe;

import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.GalaxyType;

/**
 * A galaxy filled with star systems
 *
 * @author Mooy1
 */
public final class Galaxy extends UniversalObject {

    public Galaxy(String name, String id, GalaxyType type, Orbit orbit, TheUniverse orbiting, ItemStack baseItem) {
        super(name, id, type, orbit, orbiting, baseItem);
    }

}
