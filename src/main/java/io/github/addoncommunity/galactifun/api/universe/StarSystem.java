package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.StarSystemType;
import io.github.addoncommunity.galactifun.util.ItemChoice;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A star system filled with celestial objects
 *
 * @author Mooy1
 */
public final class StarSystem extends UniversalObject<CelestialBody> {
    
    public StarSystem(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull StarSystemType type, @Nonnull ItemChoice choice) {
        super(name, orbit, type, choice);
    }

    @Override
    protected void getItemStats(@Nonnull List<String> stats) {

    }

}
