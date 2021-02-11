package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.jupiter;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.type.CelestialType;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;

import javax.annotation.Nonnull;

public class Jupiter extends CelestialBody {

    public Jupiter(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull CelestialType type, @Nonnull ItemChoice choice, @Nonnull CelestialBody... celestialBodies) {
        super("Jupiter", new Orbit(), CelestialType.GAS_GIANT, choice, celestialBodies);
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return new DayCycle(10);
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return new AtmosphereBuilder().addStorm().addThunder().enableFire().enableWeather().build();
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return new Gravity(24.79);
    }

    @Override
    protected long createSurfaceArea() {
        return 0;
    }
}
