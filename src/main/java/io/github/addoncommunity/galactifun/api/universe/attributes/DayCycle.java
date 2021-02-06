package io.github.addoncommunity.galactifun.api.universe.attributes;

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
    public static final DayCycle ETERNAL_NIGHT = new DayCycle(18000L);
    public static final DayCycle EARTH_LIKE = new DayCycle(1D);
    
    @Nonnull @Getter
    private final String dayLength;
    private final boolean cycle;
    private final long time;
    
    public DayCycle(double relativeToEarth) {
        this((int) (relativeToEarth * 24));
    }

    public DayCycle(int hours) {
        this(hours % 24, hours / 24);
    }

    public DayCycle(int days, int hours) {
        StringBuilder builder = new StringBuilder();
        if (days > 0) {
            builder.append(hours);
            builder.append(" day");
            if (hours != 1) {
                builder.append('s');
            }
            builder.append(' ');
        }
        if (hours > 0) {
            builder.append(hours);
            builder.append(" hour");
            if (hours != 1) {
                builder.append('s');
            }
        }
        this.dayLength = builder.toString();
        this.time = 0;
        this.cycle = true;
    }

    public DayCycle(long time) {
        this.dayLength = time >= 0 && time < 12000 ? "Eternal" : "Never";
        this.time = time;
        this.cycle = false;
    }
    
    public void applyEffects(@Nonnull World world) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, this.cycle);
        if (!this.cycle) {
            world.setTime(this.time);
        }
    }
    
    public void applyTime(@Nonnull World world) {
        // dunno how to implement, it wud slow down/speed up time
    }
    
}
