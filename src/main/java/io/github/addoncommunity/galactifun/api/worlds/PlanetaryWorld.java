package io.github.addoncommunity.galactifun.api.worlds;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.PlanetaryObject;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.base.universe.Earth;
import io.github.addoncommunity.galactifun.core.managers.WorldManager;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

/**
 * Any world that can be travelled to by rockets or other means
 * this should only be used to allow worlds from vanilla or other plugins to be travelled to,
 * if you want to make your own world use {@link SimpleAlienWorld} or {@link AlienWorld}
 *
 * @author Mooy1
 * @see Earth
 */
@Getter
public abstract class PlanetaryWorld extends PlanetaryObject {

    private World world;
    private WorldManager worldManager;
    private SlimefunAddon addon;

    public PlanetaryWorld(String name, PlanetaryType type, Orbit orbit, StarSystem orbiting, ItemStack baseItem,
                          DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    public PlanetaryWorld(String name, PlanetaryType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                          DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    public final void register(@Nonnull SlimefunAddon addon) {
        if (isRegistered()) {
            throw new IllegalStateException("World already registered!");
        }
        this.worldManager = Galactifun.worldManager();
        this.addon = addon;
        this.world = loadWorld();
        if (this.world != null) {
            this.worldManager.register(this);
        }
    }

    public final boolean isRegistered() {
        return this.worldManager != null;
    }

    @Nullable
    protected abstract World loadWorld();

}
