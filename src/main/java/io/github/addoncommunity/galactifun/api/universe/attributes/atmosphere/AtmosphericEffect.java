package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;

/**
 * An effect that can be applied by an atmosphere
 *
 * @author Mooy1
 * @author Seggan
 */
public record AtmosphericEffect(@Nonnull String id, @Nonnull String name,
                                @Nonnull BiConsumer<Player, Integer> applier) {

    public static final AtmosphericEffect RADIATION = new AtmosphericEffect("RADIATION", PotionEffectType.WITHER);
    public static final AtmosphericEffect HEAT = new AtmosphericEffect("HEAT", (player, level) -> player.setFireTicks(240 * level));
    public static final AtmosphericEffect COLD = new AtmosphericEffect("COLD", (player, level) -> {
        player.damage(level * 2);
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SLOW,
                200,
                Math.min(200, level),
                false,
                false,
                false
        ));
    });

    public AtmosphericEffect(@Nonnull String id, @Nonnull BiConsumer<Player, Integer> applier) {
        this(id, ChatUtils.humanize(id), applier);
    }

    public AtmosphericEffect(@Nonnull String id, @Nonnull String name, @Nonnull PotionEffectType effectType) {
        this(id, name, (player, level) -> player.addPotionEffect(new PotionEffect(
                effectType,
                200,
                Math.min(200, level - 1), // i think the max is 255 but to be on the safe side
                false,
                false,
                false
        )));
    }

    public AtmosphericEffect(@Nonnull String id, @Nonnull PotionEffectType effectType) {
        this(id, ChatUtils.humanize(id), effectType);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof AtmosphericEffect other)) return false;

        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
