package io.github.addoncommunity.galactifun.api;

import io.github.addoncommunity.galactifun.api.attributes.SolarType;
import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;

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
    
    private final List<Moon> moons;

    public Planet(@Nonnull String name, long distance, int gravity, long surfaceArea, @Nonnull SolarType type, @Nonnull Atmosphere atmosphere, @Nonnull Moon... moons) {
        super(name, distance, gravity, surfaceArea, type, atmosphere);
        this.moons = new ArrayList<>(Arrays.asList(moons));
    }
    
    public final void addMoons(@Nonnull Moon... moons) {
        this.moons.addAll(Arrays.asList(moons));
    }
    
}
