package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;

import javax.annotation.Nonnull;

/**
 * A celestial object
 * 
 * @author Mooy1
 */
public class CelestialBody extends UniversalObject<CelestialBody> {
    
    @Nonnull 
    protected final DayCycle dayCycle;
    @Nonnull 
    protected final Atmosphere atmosphere;
    @Nonnull 
    protected final Terrain terrain;
    @Nonnull 
    protected final Gravity gravity;
    
    /**
     * Distance in kilometers from the object that this it orbiting
     */
    @Getter
    protected final long distance;

    /**
     * Surface area in square meters
     */
    protected final long surfaceArea;
    
    public CelestialBody(@Nonnull String name, long distance, long surfaceArea, @Nonnull Gravity gravity, @Nonnull Material material,
                         @Nonnull DayCycle dayCycle, @Nonnull Terrain terrain, @Nonnull Atmosphere atmosphere, @Nonnull CelestialBody... celestialBodies) {
        super(name, new CustomItem(
                material,
                name,
                "&7Distance: " + distance + " km",
                "&7Surface Area: " + surfaceArea + " blocks",
                "&7Gravity: " + gravity.getPercent() + "%",
                "&7Oxygen: " + atmosphere.getOxygenPercentage() + "%",
                "&7Terrain: " + terrain.getName(),
                "&7Day Length: " + dayCycle.getDayLength()
        ), celestialBodies);
        
        Validate.notNull(dayCycle);
        Validate.notNull(atmosphere);
        Validate.notNull(terrain);
        Validate.notNull(gravity);

        this.terrain = terrain;
        this.distance = distance;
        this.gravity = gravity;
        this.surfaceArea = surfaceArea;
        this.dayCycle = dayCycle;
        this.atmosphere = atmosphere;
    }
    
}
