package io.github.addoncommunity.galactifun.base.universe.earth;

import java.util.Objects;

import javax.annotation.Nonnull;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.StarSystem;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.PlanetaryType;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;

/**
 * A class to connect the default earth world into the api
 *
 * @author Mooy1
 */
public final class Earth extends PlanetaryWorld {

    public Earth(String name, String id, PlanetaryType type, Orbit orbit, StarSystem orbiting, ItemStack baseItem,
                 DayCycle dayCycle, Atmosphere atmosphere, Gravity gravity) {
        super(name, id, type, orbit, orbiting, baseItem, dayCycle, atmosphere, gravity);
    }

    @Nonnull
    @Override
    public World loadWorld() {
        String name = Galactifun.instance().getConfig().getString("worlds.earth-name");
        World world = new WorldCreator(Objects.requireNonNull(name)).createWorld(); // this will load the world as only the default world loads on startup
        if (world == null) {
            throw new IllegalStateException("无法从配置中读取地球世界名; 未找到默认世界!");
        } else {
            return world;
        }
    }

}
