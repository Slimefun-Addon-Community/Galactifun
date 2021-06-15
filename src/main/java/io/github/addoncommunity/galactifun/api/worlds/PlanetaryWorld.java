package io.github.addoncommunity.galactifun.api.worlds;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import lombok.Getter;
import lombok.NonNull;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.UniversalType;
import io.github.addoncommunity.galactifun.base.milkyway.solarsystem.earth.Earth;

/**
 * Any world that can be travelled to by rockets or other means
 * this should only be used to allow worlds from vanilla or other plugins to be travelled to,
 * if you want to make your own world use {@link SimpleAlienWorld} or {@link AlienWorld}
 * 
 * @see Earth
 * 
 * @author Mooy1
 */
public abstract class PlanetaryWorld extends PlanetaryObject {

    @Getter
    private World world;

    public PlanetaryWorld(String name, UniversalType type, Orbit orbit, StarSystem orbiting, ItemStack baseItem,
                          DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    public PlanetaryWorld(String name, UniversalType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                          DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @OverridingMethodsMustInvokeSuper
    public PlanetaryWorld register(@NonNull WorldManager worldManager) {
        this.world = loadWorld(worldManager);
        if (this.world != null) {
            worldManager.register(this);
        }
        return this;
    }

    @Nullable
    protected abstract World loadWorld(WorldManager worldManager);

    public boolean isReachableByRocket() {
        return true;
    }

}
