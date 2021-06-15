package io.github.addoncommunity.galactifun.api.universe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.UniversalType;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Any object in the universe
 * 
 * @author Mooy1
 *
 * @param <T> The type of object that orbits this
 */
@Getter
public abstract class UniversalObject<T extends UniversalObject<?>> {

    private final List<UniversalObject<?>> orbiters = new ArrayList<>();
    private final String name;
    private final ItemStack item;
    private final UniversalType type;
    private final Orbit orbit;
    private final UniversalObject<? extends UniversalObject<T>> orbiting;
    private final int orbitLevel;

    UniversalObject(String name, UniversalType type, Orbit orbit, UniversalObject<UniversalObject<T>> orbiting, ItemStack baseItem) {
        this.name = ChatColor.stripColor(ChatColors.color(name));
        this.type = type;
        this.orbit = orbit;
        this.orbiting = orbiting;
        this.orbitLevel = orbiting == null ? 0 : orbiting.getOrbitLevel();
        this.item = new CustomItem(baseItem, name, "&7Type: " + type.getDescription());
    }

    /**
     * Gets the distance in light years between 2 objects
     */
    public final double getDistanceTo(@Nonnull UniversalObject<?> other) {
        if (this.orbiting == other.orbiting) {
            double thisDist1 = this.orbit.getCurrentDistance();
            double otherDist1 = other.orbit.getCurrentDistance();
            double thisDist2 = thisDist1 * thisDist1;
            double otherDist2 = otherDist1 * otherDist1;
            double angle = this.orbit.getOrbitPos() - other.orbit.getOrbitPos();
            return Math.sqrt(thisDist2 + otherDist2 - (2 * thisDist1 * otherDist1 * Math.cos(Math.toRadians(angle))));
        }
        if (this.orbiting == null || this.orbitLevel < other.orbitLevel) {
            return other.orbit.getCurrentDistance() + getDistanceTo(other.orbiting);
        }
        return this.orbit.getCurrentDistance() + this.orbiting.getDistanceTo(other);
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
