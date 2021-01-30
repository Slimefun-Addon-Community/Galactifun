package io.github.addoncommunity.galactifun.api.attributes;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lombok.Getter;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;

/**
 * Represents the gravitational pull.
 *
 * @author GallowsDove
 * @author Mooy1
 *
 */
public class Gravity {
    
    public static final Gravity EARTH_LIKE = new Gravity(1);
    public static final Gravity MOON_LIKE = new Gravity(.165);
    public static final Gravity MARS_LIKE = new Gravity(.378);
    
    private final int jump;
    private final int speed;
    
    public Gravity(double relativeToEarth) {
        int affect;
        if (relativeToEarth < 1) {
            affect = (int) ((1 / relativeToEarth) - 1);
        } else {
            affect = (int) (-1 * (relativeToEarth - 1));
        }
        this.jump = affect - 1;
        this.speed = (affect / 2) - 1;
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
