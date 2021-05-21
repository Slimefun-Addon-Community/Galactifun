package io.github.addoncommunity.galactifun.api.universe.types;

import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Types of galaxies
 * 
 * @author Mooy1
 */
@AllArgsConstructor
public enum GalaxyType implements UniversalType {
    
    ELLIPTICAL("Elliptical"),
    
    SPIRAL("Spiral"),
    
    IRREGULAR("Irregular");
    
    @Getter
    @Nonnull
    private final String name;
}
