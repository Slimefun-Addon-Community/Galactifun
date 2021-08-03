package io.github.addoncommunity.galactifun.api.universe.types;

/**
 * Types of galaxies
 *
 * @author Mooy1
 */
public final class GalaxyType extends UniversalType {

    public static final GalaxyType ELLIPTICAL = new GalaxyType("Elliptical");
    public static final GalaxyType SPIRAL = new GalaxyType("Spiral");
    public static final GalaxyType IRREGULAR = new GalaxyType("Irregular");

    public GalaxyType(String name) {
        super(name);
    }

}
