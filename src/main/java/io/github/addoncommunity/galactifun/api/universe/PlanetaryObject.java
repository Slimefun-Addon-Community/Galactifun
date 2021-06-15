package io.github.addoncommunity.galactifun.api.universe;

import lombok.Getter;
import lombok.NonNull;

import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.types.UniversalType;

/**
 * A celestial object
 * 
 * @author Mooy1
 */
@Getter
public class PlanetaryObject extends UniversalObject {

    private final DayCycle dayCycle;
    private final Atmosphere atmosphere;
    private final Gravity gravity;

    public PlanetaryObject(String name, UniversalType type, Orbit orbit, StarSystem orbiting, ItemStack baseItem,
                           @NonNull DayCycle dayCycle, @NonNull Atmosphere atmosphere, @NonNull Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem);
        this.dayCycle = dayCycle;
        this.atmosphere = atmosphere;
        this.gravity = gravity;
    }

    public PlanetaryObject(String name, UniversalType type, Orbit orbit, PlanetaryObject orbiting, ItemStack baseItem,
                           @NonNull DayCycle dayCycle, @NonNull Atmosphere atmosphere, @NonNull Gravity gravity) {
        super(name, type, orbit, orbiting, baseItem);
        this.dayCycle = dayCycle;
        this.atmosphere = atmosphere;
        this.gravity = gravity;
    }

}
