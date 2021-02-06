package io.github.addoncommunity.galactifun.api.universe.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

/**
 * Type of celestial body
 * 
 * @author Mooy1
 */
@AllArgsConstructor
public enum CelestialType {

    /**
     * Orbit, asteroid belts, etc
     */
    SPACE("Space"),

    /**
     * A star
     */
    STAR("Star"),
    
    /**
     * Gaseous planets ex: jupiter
     */
    GAS_GIANT("Gas giant"),

    /**
     * Frozen planets ex: neptune
     */
    ICE_GIANT("Ice Giant"),
    
    /**
     * Mostly liquid planets
     */
    OCEANIC("Oceanic"),
    
    /**
     * Rocky/Solid planets ex: earth, mars, moon
     */
    TERRESTRIAL("Terrestrial"),

    /**
     * Unknown or not here
     */
    UNKNOWN("Unknown");
    
    @Getter @Nonnull
    private final String name;
    
}
