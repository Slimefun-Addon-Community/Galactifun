package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

/**
 * A star system filled with celestial objects
 *
 * @author Mooy1
 */
public class StarSystem extends UniversalObject<CelestialBody> {

    public StarSystem(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull ItemChoice choice, @Nonnull CelestialBody... orbiters) {
        super(name, orbit, choice, orbiters);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void getItemStats(@Nonnull List<String> stats) {

    }

}
