package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.function.BiConsumer;

import lombok.AllArgsConstructor;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * An effect that can be applied by an atmosphere
 *
 * @author Mooy1
 */
@AllArgsConstructor
public final class AtmosphericEffect {

    public static final AtmosphericEffect HEAT = new AtmosphericEffect((player, protection) -> {
        if (protection != 0) {
            player.setFireTicks(240);
        }
    });
    public static final AtmosphericEffect RADIATION = new AtmosphericEffect(PotionEffectType.WITHER, 3);

    private final BiConsumer<Player, Integer> applier;

    public AtmosphericEffect(PotionEffectType potionEffectType, int level) {
        this.applier = (player, protection) -> {
            int lvl = level - protection;
            if (lvl > 0) {
                player.addPotionEffect(new PotionEffect(potionEffectType, 240, lvl - 1));
            }
        };
    }

    public void apply(Player p) {
        // TODO implement protection
        this.applier.accept(p, 0);
    }

}
