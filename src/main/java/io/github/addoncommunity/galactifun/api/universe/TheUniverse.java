package io.github.addoncommunity.galactifun.api.universe;

import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

/**
 * The universe, serving simply as a holder for all galaxies. At some point could be made abstract
 * 
 * @author Mooy1
 */
public final class TheUniverse extends UniversalObject<Galaxy> {
    
    @Getter
    private static final TheUniverse instance = new TheUniverse();
    
    private TheUniverse() {
        super("The Universe", new CustomItem(Material.BARRIER, "ERROR"));
    }

}
