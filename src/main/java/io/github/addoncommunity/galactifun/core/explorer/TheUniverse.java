package io.github.addoncommunity.galactifun.core.explorer;

import io.github.addoncommunity.galactifun.api.universe.Galaxy;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * The universe, serving simply as a holder for all galaxies. At some point could be made abstract
 * 
 * @author Mooy1
 */
public final class TheUniverse implements GalacticHolder<Galaxy> {
    
    public static final TheUniverse UNIVERSE = new TheUniverse();
    
    private final List<Galaxy> galaxies = new ArrayList<>();
    
    @Nonnull
    @Override
    public String getName() {
        return "The Universe";
    }

    @Nonnull
    @Override
    public List<Galaxy> getComponents() {
        return this.galaxies;
    }
    
    private TheUniverse() {}

}
