package io.github.addoncommunity.galactifun.api.universe.types;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Type of celestial body
 * 
 * @author Mooy1
 */
@AllArgsConstructor
public enum CelestialType implements UniversalType {

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
    FROZEN("Frozen"),
    
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
    
    @Getter
    @Nonnull
    private final String name;
    
}
