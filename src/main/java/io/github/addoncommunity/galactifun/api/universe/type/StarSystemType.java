package io.github.addoncommunity.galactifun.api.universe.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

/**
 * Star system types
 * 
 * @author Mooy1
 */
@AllArgsConstructor
public enum StarSystemType implements UniversalType {

    NORMAL("Normal"),
    
    BINARY("Binary");
    
    @Getter
    @Nonnull
    private final String name;
    
}
