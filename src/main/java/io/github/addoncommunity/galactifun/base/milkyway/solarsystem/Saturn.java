package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.universe.CelestialObject;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import org.bukkit.Material;
import org.bukkit.World;

/**
 * Saturn, an ex of a planet with no implemented world
 */
public final class Saturn extends CelestialObject {

    // TODO better material and terrain
    public Saturn() {
        super("Saturn", 926_200_000L, 16_490_000_000L, new Gravity(1.06),
                Material.QUARTZ_BLOCK, new DayCycle(.445),
                Terrain.SMOOTH, new Atmosphere(0, false, true, false, false, World.Environment.NORMAL)
        );
    }

}
