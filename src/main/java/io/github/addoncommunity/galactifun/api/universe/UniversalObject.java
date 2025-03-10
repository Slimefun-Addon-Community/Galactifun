package io.github.addoncommunity.galactifun.api.universe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.UniversalType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;

/**
 * Any object in the universe
 *
 * @author Mooy1
 */
public abstract class UniversalObject {

    private final List<UniversalObject> orbiters = new ArrayList<>();
    @Getter
    private final String name;
    @Getter
    protected final String id;
    @Getter
    private final ItemStack item;
    @Getter
    private final UniversalObject orbiting;
    @Getter
    private final Orbit orbit;
    protected final int orbitLevel;

    UniversalObject(@Nonnull String name, @Nonnull UniversalType type, @Nonnull Orbit orbit,
                    @Nonnull UniversalObject orbiting, @Nonnull ItemStack baseItem) {
        this.name = ChatUtils.removeColorCodes(name);
        this.item = CustomItemStack.create(baseItem, name, "&7Type: " + type.description());
        this.orbiting = orbiting;
        this.orbit = orbit;
        this.orbitLevel = orbiting.orbitLevel + 1;
        this.id = this.name.toLowerCase(Locale.ROOT).replace(' ', '_');
        orbiting.orbiters.add(this);
    }

    /**
     * Constructor for the universe
     */
    UniversalObject(String name) {
        this.name = ChatUtils.removeColorCodes(name);
        this.id = this.name.toLowerCase(Locale.ROOT).replace(' ', '_');
        this.item = null;
        this.orbiting = null;
        this.orbit = null;
        this.orbitLevel = 0;
    }

    /**
     * Gets the distance in light years between 2 objects
     */
    public double distanceTo(@Nonnull UniversalObject other) {
        if (this.orbiting == other.orbiting) {
            double thisDist = this.orbit.currentDistance();
            double otherDist = other.orbit.currentDistance();
            double cosAngle = Math.cos(this.orbit.position() - other.orbit.position());
            return Math.sqrt(thisDist * thisDist + otherDist * otherDist - (2 * thisDist * otherDist * cosAngle));
        }
        if (this.orbiting == null || this.orbitLevel < other.orbitLevel) {
            return other.orbit.currentDistance() + distanceTo(other.orbiting);
        }
        return this.orbit.currentDistance() + this.orbiting.distanceTo(other);
    }

    @Nonnull
    public List<UniversalObject> orbiters() {
        return Collections.unmodifiableList(this.orbiters);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
