package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.GalaxyType;
import io.github.addoncommunity.galactifun.util.ItemChoice;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A galaxy filled with star systems
 *
 * @author Mooy1
 */
public final class Galaxy extends UniversalObject<StarSystem> {

    public Galaxy(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull GalaxyType type, @Nonnull ItemChoice choice) {
        super(name, orbit, type, choice);
        
        // all galaxies are in the universe
        TheUniverse.getInstance().addOrbiters(this);
    }
    
    @Override
    protected void getItemStats(@Nonnull List<String> stats) {

    }

}
