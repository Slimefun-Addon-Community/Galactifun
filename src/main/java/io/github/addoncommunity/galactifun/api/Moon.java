package io.github.addoncommunity.galactifun.api;

import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.Terrain;
import io.github.addoncommunity.galactifun.api.attributes.DayCycle;

import javax.annotation.Nonnull;

/**
 * A moon that orbits a planet
 *
 * @author Mooy1
 *
 */
public abstract class Moon extends CelestialObject {
    
    public Moon(@Nonnull String name, long distance, int gravity, long surfaceArea, @Nonnull DayCycle solarType, @Nonnull Atmosphere atmosphere, @Nonnull Terrain generationType) {
        super(name, distance, gravity, surfaceArea, solarType, atmosphere, generationType);
    }

}
