package io.github.addoncommunity.galactifun.api.universe.attributes;

import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

/**
 * Represents the gravitational pull.
 *
 * @author GallowsDove
 * @author Mooy1
 * @author Seggan
 *
 */
public class Gravity {

    private static final double EARTH_GRAVITY = 9.81;
    private static final double DEFAULT_JUMP = 1.25;
    private static final double LOG_JUMP_BOOST = Math.log(1.45);

    public static final Gravity MOON_LIKE = new Gravity(1.62);
    public static final Gravity EARTH_LIKE = new Gravity(1);
    public static final Gravity ZERO = new Gravity(0) {
        @Override
        public void applyGravity(@Nonnull Player p) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 0, false, false));
        }
    };
    
    @Getter
    private final int percent;
    private final int jump;
    private final int speed;

    @Nonnull
    public static Gravity relativeToEarth(double ratio) {
        Validate.isTrue(ratio > 0);
        return new Gravity((float) ratio);
    }

    @Nonnull
    public static Gravity jumpHeight(double blocks) {
        return new Gravity(validateAndDiv(blocks, DEFAULT_JUMP));
    }

    public Gravity(double gravity) {
        this(validateAndDiv(gravity, EARTH_GRAVITY));
    }

    protected Gravity(float boost) {
        // supports negative, 0, and positive
        int level;
        if (boost > 0) {
            level = (int) (Math.log(boost) / LOG_JUMP_BOOST);
        } else if (boost < 0) {
            level = (int) (Math.log(boost * -1) / LOG_JUMP_BOOST) * -1;
        } else {
            level = 0;
        }
        this.jump = level - 1;
        this.speed = (level >> 1) - 1;
        this.percent = (int) (boost * 100);
    }
    
    private static float validateAndDiv(double num, double div) {
        Validate.isTrue(num > 0);
        return (float) (num / div);
    }
    
    public void applyGravity(@Nonnull Player p) {
        if (this.jump != -1) {
            new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, this.jump, false, false).apply(p);
            if (this.speed != -1) {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, this.speed, false, false).apply(p);
            }
        }
    }
    
    public static void removeGravity(@Nonnull Player p) {
        p.removePotionEffect(PotionEffectType.JUMP);
        p.removePotionEffect(PotionEffectType.SPEED);
        p.removePotionEffect(PotionEffectType.SLOW_FALLING);
    }
    
}
