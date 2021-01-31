package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import io.github.addoncommunity.galactifun.core.explorer.GalacticComponent;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A celestial object
 * 
 * @author Mooy1
 */
public abstract class CelestialObject implements GalacticComponent {

    @Getter @Nonnull protected final String name;
    @Nonnull protected final DayCycle dayCycle;
    @Nonnull protected final Atmosphere atmosphere;
    @Nonnull protected final Terrain terrain;
    @Nonnull protected final Gravity gravity;

    /**
     * Distance in miles from the object that this it orbiting
     */
    private final long distance;

    /**
     * Surface area in square meters
     */
    protected final long surfaceArea;

    /**
     * Display item
     */
    private final ItemStack displayItem;

    public CelestialObject(@Nonnull String name, long distance, long surfaceArea, @Nonnull Gravity gravity, @Nonnull Material material,
                           @Nonnull DayCycle dayCycle, @Nonnull Terrain terrain, @Nonnull Atmosphere atmosphere) {

        Validate.notNull(name);
        Validate.notNull(dayCycle);
        Validate.notNull(atmosphere);
        Validate.notNull(terrain);

        this.name = name;
        this.distance = distance;
        this.gravity = gravity;
        this.surfaceArea = surfaceArea;
        this.dayCycle = dayCycle;
        this.atmosphere = atmosphere;
        this.terrain = terrain;
        this.displayItem = new CustomItem(
                material,
                name,
                "&7Distance: " + distance + " miles",
                "&7Surface Area: " + surfaceArea + " blocks",
                "&7Gravity: " + gravity.getPercent() + "%",
                "&7Oxygen: " + atmosphere.getOxygenPercentage() + "%"
        );
    }

    @Nonnull
    @Override
    public final ItemStack getDisplayItem() {
        return this.displayItem;
    }

    @Override
    public final int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof CelestialWorld && ((CelestialWorld) obj).name.equals(this.name);
    }
    
}
