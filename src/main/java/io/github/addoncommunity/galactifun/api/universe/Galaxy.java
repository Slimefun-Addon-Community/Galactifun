package io.github.addoncommunity.galactifun.api.universe;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A galaxy filled with star systems
 *
 * @author Mooy1
 */
public class Galaxy extends UniversalObject<StarSystem> {

    public Galaxy(@Nonnull String name, @Nonnull ItemStack item, @Nonnull StarSystem... orbiters) {
        super(name, item, orbiters);
        TheUniverse.getInstance().addOrbiters(this);
    }

}
