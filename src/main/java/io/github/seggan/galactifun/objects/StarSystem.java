package io.github.seggan.galactifun.objects;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StarSystem {

    public static final StarSystem SOLAR_SYSTEM = new StarSystem(CelestialObject.MARS, CelestialObject.THE_MOON);
    
    private final List<CelestialObject> celestialObjects = new ArrayList<>();
    // TODO add stats of star like heat, size, special effects over planets, etc
    
    public StarSystem(@Nonnull CelestialObject... objects) {
        this.celestialObjects.addAll(Arrays.asList(objects));
    }
    
    public void addCelestialObject(@Nonnull CelestialObject... objects) {
        this.celestialObjects.addAll(Arrays.asList(objects));
    }
    
}
