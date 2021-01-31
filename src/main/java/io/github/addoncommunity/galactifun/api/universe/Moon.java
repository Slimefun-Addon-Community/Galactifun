package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import org.bukkit.Material;

import javax.annotation.Nonnull;

/**
 * A moon that orbits a planet
 *
 * @author Mooy1
 */
public abstract class Moon extends CelestialWorld {

    public Moon(@Nonnull String name, long distance, long surfaceArea, @Nonnull Gravity gravity,@Nonnull Material material,
                @Nonnull DayCycle dayCycle, @Nonnull Atmosphere atmosphere, @Nonnull Terrain terrain) {
        super(name, distance, surfaceArea, gravity, material, dayCycle, terrain, atmosphere);
    }

}
