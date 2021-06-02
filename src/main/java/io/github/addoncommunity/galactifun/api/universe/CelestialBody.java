package io.github.addoncommunity.galactifun.api.universe;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.apache.commons.lang.Validate;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.util.ItemChoice;

/**
 * A celestial object
 * 
 * @author Mooy1
 */
@Getter
public abstract class CelestialBody extends UniversalObject<CelestialBody> {

    protected final DayCycle dayCycle;
    protected final Atmosphere atmosphere;
    protected final Gravity gravity;
    
    public CelestialBody(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull CelestialType type, @Nonnull ItemChoice choice) {
        super(name, orbit, type, choice);
        
        Validate.notNull(this.gravity = createGravity());
        Validate.notNull(this.dayCycle = createDayCycle());
        Validate.notNull(this.atmosphere = createAtmosphere());
    }
    
    @Nonnull
    protected abstract DayCycle createDayCycle();
    
    @Nonnull
    protected abstract Atmosphere createAtmosphere();
    
    @Nonnull
    protected abstract Gravity createGravity();

}
