package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
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

    public StarSystem(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull CelestialBody... orbiters) {
        super(name, orbit, orbiters);
    }

    @Nonnull
    @Override
    protected ItemChoice getBaseItem() {
        return new ItemChoice(Material.NETHER_STAR);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void getItemStats(@Nonnull List<String> stats) {

    }

}
