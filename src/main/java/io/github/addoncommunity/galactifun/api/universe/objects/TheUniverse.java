package io.github.addoncommunity.galactifun.api.universe.objects;

import java.util.List;

import javax.annotation.Nonnull;
import lombok.Getter;

import org.bukkit.Material;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.util.ItemChoice;

/**
 * The universe, serving simply as a holder for all galaxies. At some point could be made abstract
 * 
 * @author Mooy1
 */
public final class TheUniverse extends UniversalObject<Galaxy> {
    
    @Getter
    private static final TheUniverse instance = new TheUniverse();
    
    private TheUniverse() {
        super("The Universe", Orbit.ZERO, () -> "ERROR", new ItemChoice(Material.BARRIER));
    }

    @Override
    protected final void getItemStats(@Nonnull List<String> stats) {
        
    }

}
