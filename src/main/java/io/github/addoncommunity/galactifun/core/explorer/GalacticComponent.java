package io.github.addoncommunity.galactifun.core.explorer;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * An interface for any galactic component that is used for the galactic explorer
 * 
 * @author Mooy1
 */
public interface GalacticComponent {

    @Nonnull ItemStack getDisplayItem();
    
}
