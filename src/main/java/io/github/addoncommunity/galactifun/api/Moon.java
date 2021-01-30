package io.github.addoncommunity.galactifun.api;

import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.attributes.Terrain;

import javax.annotation.Nonnull;

/**
 * A moon that orbits a planet
 *
 * @author Mooy1
 *
 */
public abstract class Moon extends CelestialObject {


    public Moon(@Nonnull String name, long distance, long surfaceArea, @Nonnull Gravity gravity,
                @Nonnull DayCycle solarType, @Nonnull Atmosphere atmosphere, @Nonnull Terrain terrain) {
        super(name, distance, surfaceArea, gravity, solarType, atmosphere, terrain);
    }

}
