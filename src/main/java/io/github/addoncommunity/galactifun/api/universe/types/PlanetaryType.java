package io.github.addoncommunity.galactifun.api.universe.types;

import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;

/**
 * Type of an {@link PlanetaryObject}
 *
 * @author Mooy1
 */
public final class PlanetaryType extends UniversalType {

    /**
     * Orbit, asteroid belts, etc
     */
    public static final PlanetaryType SPACE = new PlanetaryType("Space");

    /**
     * Gaseous planets ex: jupiter
     */
    public static final PlanetaryType GAS_GIANT = new PlanetaryType("Gas giant");

    /**
     * Frozen planets ex: neptune
     */
    public static final PlanetaryType FROZEN = new PlanetaryType("Frozen");

    /**
     * Mostly liquid planets
     */
    public static final PlanetaryType OCEANIC = new PlanetaryType("Oceanic");

    /**
     * Rocky/Solid planets ex: earth, mars, moon
     */
    public static final PlanetaryType TERRESTRIAL = new PlanetaryType("Terrestrial");

    /**
     * Unknown
     */
    public static final PlanetaryType UNKNOWN = new PlanetaryType("Unknown");

    public PlanetaryType(String name) {
        super(name);
    }

}
