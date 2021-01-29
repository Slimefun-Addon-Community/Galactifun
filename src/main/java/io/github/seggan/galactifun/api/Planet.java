package io.github.seggan.galactifun.api;

import io.github.seggan.galactifun.api.attributes.Atmosphere;
import io.github.seggan.galactifun.api.attributes.SolarType;

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
