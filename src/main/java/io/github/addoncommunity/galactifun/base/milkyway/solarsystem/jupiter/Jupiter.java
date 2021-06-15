package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.jupiter;

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

public final class Jupiter extends PlanetaryObject {

    public Jupiter(StarSystem starSystem) {
        super("&6Jupiter", PlanetaryType.GAS_GIANT, Orbit.kilometers(778_340_821L, 12D),
                starSystem, new ItemStack(Material.RED_DYE), DayCycle.hours(10),
                new AtmosphereBuilder().addStorm().addThunder().enableFire().enableWeather()
                        .add(Gas.HYDROGEN, 90)
                        .add(Gas.HELIUM, 10)
                        .build(),
                Gravity.metersPerSec(24.79));
    }

}
