package io.github.addoncommunity.galactifun.api.universe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import lombok.Getter;
import lombok.NonNull;

import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.UniversalType;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

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
    private final Orbit orbit;
    @Getter
    private final UniversalObject orbiting;
    private final int orbitLevel;

    UniversalObject(@NonNull String name, @NonNull UniversalType type, @NonNull Orbit orbit,
                    @NonNull UniversalObject orbiting, @NonNull ItemStack baseItem) {
        this.name = ChatUtils.removeColorCodes(name);
        this.item = new CustomItem(baseItem, name, "&7Type: " + type.getDescription());
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
    public final double getDistanceTo(@Nonnull UniversalObject other) {
        // star systems orbiting a galaxy would take a little while to get close enough :monkaS:
        if (this instanceof StarSystem && other instanceof StarSystem) {
            return Math.abs(this.orbit.getCurrentDistance() - other.orbit.getCurrentDistance());
        }
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

    @Nonnull
    public List<UniversalObject> getOrbiters() {
        return Collections.unmodifiableList(this.orbiters);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof UniversalObject other)) return false;

        return this.id.equals(other.id);
    }
}
