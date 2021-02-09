package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.core.util.ItemChoice;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Any world that can be travelled to by rockets or other means
 * 
 * this should only be used to allow worlds from vanilla or other plugins to be travelled to
 * 
 * @see io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth
 * 
 * @author Mooy1
 */
public abstract class CelestialWorld extends CelestialBody {

    /**
     * All celestial worlds
     */
    private static final Map<World, CelestialWorld> WORLDS = new HashMap<>();

    @Nullable
    public static CelestialWorld getByWorld(@Nonnull World world) {
        return WORLDS.get(world);
    }

    public CelestialWorld(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull CelestialType type,
                          @Nonnull ItemChoice choice, @Nonnull CelestialBody... celestialBodies) {
        
        super(name, orbit, type, choice, celestialBodies);
        
        // register the world after subclass loads
        PluginUtils.runSync(() -> {
            World world = getWorld();
            if (world != null) {
                WORLDS.put(world, this);
            }
        });
        
    }

    @Nullable
    public abstract World getWorld();

}
