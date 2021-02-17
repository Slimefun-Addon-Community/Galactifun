package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import lombok.Getter;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The universe, serving simply as a holder for all galaxies. At some point could be made abstract
 * 
 * @author Mooy1
 */
public final class TheUniverse extends UniversalObject<Galaxy> {
    
    @Getter
    private static final TheUniverse instance = new TheUniverse();
    
    private TheUniverse() {
        super("The Universe", Orbit.ZERO, () -> { throw new IllegalStateException(); }, new ItemChoice(Material.BARRIER));
    }

    @Override
    protected final void getItemStats(@Nonnull List<String> stats) {
        throw new IllegalStateException();
    }

}
