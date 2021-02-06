package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

/**
 * A celestial object
 * 
 * @author Mooy1
 */
public abstract class CelestialBody extends UniversalObject<CelestialBody> {
    
    @Nonnull 
    protected final DayCycle dayCycle;
    @Nonnull 
    protected final Atmosphere atmosphere;
    @Nonnull 
    protected final Gravity gravity;
    @Nonnull
    private final CelestialType type;
    
    /**
     * Surface area in square meters
     */
    protected final long surfaceArea;
    
    public CelestialBody(@Nonnull String name, @Nonnull Orbit orbit, long surfaceArea, @Nonnull Gravity gravity,
                         @Nonnull DayCycle dayCycle, @Nonnull CelestialType type, @Nonnull Atmosphere atmosphere,
                         @Nonnull CelestialBody... celestialBodies) {
        super(name, orbit, celestialBodies);

        Validate.notNull(dayCycle);
        Validate.notNull(atmosphere);
        Validate.notNull(type);
        Validate.notNull(gravity);
        
        this.type = type;
        this.gravity = gravity;
        this.surfaceArea = surfaceArea;
        this.dayCycle = dayCycle;
        this.atmosphere = atmosphere;
    }
    
    @Override
    @OverridingMethodsMustInvokeSuper
    protected void getItemStats(@Nonnull List<String> stats) {
        stats.add("&7Surface Area: " + this.surfaceArea + " blocks");
        stats.add("&7Gravity: " + this.gravity.getPercent() + "%");
        stats.add("&7Oxygen: " + this.atmosphere.getOxygenPercentage() + "%");
        stats.add("&7Type: " + this.type.getName());
        stats.add("&7Day Length: " + this.dayCycle.getDayLength());
    }

}
