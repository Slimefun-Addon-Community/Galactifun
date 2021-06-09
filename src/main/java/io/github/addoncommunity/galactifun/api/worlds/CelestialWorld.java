package io.github.addoncommunity.galactifun.api.worlds;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;

import org.bukkit.World;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.CelestialType;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth;
import io.github.addoncommunity.galactifun.util.ItemChoice;

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

    @Getter
    private final World world;

    public CelestialWorld(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull CelestialType type, @Nonnull ItemChoice choice) {
        super(name, orbit, type, choice);
        this.world = loadWorld();

        // TODO improve register system
        Galactifun.inst().getWorldManager().register(this);
        if (this instanceof AlienWorld alienWorld) {
            Galactifun.inst().getWorldManager().register(alienWorld);
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

}
