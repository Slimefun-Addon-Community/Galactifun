package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.terrain.Terrain;
import io.github.addoncommunity.galactifun.core.explorer.GalacticHolder;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A planet which can have moons
 * 
 * @author Mooy1
 */
public abstract class Planet extends CelestialWorld implements GalacticHolder<Moon> {

    private final List<Moon> moons;
    
    public Planet(@Nonnull String name, long distance, long surfaceArea, @Nonnull Gravity gravity, @Nonnull Material material,
                  @Nonnull DayCycle dayCycle, @Nonnull Atmosphere atmosphere, @Nonnull Terrain terrain, @Nonnull Moon... moons) {
        super(name, distance, surfaceArea, gravity, material, dayCycle, terrain, atmosphere);
        this.moons = new ArrayList<>(Arrays.asList(moons));
    }

    @Nonnull
    @Override
    public final List<Moon> getComponents() {
        return this.moons;
    }

}
