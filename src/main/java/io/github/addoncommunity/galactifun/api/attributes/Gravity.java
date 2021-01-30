package io.github.addoncommunity.galactifun.api.attributes;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public class Gravity {
    private final int gravity;

    public void applyGravity(Player p) {
        int g = -this.gravity - 1;

        if (g != -1) {
            if (g < 0) {
                g += 1;
            }

            p.removePotionEffect(PotionEffectType.JUMP);
            p.removePotionEffect(PotionEffectType.SLOW_FALLING);
            new PotionEffect(PotionEffectType.JUMP, 2147483647, g).apply(p);
            if (g > 0) {
                new PotionEffect(PotionEffectType.SLOW_FALLING, 2147483647, (g - 1) / 2).apply(p);
            }
        }
    }
}
