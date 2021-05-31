package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.jupiter;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import org.bukkit.Material;

import javax.annotation.Nonnull;

public final class Jupiter extends CelestialBody {

    public Jupiter() {
        super("&6Jupiter", Orbit.kilometers(778_340_821L, 12D), CelestialType.GAS_GIANT, new ItemChoice(Material.RED_DYE));
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return DayCycle.hours(10);
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return new AtmosphereBuilder().addStorm().addThunder().enableFire().enableWeather()
            .add(Gas.HYDROGEN, 90)
            .add(Gas.HELIUM, 10)
            .build();
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.metersPerSec(24.79);
    }

}
