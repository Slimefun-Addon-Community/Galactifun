package io.github.addoncommunity.galactifun.api.universe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.UniversalType;

/**
 * The universe, serving simply as a holder for all galaxies. At some point could be made abstract
 * 
 * @author Mooy1
 */
public final class TheUniverse extends UniversalObject<Galaxy> {
    
    public TheUniverse() {
        super("The Universe", UniversalType.THE_UNIVERSE,
                Orbit.NONE, null, new ItemStack(Material.BARRIER));
    }

}
