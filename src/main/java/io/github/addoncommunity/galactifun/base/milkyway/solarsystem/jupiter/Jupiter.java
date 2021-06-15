package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.jupiter;

import javax.annotation.Nonnull;

import org.bukkit.Material;

import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;

public final class Jupiter extends PlanetaryObject {

    public Jupiter() {
        super("&6Jupiter", Orbit.kilometers(778_340_821L, 12D), PlanetaryType.GAS_GIANT, new ItemChoice(Material.RED_DYE));
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
