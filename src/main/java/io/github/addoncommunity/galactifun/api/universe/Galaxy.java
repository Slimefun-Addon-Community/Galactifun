package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

/**
 * A galaxy filled with star systems
 *
 * @author Mooy1
 */
public class Galaxy extends UniversalObject<StarSystem> {

    public Galaxy(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull StarSystem... orbiters) {
        super(name, orbit, orbiters);
        TheUniverse.getInstance().addOrbiters(this);
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
