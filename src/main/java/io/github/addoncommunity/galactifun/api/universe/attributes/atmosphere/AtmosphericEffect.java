package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

    public static final AtmosphericEffect RADIOACTIVE = new AtmosphericEffect("Radioactive", PotionEffectType.WITHER, 3);
    public static final AtmosphericEffect HEAT = new AtmosphericEffect("Heat", (player, integer) -> {
        if (integer != 0) {
            player.setFireTicks(300);
        }
    });

    @Getter
    @Nonnull
    private final String name;
    @Nonnull
    private final BiConsumer<Player, Integer> effect;

    public AtmosphericEffect(@Nonnull String name, @Nonnull PotionEffectType effect, int level) {
        this.name = name;
        this.effect = (player, integer) -> {
            if (level - integer > 0) {
                player.addPotionEffect(new PotionEffect(effect, 300, level - integer, false, false));
            }
        };
    }

    public void apply(Player p) {
        // TODO add protection
        this.effect.accept(p, 0);
    }

}
