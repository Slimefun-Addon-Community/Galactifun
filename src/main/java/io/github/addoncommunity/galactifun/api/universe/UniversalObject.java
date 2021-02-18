package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.api.universe.types.UniversalType;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.items.LoreUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Any object in the universe
 * 
 * @author Mooy1
 * 
 * @param <T> the object that it can hold
 */
public abstract class UniversalObject<T extends UniversalObject<?>> {

    /**
     * All objects in the universe including the universe
     */
    private static final Map<String, UniversalObject<?>> OBJECTS = new HashMap<>();
    
    @Nullable
    public static UniversalObject<?> getByName(@Nonnull String name) {
        return OBJECTS.get(name);
    }

    @Getter
    @Nonnull
    protected final String name;
    
    @Getter
    @Nonnull
    private final Orbit orbit;
    
    @Getter
    @Nonnull
    private final ItemStack item;

    @Getter
    private UniversalObject<?> orbiting;

    @Getter
    @Nonnull
    private final List<UniversalObject<?>> orbiters = new ArrayList<>();
    
    @SafeVarargs
    public UniversalObject(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull UniversalType type, @Nonnull ItemChoice choice, @Nonnull T... orbiters) {
        Validate.notNull(name, "Name cannot be null");
        Validate.notNull(orbit, "Orbit cannot be null");
        Validate.notNull(type, "Type cannot be null");
        Validate.notNull(choice, "Item Choice cannot be null");
        
        this.orbit = orbit;
        this.name = ChatUtils.removeColorCodes(name);
        this.item = new CustomItem(choice.getItem(), ChatColor.AQUA + name);

        // add stats after subclass constructor completes
        PluginUtils.runSync(() -> {
            List<String> stats = new ArrayList<>();
            stats.add("&6Type: " + type.getName());
            getItemStats(stats);
            stats.add("&7Distance: &8Unknown");
            LoreUtils.setLore(this.item, stats);
        });
        
        OBJECTS.put(this.name, this);

        addOrbiters(orbiters);
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
        if (this.orbiting == object.getOrbiting()) { // getLevel() == object.getLevel()
            return Math.abs(this.orbit.getCurrentDistance() - object.orbit.getCurrentDistance());
        }
        if (this.orbiting == null || getLevel() < object.getLevel()) {
            // object.getOrbiting() != null
            return object.orbit.getCurrentDistance() + getDistanceTo(object.getOrbiting());
        }
        // object.getOrbiting() == null || getLevel() > object.getLevel()
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
    
    protected abstract void getItemStats(@Nonnull List<String> stats);
    
    @OverridingMethodsMustInvokeSuper
    protected void register() {
        // add stuff that needs to be called after subclasses are loaded
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
