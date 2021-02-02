package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.saturn;

import io.github.addoncommunity.galactifun.api.universe.CelestialObject;
import io.github.addoncommunity.galactifun.api.universe.Moon;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.terrain.Terrain;
import io.github.addoncommunity.galactifun.core.explorer.GalacticHolder;
import org.bukkit.Material;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Saturn, an example of a planet with no implemented world
 */
public final class Saturn extends CelestialObject implements GalacticHolder<Moon> {

    private static final List<Moon> moons = new ArrayList<>();

    // TODO better material and terrain
    public Saturn() {
        super("Saturn", 1_490_500_000L, 16_490_000_000L, new Gravity(1.06),
            Material.QUARTZ_BLOCK, new DayCycle(.445),
            Terrain.SMOOTH, new Atmosphere(0, false, true, false, false, World.Environment.NORMAL)
        );

        moons.add(new Enceladus());
    }

    @Nonnull
    @Override
    public List<Moon> getComponents() {
        return moons;
    }
}
