package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

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

/**
 * Saturn, an example of a planet with no implemented world
 * 
 * @author Mooy1
 */
public final class Saturn extends PlanetaryObject {
    
    public Saturn() {
        super("Saturn", Orbit.kilometers(1_490_500_000, 29D), PlanetaryType.GAS_GIANT, new ItemChoice(Material.QUARTZ_BLOCK));
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return DayCycle.hours(10);
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return new AtmosphereBuilder().enableWeather()
            .add(Gas.HYDROGEN, 96.3)
            .add(Gas.HELIUM, 3.25)
            .add(Gas.METHANE, 0.45)
            .build();
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.relativeToEarth(1.06);
    }

}
