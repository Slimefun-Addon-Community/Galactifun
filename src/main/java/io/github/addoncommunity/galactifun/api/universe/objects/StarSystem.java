package io.github.addoncommunity.galactifun.api.universe.objects;

import java.util.List;

import javax.annotation.Nonnull;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.StarSystemType;
import io.github.addoncommunity.galactifun.util.ItemChoice;

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
