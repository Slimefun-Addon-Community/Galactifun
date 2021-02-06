package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
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
        super("The Universe", new Orbit(0), new ItemChoice(Material.BARRIER));
    }

    @Override
    protected void getItemStats(@Nonnull List<String> stats) {

    }

}
