package io.github.addoncommunity.galactifun.api.universe;

import javax.annotation.Nonnull;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.StarSystemType;
import io.github.addoncommunity.galactifun.util.ItemChoice;

/**
 * A star system filled with celestial objects
 *
 * @author Mooy1
 */
public final class StarSystem extends UniversalObject<CelestialBody> {
    
    public StarSystem(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull StarSystemType type, @Nonnull ItemChoice choice) {
        super(name, orbit, type, choice);
    }

    @Override
    public double getDistanceTo(@Nonnull UniversalObject<?> object) {
        if (object instanceof StarSystem system) {
            return Math.abs(this.orbit.getCurrentDistance() - system.orbit.getCurrentDistance());
        } else {
            return super.getDistanceTo(object);
        }
    }
}
