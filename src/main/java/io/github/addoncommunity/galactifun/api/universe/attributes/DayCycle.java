package io.github.addoncommunity.galactifun.api.universe.attributes;

import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.GameRule;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadLocalRandom;

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

    // cached values
    private final long longValue;
    private final double doubleValue;

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

        // yes, i need the reciprocal
        double relativeToEarth = 24.0 / (hours + days * 24);
        this.longValue = (long) relativeToEarth;
        this.doubleValue = relativeToEarth - this.longValue;
    }

    /**
     * Eternal constructor
     */
    private DayCycle(long time) {
        Validate.isTrue(time >= 0 && time < 24000, "Eternal time must be between 0 and 24000!");

        this.description = time < 12000 ? "Eternal" : "Never";
        this.time = time;
        this.cycle = false;
        this.longValue = 0;
        this.doubleValue = 0;
    }

    public boolean isEternal() {
        return !this.cycle;
    }
    
    public void applyEffects(@Nonnull World world) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, this.cycle);
        if (!this.cycle) {
            world.setTime(this.time);
        }
    }

    public void tick(@Nonnull World world) {
        long time = world.getTime() + longValue;
        if (ThreadLocalRandom.current().nextDouble() < doubleValue) {
            time++;
        }
        world.setTime(time);
    }
    
}
