package io.github.addoncommunity.galactifun.api.universe.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

/**
 * Star system types
 * 
 * @author Mooy1
 */
@AllArgsConstructor
public enum StarSystemType {

    NORMAL("Normal"),
    
    BINARY("Binary");
    
    @Getter
    @Nonnull
    private final String name;
    
}
