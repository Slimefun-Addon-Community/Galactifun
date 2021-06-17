package io.github.addoncommunity.galactifun.api.universe;

import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.StarSystemType;

/**
 * A star system filled with celestial objects
 *
 * @author Mooy1
 */
public final class StarSystem extends UniversalObject {

    public StarSystem(String name, StarSystemType type, Orbit orbit, Galaxy orbiting, ItemStack baseItem) {
        super(name, type, orbit, orbiting, baseItem);
    }

}
