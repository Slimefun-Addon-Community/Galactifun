package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

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
    
    // TODO better material and terrain
    public Saturn() {
        super("Saturn", 1_490_500_000L, 16_490_000_000L, new Gravity(1.06),
            Material.QUARTZ_BLOCK, new DayCycle(.445), Terrain.GASEOUS,
                new Atmosphere(0, false, true, false, false, World.Environment.NORMAL)
        );
    }

}
