package io.github.addoncommunity.galactifun.api.universe.types;

/**
 * Star system types
 *
 * @author Mooy1
 */
public final class StarSystemType extends UniversalType {

    public static final StarSystemType NORMAL = new StarSystemType("Normal");
    public static final StarSystemType BINARY = new StarSystemType("Binary");

    public StarSystemType(String name) {
        super(name);
    }

}
