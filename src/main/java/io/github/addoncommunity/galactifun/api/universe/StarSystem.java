package io.github.addoncommunity.galactifun.api.universe;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A star system filled with celestial objects
 *
 * @author Mooy1
 */
public class StarSystem extends UniversalObject<CelestialBody> {

    public StarSystem(@Nonnull String name, @Nonnull ItemStack item, @Nonnull CelestialBody... orbiters) {
        super(name, item, orbiters);
    }

}
