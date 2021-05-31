package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import lombok.Getter;
import org.bukkit.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Any world that can be travelled to by rockets or other means
 * this should only be used to allow worlds from vanilla or other plugins to be travelled to,
 * if you want to make your own world use {@link SimpleAlienWorld} or {@link AlienWorld}
 * 
 * @see Earth
 * 
 * @author Mooy1
 */
public abstract class CelestialWorld extends CelestialBody {

    /**
     * All celestial worlds
     */
    @Getter
    private static final Map<World, CelestialWorld> worlds = new HashMap<>();

    @Nullable
    public static CelestialWorld getByWorld(@Nonnull World world) {
        return worlds.get(world);
    }

    public CelestialWorld(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull CelestialType type, @Nonnull ItemChoice choice) {
        super(name, orbit, type, choice);
    }

    @Override
    public final void register() {
        super.register();
        World world = loadWorld();
        if (world != null) {
            worlds.put(world, this);
        }
    }

    public boolean isReachableByRocket() {
        return true;
    }

    /**
     * Gets the world, called when registered
     */
    @Nullable
    protected abstract World loadWorld();

    public World getWorld() {
        for (Map.Entry<World, CelestialWorld> entry : worlds.entrySet()) {
            if (entry.getValue().equals(this)) {
                return entry.getKey();
            }
        }

        // shouldn't happen so no nonnull or nullable
        return null;
    }

}
