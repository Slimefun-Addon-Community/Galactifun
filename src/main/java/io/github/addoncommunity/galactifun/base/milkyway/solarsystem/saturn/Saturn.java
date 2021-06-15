package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphereBuilder;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;

/**
 * Saturn, an example of a planet with no implemented world
 * 
 * @author Mooy1
 */
public final class Saturn extends PlanetaryObject {
    
    public Saturn(StarSystem starSystem) {
        super("Saturn", PlanetaryType.GAS_GIANT, Orbit.kilometers(1_490_500_000, 29D), starSystem,
                new ItemStack(Material.QUARTZ_BLOCK), DayCycle.hours(10),
                new AtmosphereBuilder().enableWeather()
                        .add(Gas.HYDROGEN, 96.3)
                        .add(Gas.HELIUM, 3.25)
                        .add(Gas.METHANE, 0.45)
                        .build(),
                Gravity.relativeToEarth(1.06));
    }

}
