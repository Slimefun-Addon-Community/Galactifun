package io.github.addoncommunity.galactifun.api;

import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.GenerationType;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;

import javax.annotation.Nonnull;

/**
 * A moon that orbits a planet
 *
 * @author Mooy1
 *
 */
public abstract class Moon extends CelestialObject {
    
    public Moon(@Nonnull String name, long distance, int gravity, long surfaceArea, @Nonnull SolarType solarType, @Nonnull Atmosphere atmosphere, @Nonnull GenerationType generationType) {
        super(name, distance, gravity, surfaceArea, solarType, atmosphere, generationType);
    }

}
