package io.github.addoncommunity.galactifun.api.attributes;

import org.bukkit.GameRule;
import org.bukkit.World;

import javax.annotation.Nonnull;

/**
 * Represents the amount of sunlight a celestial object gets
 *
 * @author Mooy1
 *
 */
public class SolarType {

    public static final SolarType ETERNAL_DAY = new SolarType(6000);
    public static final SolarType ETERNAL_NIGHT = new SolarType(18000);
    public static final SolarType NORMAL = new SolarType();
        
    private final boolean dayLightCycle;
    private final long time;

    public SolarType(long time) {
        this.time = time;
        this.dayLightCycle = false;
    }
    
    private SolarType() {
        this.time = 0;
        this.dayLightCycle = true;
    }
    
    public void applyEffects(@Nonnull World world) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, this.dayLightCycle);
        world.setTime(this.time);
    }
    
}
