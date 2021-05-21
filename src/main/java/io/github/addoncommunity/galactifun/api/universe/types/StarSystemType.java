package io.github.addoncommunity.galactifun.api.universe.types;

import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
