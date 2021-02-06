package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Orbit;
import io.github.addoncommunity.galactifun.core.util.Util;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.items.LoreUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    private static final Map<String, UniversalObject<?>> OBJECTS = new HashMap<>();

    @Nullable
    public static UniversalObject<?> getByName(@Nonnull String name) {
        return OBJECTS.get(Util.stripUntranslatedColors(name));
    }
    
    @Getter
    @Nonnull
    protected final String name;
    @Getter
    @Nonnull
    private final List<UniversalObject<?>> orbiters = new ArrayList<>();
    @Nonnull
    private final Orbit orbit;
    @Getter
    private UniversalObject<?> orbiting;
    @Nonnull
    private final ItemStack item;
    
    @SafeVarargs
    public UniversalObject(@Nonnull String name, @Nonnull Orbit orbit, @Nonnull T... orbiters) {
        Validate.notNull(name);
        Validate.notNull(orbit);
        
        this.orbit = orbit;
        this.name = name;
        this.item = new CustomItem(getBaseItem().item, name);

        // add stats after subclass constructor completes
        PluginUtils.runSync(() -> {
            List<String> stats = new ArrayList<>();
            getItemStats(stats);
            LoreUtils.setLore(this.item, stats);
        });
        
        OBJECTS.put(Util.stripUntranslatedColors(name), this);

        addOrbiters(orbiters);
    }
    
    @SafeVarargs
    public final void addOrbiters(@Nonnull T... orbiters) {
        for (UniversalObject<?> orbiter : orbiters) {
            Validate.notNull(orbiter);
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
            return this.orbit.getCurrentDistance() + getDistanceTo(object.getOrbiting());
        }
        // object.getOrbiting() == null || getLevel() > object.getLevel()
        return object.orbit.getCurrentDistance() + this.orbiting.getDistanceTo(object);
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
    
    @Nonnull // TODO move this part to where its actually used eventually
    public final ItemStack getDistanceItem(@Nonnull UniversalObject<?> current) {
        ItemStack item = this.item.clone();
        double distance = getDistanceTo(current);
        LoreUtils.addLore(item, "&7Distance: " + (distance < 1 ? LorePreset.format(distance * Util.LY_TO_KM) + " Kilometers" : distance + " Light Years"));
        return item;
    }
    
    @Nonnull
    protected abstract ItemChoice getBaseItem();
    
    protected abstract void getItemStats(@Nonnull List<String> stats);
    
    /**
     * A utility class that lets people choose between using a material or head texture for the item
     */
    protected static final class ItemChoice {

        @Nonnull
        private final ItemStack item;

        public ItemChoice(@Nonnull String texture) {
            Validate.notNull(texture);
            this.item = SkullItem.fromBase64(texture);
        }

        public ItemChoice(@Nonnull Material material) {
            Validate.notNull(material);
            this.item = new ItemStack(material);
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

}
