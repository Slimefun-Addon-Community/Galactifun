package io.github.addoncommunity.galactifun.api.universe.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

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
