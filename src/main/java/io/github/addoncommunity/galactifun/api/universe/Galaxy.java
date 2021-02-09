package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.GalaxyType;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

/**
 * A galaxy filled with star systems
 *
 * @author Mooy1
 */
public class Galaxy extends UniversalObject<StarSystem> {

    public Galaxy(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull GalaxyType type, @Nonnull ItemChoice choice, @Nonnull StarSystem... orbiters) {
        super(name, orbit, type, choice, orbiters);
        
        // all galaxies are in the universe
        TheUniverse.getInstance().addOrbiters(this);
    }
    
    @Override
    @OverridingMethodsMustInvokeSuper
    protected void getItemStats(@Nonnull List<String> stats) {

    }

}
