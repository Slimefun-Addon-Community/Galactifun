package io.github.addoncommunity.galactifun.api.universe.attributes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

/**
 * A simple terrain with a description
 * 
 * @author Mooy1
 */
@AllArgsConstructor
public class Terrain {
    
    @Nonnull @Getter 
    protected final String name;
    
}
