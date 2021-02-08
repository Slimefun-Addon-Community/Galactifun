package io.github.addoncommunity.galactifun.api.universe.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

/**
 * Types of galaxies
 * 
 * @author Mooy1
 */
@AllArgsConstructor
public enum GalaxyType {
    
    ELLIPSE("Ellipse"),
    
    SPIRAL("Spiral"),
    
    IRREGULAR("Irregular");
    
    @Getter
    @Nonnull
    private final String name;
}
