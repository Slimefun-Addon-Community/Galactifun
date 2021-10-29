package io.github.addoncommunity.galactifun.api.universe;

import javax.annotation.Nonnull;

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

    @Override
    public double distanceTo(@Nonnull UniversalObject other) {
        if (this.orbitLevel > other.orbitLevel) {
            return other.orbit().currentDistance() + distanceTo(other.orbiting());
        } else if (this.orbitLevel == other.orbitLevel) {
            return Math.abs(this.orbit().currentDistance() - other.orbit().currentDistance());
        } else {
            return super.distanceTo(other);
        }
    }

}
