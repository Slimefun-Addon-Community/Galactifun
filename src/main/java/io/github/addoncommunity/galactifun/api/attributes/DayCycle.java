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
public class DayCycle {

    public static final DayCycle ETERNAL_DAY = new DayCycle(6000);
    public static final DayCycle ETERNAL_NIGHT = new DayCycle(18000);
    public static final DayCycle NORMAL = new DayCycle();
        
    private final boolean dayLightCycle;
    private final long time;

    public DayCycle(long time) {
        this.time = time;
        this.dayLightCycle = false;
    }
    
    private DayCycle() {
        this.time = 0;
        this.dayLightCycle = true;
    }
    
    public void applyEffects(@Nonnull World world) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, this.dayLightCycle);
        world.setTime(this.time);
    }
    
}
