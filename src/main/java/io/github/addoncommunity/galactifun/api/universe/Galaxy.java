package io.github.addoncommunity.galactifun.api.universe;

import javax.annotation.Nonnull;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.GalaxyType;
import io.github.addoncommunity.galactifun.util.ItemChoice;

/**
 * A galaxy filled with star systems
 *
 * @author Mooy1
 */
public final class Galaxy extends UniversalObject<StarSystem> {

    public Galaxy(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull GalaxyType type, @Nonnull ItemChoice choice) {
        super(name, orbit, type, choice);
    }

}
