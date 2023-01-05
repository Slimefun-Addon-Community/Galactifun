package io.github.addoncommunity.galactifun.api.universe.attributes;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.apache.commons.lang.Validate;

/**
 * Represents the amount of sunlight a celestial object s
 *
 * @author Mooy1
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
    private final long startTime;
    private final long perFiveSeconds;

    private DayCycle(int days, int hours) {
        Validate.isTrue((days > 0 && hours >= 0) || (hours > 0 && days >= 0), "自转时间至少为1小时!");

        StringBuilder builder = new StringBuilder();
        if (days > 0) {
            builder.append(days);
            builder.append(" 天");
            builder.append(' ');
        }
        if (hours > 0) {
            builder.append(hours);
            builder.append(" 小时");
        }

        this.description = builder.toString();
        this.startTime = -1;
        this.perFiveSeconds = days * 100L + hours * 4L;
    }

    /**
     * Eternal constructor
     */
    private DayCycle(long time) {
        Validate.isTrue(time >= 0 && time < 24000, "固定时间必须在 0 和 24000 中间!");

        this.description = "永远的 " + (time < 12000 ? "白天" : "黑夜");
        this.startTime = time;
        this.perFiveSeconds = 0;
    }

    public void applyEffects(@Nonnull World world) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        if (this.startTime != -1) {
            world.setTime(this.startTime);
        }
    }

    /**
     * Apply time effects to world every 5 seconds
     */
    public void tick(@Nonnull World world) {
        if (this.perFiveSeconds != 0) {
            world.setTime(world.getTime() + this.perFiveSeconds);
        }
    }

}
