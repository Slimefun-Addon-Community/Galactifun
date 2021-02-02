package io.github.addoncommunity.galactifun.api.universe.attributes;

import io.github.addoncommunity.galactifun.core.GalacticTicker;
import lombok.Getter;
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
    
    public static final DayCycle ETERNAL_DAY = new DayCycle(6000L);
    public static final DayCycle ETERNAL_NIGHT = new DayCycle(8000L);
    public static final DayCycle EARTH_LIKE = new DayCycle(1D);
    
    @Nonnull @Getter private final String dayLength;
    private final boolean cycle;
    private final long time;
    private final long extraTicks;

    public DayCycle(long time) {
        this.dayLength = time > 0 && time < 3 ? "Eternal" : "Zero";
        this.time = time;
        this.cycle = false;
        this.extraTicks = 0;
    }

    public DayCycle(double relativeToEarth) {
        long hours = (int) (relativeToEarth * 24);
        this.dayLength = (hours > 24 ? hours / 24 + " Days " + hours % 24 : hours) + " Hours";
        this.time = 0;
        this.cycle = true;
        this.extraTicks = (long) (((1 / relativeToEarth) - 1) * GalacticTicker.INTERVAL);
    }
    
    public void applyEffects(@Nonnull World world) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, this.cycle);
        if (!this.cycle) {
            world.setTime(this.time);
        }
    }
    
    public void applyTime(@Nonnull World world) {
        if (this.extraTicks != 0) {
            // find a way to implement
        }
    }
    
}
