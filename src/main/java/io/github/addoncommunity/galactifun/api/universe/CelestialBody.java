package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.type.CelestialType;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import lombok.Getter;
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
    protected final double surfaceArea;
    
    public CelestialBody(@Nonnull String name, @Nonnull Orbit orbit, long surfaceArea, @Nonnull Gravity gravity,
                         @Nonnull DayCycle dayCycle, @Nonnull CelestialType type, @Nonnull Atmosphere atmosphere,
                         @Nonnull ItemChoice choice, @Nonnull CelestialBody... celestialBodies) {
        super(name, orbit, type, choice, celestialBodies);

        Validate.notNull(dayCycle);
        Validate.notNull(atmosphere);
        Validate.notNull(type);
        Validate.notNull(gravity);
        
        this.gravity = gravity;
        this.surfaceArea = surfaceArea / 1000000D;
        this.dayCycle = dayCycle;
        this.atmosphere = atmosphere;
    }
    
    @Override
    @OverridingMethodsMustInvokeSuper
    protected void getItemStats(@Nonnull List<String> stats) {
        stats.add("&bSurface Area: &7" + this.surfaceArea + " square kilometers");
        stats.add("&bGravity: &7" + this.gravity.getPercent() + "%");
        stats.add("&bOxygen: &7" + this.atmosphere.getOxygenPercentage() + "%");
        stats.add("&6Day Cycle: &e" + this.dayCycle.getDayLength());
    }

}
