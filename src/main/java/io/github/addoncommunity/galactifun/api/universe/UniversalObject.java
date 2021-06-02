package io.github.addoncommunity.galactifun.api.universe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.UniversalType;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Any object in the universe
 * 
 * @author Mooy1
 * 
 * @param <T> the object that it can hold
 */
public abstract class UniversalObject<T extends UniversalObject<?>> {
    
    @Getter
    @Nonnull
    protected final String name;
    
    @Nonnull
    private final Orbit orbit;
    
    @Getter
    @Nonnull
    private final ItemStack item;

    @Nonnull
    private final UniversalType type; // TODO add lore to item
    
    @Getter
    private UniversalObject<?> orbiting;

    @Getter
    @Nonnull
    private final List<UniversalObject<?>> orbiters = new ArrayList<>();
    
    UniversalObject(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull UniversalType type, @Nonnull ItemChoice choice) {
        Validate.notNull(name, "Name cannot be null");
        Validate.notNull(orbit, "Orbit cannot be null");
        Validate.notNull(type, "Type cannot be null");
        Validate.notNull(choice, "Item Choice cannot be null");
        
        this.orbit = orbit;
        this.name = ChatUtils.removeColorCodes(name);
        this.item = new CustomItem(choice.getItem(), ChatColor.AQUA + name);
        this.type = type;
    }
    
    @SafeVarargs
    public final void addOrbiters(@Nonnull T... orbiters) {
        for (UniversalObject<?> orbiter : orbiters) {
            Validate.notNull(orbiter, "Cannot add a null orbiter");
            this.orbiters.add(orbiter);
            orbiter.orbiting = this;
        }
    }

    /**
     * Gets the distance in light years between 2 objects
     */
    public final double getDistanceTo(@Nonnull UniversalObject<?> object) {
        if (this.orbiting == object.getOrbiting()) {
            return Util.lawOfCosines(
                this.orbit.getCurrentDistance(),
                object.orbit.getCurrentDistance(),
                Math.abs(this.orbit.getOrbitPos() - object.orbit.getOrbitPos())
            );
        }
        if (this.orbiting == null || getLevel() < object.getLevel()) {
            return object.orbit.getCurrentDistance() + getDistanceTo(object.getOrbiting());
        }
        return this.orbit.getCurrentDistance() + this.orbiting.getDistanceTo(object);
    }

    /**
     * Internal way to compare objects, universe = 0, galaxy = 1, etc.
     */
    private int getLevel() {
        if (this.orbiting == null) {
            return 0;
        } else {
            return this.orbiting.getLevel() + 1;
        }
    }

    @Override
    public final int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public final String toString() {
        return this.name;
    }

}
