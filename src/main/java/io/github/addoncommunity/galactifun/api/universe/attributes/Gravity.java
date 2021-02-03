package io.github.addoncommunity.galactifun.api.universe.attributes;

import lombok.Getter;
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

    public static final Gravity MOON_LIKE = new Gravity(1.62F);
    public static final Gravity EARTH_LIKE = new Gravity(1D);

    @Getter
    private final int percent;
    private final int jump;
    private final int speed;
    
    @Nonnull
    public static Gravity jumpHeight(double blocks) {
        return new Gravity(blocks / DEFAULT_JUMP);
    }

    @Nonnull
    public static Gravity relativeToEarth(double ratio) {
        return new Gravity(ratio);
    }

    public Gravity(float gravity) {
        this(gravity / EARTH_GRAVITY);
    }

    private Gravity(double boost) {
        int level = (int) (Math.log(boost) / LOG_JUMP_BOOST);
        this.jump = level - 1;
        this.speed = -1 * (level & 1) == 0 ? level / 2 - 1 : level / 2;
        this.percent = (int) (boost * 100);
    }
    
    public void applyGravity(@Nonnull Player p) {
        removeGravity(p);
        if (this.jump != -1) {
            new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, this.jump).apply(p);
            
            if (this.speed != -1) {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, this.speed).apply(p);
            }
        }
    }
    
    public static void removeGravity(@Nonnull Player p) {
        p.removePotionEffect(PotionEffectType.JUMP);
        p.removePotionEffect(PotionEffectType.SPEED);
    }
    
}
