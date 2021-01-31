package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A planet that could hold moons
 * 
 * @author Mooy1
 * 
 */
public abstract class Planet extends CelestialObject {
    
    private final List<Moon> moons = new ArrayList<>();

    public Planet(@Nonnull String name, long distance, long surfaceArea, @Nonnull Gravity gravity, @Nonnull DayCycle dayCycle,
                  @Nonnull Atmosphere atmosphere, @Nonnull Terrain terrain, @Nonnull Moon... moons) {
        super(name, distance, surfaceArea, gravity, dayCycle, atmosphere, terrain);
        addMoons(moons);
    }

    public final void addMoons(@Nonnull Moon... moons) {
        this.moons.addAll(Arrays.asList(moons));
    }
    
}
