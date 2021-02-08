package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.CelestialType;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.World;

import javax.annotation.Nonnull;

/**
 * Any world that can be travelled to by rockets or other means
 * 
 * @author Mooy1
 */
public abstract class AbstractCelestialWorld extends CelestialBody {

    public AbstractCelestialWorld(@Nonnull String name, @Nonnull Orbit orbit, long surfaceArea, @Nonnull Gravity gravity,
                                  @Nonnull DayCycle dayCycle, @Nonnull CelestialType type, @Nonnull Atmosphere atmosphere,
                                  @Nonnull ItemChoice choice, @Nonnull CelestialBody... celestialBodies) {
        super(name, orbit, surfaceArea, gravity, dayCycle, type, atmosphere, choice, celestialBodies);
        
        // register the world after subclass loads
        PluginUtils.runSync(() -> registerWorld(getWorld()));
    }

    @Nonnull
    public abstract World getWorld();

}
