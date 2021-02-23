package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericComponent;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import org.bukkit.Material;

import javax.annotation.Nonnull;

/**
 * Saturn, an example of a planet with no implemented world
 * 
 * @author Mooy1
 */
public final class Saturn extends CelestialBody {
    
    public Saturn(@Nonnull CelestialBody... moons) {
        super("Saturn", new Orbit(1_490_500_000), CelestialType.GAS_GIANT, new ItemChoice(Material.QUARTZ_BLOCK), moons);
    }

    @Nonnull
    @Override
    protected DayCycle createDayCycle() {
        return new DayCycle(.445);
    }

    @Nonnull
    @Override
    protected Atmosphere createAtmosphere() {
        return new AtmosphereBuilder().enableWeather()
            .addComponent(AtmosphericComponent.HYDROGEN, 96.3)
            .addComponent(AtmosphericComponent.HELIUM, 3.25)
            .addComponent(AtmosphericComponent.METHANE, 0.45)
            .build();
    }

    @Nonnull
    @Override
    protected Gravity createGravity() {
        return Gravity.relativeToEarth(1.06);
    }

    @Override
    protected long createSurfaceArea() {
        return 16_490_000_000L;
    }

}
