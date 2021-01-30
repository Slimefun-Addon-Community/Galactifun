package io.github.addoncommunity.galactifun.api;

import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;

import javax.annotation.Nonnull;

/**
 * A moon that orbits a planet
 *
 * @author Mooy1
 *
 */
public abstract class Moon extends CelestialObject {
    
    public Moon(@Nonnull String name, int distance, @Nonnull Gravity gravity, long surfaceArea, @Nonnull SolarType type, @Nonnull Atmosphere atmosphere) {
        super(name, distance, gravity, surfaceArea, type, atmosphere);
    }

}
