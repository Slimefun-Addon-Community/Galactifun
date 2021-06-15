package io.github.addoncommunity.galactifun.api.universe.types;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Interface for identifying enums in this package
 * 
 * @author Mooy1
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UniversalType {

    public static final UniversalType THE_UNIVERSE = new UniversalType("");

    @Getter
    private final String description;
    
}
