package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import org.bukkit.Material;
import org.bukkit.World;

/**
 * Saturn, an example of a planet with no implemented world
 * 
 * @author Mooy1
 */
public final class Saturn extends CelestialBody {

    public Saturn() {
        super("Saturn", 926_200_000L, 16_490_000_000L, new Gravity(10.44), Material.QUARTZ_BLOCK, new DayCycle(.445),
                Terrain.GASEOUS, new Atmosphere(0, false, true, false, false, World.Environment.NORMAL)
        );
    }

}
