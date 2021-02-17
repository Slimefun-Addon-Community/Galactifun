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
public final class Gravity {

    private static final double EARTH_GRAVITY = 9.81F;
    private static final double DEFAULT_JUMP = 1.25F;
    private static final double LOG_JUMP_BOOST = Math.log(1.45);

    public static final Gravity MOON_LIKE = Gravity.metersPerSec(1.62);
    public static final Gravity EARTH_LIKE = Gravity.relativeToEarth(1);
    public static final Gravity ZERO = new Gravity();

    @Nonnull
    public static Gravity relativeToEarth(double ratio) {
        return new Gravity(ratio);
    }

    @Nonnull
    public static Gravity jumpHeightOf(double blocks) {
        return new Gravity(blocks / DEFAULT_JUMP);
    }
    
    @Nonnull
    public static Gravity metersPerSec(double metersPerSec) {
        return new Gravity(metersPerSec / EARTH_GRAVITY);
    }

    @Getter
    private final int percent;
    @Nonnull
    private final PotionEffect effect;
    
    private Gravity(double comparedToEarth) {
        int level;
        if (comparedToEarth > 0) {
            level = (int) (Math.log(comparedToEarth) / LOG_JUMP_BOOST) * -1;
        } else if (comparedToEarth < 0) {
            level = (int) (Math.log(comparedToEarth * -1) / LOG_JUMP_BOOST);
        } else {
            throw new IllegalArgumentException("Cannot create a new Gravity of 0, use Gravity.ZERO instead!");
        }
        // amplifier is level - 1
        this.effect = new PotionEffect(PotionEffectType.JUMP, 200, level - 1, false, false);
        this.percent = (int) (comparedToEarth * 100);
    }

    /**
     * 0 gravity constructor
     */
    private Gravity() {
        this.effect = new PotionEffect(PotionEffectType.SLOW_FALLING, 200, 0, false, false);
        this.percent = 0;
    }
    
    public void applyGravity(@Nonnull Player p) {
        p.addPotionEffect(this.effect);
    }
    
    public void removeGravity(@Nonnull Player p) {
        p.removePotionEffect(this.effect.getType());
    }
    
}
