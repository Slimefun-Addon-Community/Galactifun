package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import org.bukkit.Material;
import org.bukkit.World;

import javax.annotation.Nonnull;

/**
 * Saturn, an example of a planet with no implemented world
 * 
 * @author Mooy1
 */
public final class Saturn extends CelestialBody {
    
    public Saturn(@Nonnull CelestialBody... moons) {
        super("Saturn", new Orbit(1_490_500_000), 16_490_000_000L, new Gravity(1.06),
                new DayCycle(.445), CelestialType.GAS_GIANT,
                new Atmosphere(0, false, true, false, false, World.Environment.NORMAL), moons
        );
    }

    @Nonnull
    @Override
    protected ItemChoice getBaseItem() {
        return new ItemChoice(Material.QUARTZ_BLOCK);
    }

}
