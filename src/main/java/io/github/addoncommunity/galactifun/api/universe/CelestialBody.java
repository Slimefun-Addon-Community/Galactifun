package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import lombok.Getter;
import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A celestial object
 * 
 * @author Mooy1
 */
public abstract class CelestialBody extends UniversalObject<CelestialBody> {
    
    @Getter
    @Nonnull 
    protected final DayCycle dayCycle;

    @Getter
    @Nonnull 
    protected final Atmosphere atmosphere;

    @Getter
    @Nonnull 
    protected final Gravity gravity;
    
    /**
     * Surface area in square kilometers
     */
    @Getter
    protected final double surfaceArea;
    
    public CelestialBody(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull CelestialType type,
                         @Nonnull ItemChoice choice, @Nonnull CelestialBody... celestialBodies) {
        super(name, orbit, type, choice, celestialBodies);
        
        this.gravity = createGravity();
        Validate.notNull(this.gravity);
        
        this.dayCycle = createDayCycle();
        Validate.notNull(this.dayCycle);
        
        this.atmosphere = createAtmosphere();
        Validate.notNull(this.atmosphere);

        this.surfaceArea = createSurfaceArea();
        Validate.isTrue(this.surfaceArea > 0, "Surface area must be greater than 0!");
        
    }
    
    @Nonnull
    protected abstract DayCycle createDayCycle();
    
    @Nonnull
    protected abstract Atmosphere createAtmosphere();
    
    @Nonnull
    protected abstract Gravity createGravity();

    /**
     * @return Surface area of the planet in square kilometers
     */
    protected abstract long createSurfaceArea();
    
    @Override
    protected final void getItemStats(@Nonnull List<String> stats) {
        stats.add("&bSurface Area: &7" + this.surfaceArea + " square kilometers");
        stats.add("&bGravity: &7" + this.gravity.getPercent() + "%");
        stats.add("&bOxygen: &7" + this.atmosphere.getOxygenPercentage() + "%");
        stats.add("&6Day Cycle: &e" + this.dayCycle.getDayLength());
    }

}
