package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;

import javax.annotation.Nonnull;

/**
 * A moon that orbits a planet
 *
 * @author Mooy1
 *
 */
public abstract class Moon extends CelestialObject {
    
    public Moon(@Nonnull String name, long distance, long surfaceArea, @Nonnull Gravity gravity,
                @Nonnull DayCycle dayCycle, @Nonnull Atmosphere atmosphere, @Nonnull Terrain terrain) {
        super(name, distance, surfaceArea, gravity, dayCycle, atmosphere, terrain);
    }

}
