package io.github.addoncommunity.galactifun.api.universe;

import org.bukkit.Material;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.util.ItemChoice;

/**
 * The universe, serving simply as a holder for all galaxies. At some point could be made abstract
 * 
 * @author Mooy1
 */
public final class TheUniverse extends UniversalObject<Galaxy> {
    
    public TheUniverse() {
        super("The Universe", Orbit.ZERO, () -> "ERROR", new ItemChoice(Material.BARRIER));
    }

}
