package io.github.addoncommunity.galactifun.api.universe.attributes;

import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.GameRule;
import org.bukkit.World;

import javax.annotation.Nonnull;

/**
 * Represents the amount of sunlight a celestial object gets
 *
 * @author Mooy1
 *
 */
public final class DayCycle {
    
    public static final DayCycle ETERNAL_DAY = DayCycle.eternal(6000L);
    public static final DayCycle ETERNAL_NIGHT = DayCycle.eternal(18000L);
    public static final DayCycle EARTH_LIKE = DayCycle.hours(24);
    
    @Nonnull
    public static DayCycle eternal(long time) {
        return new DayCycle(time);
    }
    
    @Nonnull
    public static DayCycle relativeToEarth(double ratio) {
        return hours((int) (24 * ratio));
    }

    @Nonnull
    public static DayCycle days(int days) {
        return new DayCycle(days, 0);
    }
    
    @Nonnull
    public static DayCycle hours(int hours) {
        return new DayCycle(hours / 24, hours % 24);
    }
    
    @Nonnull
    public static DayCycle of(int days, int hours) {
        return new DayCycle(days + hours / 24, hours % 24);
    }

    @Getter
    @Nonnull
    private final String description;
    private final boolean cycle;
    private final long time;
    private final long extraTicks;

    private DayCycle(int days, int hours) {
        Validate.isTrue((days > 0 && hours >= 0) || (hours > 0 && days >= 0), "Day cycles must last at least 1 hour!");
        
        StringBuilder builder = new StringBuilder();
        if (days > 0) {
            builder.append(days);
            builder.append(" day");
            if (days != 1) {
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
        this.description = builder.toString();
        this.time = 0;
        this.cycle = true;
        
        // for now we can only support speeding up time
        // because going backward skips ahead days
        int extra = (days - 1) * 24000 + hours * 1000;
        if (extra > 0) {
            // go from extra per day to extra per 5 seconds (100 ticks)
            this.extraTicks = extra / 240;
        } else {
            this.extraTicks = 0;
        }
    }

    /**
     * Eternal constructor
     */
    private DayCycle(long time) {
        Validate.isTrue(time >= 0 && time < 24000, "Eternal time must be between 0 and 24000!");

        this.description = time < 12000 ? "Eternal" : "Never";
        this.time = time;
        this.cycle = false;
        this.extraTicks = 0;
    }
    
    public void applyEffects(@Nonnull World world) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, this.cycle);
        world.setTime(this.time);
    }

    /**
     * Apply time effects to world every 5 seconds
     */
    public void tick(@Nonnull World world) {
        if (this.extraTicks != 0) {
            world.setTime(world.getTime() + this.extraTicks);
        }
    }
    
}
