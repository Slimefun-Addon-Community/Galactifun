package io.github.addoncommunity.galactifun.api.universe.attributes;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Represents the gravitational pull.
 *
 * @author GallowsDove
 * @author Mooy1
 * @author Seggan
 *
 */
public final class Gravity {

    private static final double EARTH_GRAVITY = 9.81;
    private static final double DEFAULT_JUMP = 1.25;
    private static final double JUMP_BOOST = 1.45;

    public static final Gravity MOON_LIKE = Gravity.metersPerSec(1.62);
    public static final Gravity EARTH_LIKE = Gravity.relativeToEarth(1);
    public static final Gravity ZERO = new Gravity();
    
    @Nonnull
    public static Gravity relativeToEarth(double ratio) {
        return new Gravity(ratio);
    }

    @Nonnull
    public static Gravity metersPerSec(double metersPerSec) {
        return new Gravity(metersPerSec / EARTH_GRAVITY);
    }


    @Nonnull
    public static Gravity jumpHeightOf(double blocks) {
        return new Gravity(DEFAULT_JUMP / blocks);
    }

    @Getter
    private final int percent;
    @Nonnull
    private final PotionEffect effect;
    
    private Gravity(double ratio) {
        this.percent = (int) (100 * ratio);
        if (ratio > 0) {
            int level = (int) (Math.log(ratio) / Math.log(JUMP_BOOST)) * -1;
            this.effect = new PotionEffect(PotionEffectType.JUMP, 200, level - 1, false, false, false);
        } else if (ratio < 0) {
            throw new IllegalArgumentException("Negative gravity is not supported yet!");
        } else {
            throw new IllegalArgumentException("Gravity with 0 block height is unsupported, did you mean to use Gravity.ZERO?");
        }
    }

    /**
     * 0 gravity constructor
     */
    private Gravity() {
        this.effect = new PotionEffect(PotionEffectType.SLOW_FALLING, 200, 0, false, false, false);
        this.percent = 0;
    }
    
    public void applyGravity(@Nonnull Player p) {
        p.addPotionEffect(this.effect);
    }
    
    public void removeGravity(@Nonnull Player p) {
        p.removePotionEffect(this.effect.getType());
    }
    
}
