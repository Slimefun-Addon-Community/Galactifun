package io.github.addoncommunity.galactifun.api.universe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import lombok.Getter;
import lombok.NonNull;

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
 */
public abstract class UniversalObject {

    @Getter
    private final List<UniversalObject> orbiters = new ArrayList<>();
    @Getter
    private final String name;
    @Getter
    private final ItemStack item;
    private final Orbit orbit;
    @Getter
    private final UniversalObject orbiting;
    private final int orbitLevel;

    UniversalObject(@NonNull String name, @NonNull UniversalType type, @NonNull Orbit orbit,
                    @NonNull UniversalObject orbiting, @NonNull ItemStack baseItem) {
        this.name = ChatColor.stripColor(ChatColors.color(name));
        this.item = new CustomItem(baseItem, name, "&7Type: " + type.getDescription());
        this.orbiting = orbiting;
        this.orbit = orbit;
        this.orbitLevel = orbiting.orbitLevel + 1;
        orbiting.orbiters.add(this);
    }

    /**
     * Constructor for the universe
     */
    UniversalObject(String name) {
        this.name = ChatColor.stripColor(ChatColors.color(name));
        this.item = null;
        this.orbiting = null;
        this.orbit = null;
        this.orbitLevel = 0;
    }

    /**
     * Gets the distance in light years between 2 objects
     */
    public final double getDistanceTo(@Nonnull UniversalObject other) {
        if (this.orbiting == other.orbiting) {
            double thisDist = this.orbit.getCurrentDistance();
            double otherDist = other.orbit.getCurrentDistance();
            double cosAngle = Math.cos(this.orbit.getOrbitPos() - other.orbit.getOrbitPos());
            return Math.sqrt(thisDist * thisDist + otherDist * otherDist - (2 * thisDist * otherDist * cosAngle));
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
