package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuitStat;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;

/**
 * An effect that can be applied by an atmosphere
 *
 * @author Mooy1
 * @author Seggan
 */
@Getter
@AllArgsConstructor
@ParametersAreNonnullByDefault
public final class AtmosphericEffect {

    public static final AtmosphericEffect RADIATION = new AtmosphericEffect("RADIATION", SpaceSuitStat.RADIATION_RESISTANCE, PotionEffectType.WITHER);
    public static final AtmosphericEffect HEAT = new AtmosphericEffect("HEAT", SpaceSuitStat.HEAT_RESISTANCE,
            (player, level) -> {
                player.setFireTicks(240 * level);
                if (level > 3) {
                    player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                }
            });
    public static final AtmosphericEffect COLD = new AtmosphericEffect("COLD", SpaceSuitStat.COLD_RESISTANCE,
            (player, level) -> {
                player.damage(level * 2);
                player.addPotionEffect(new PotionEffect(
                        PotionEffectType.SLOW,
                        200,
                        Math.min(200, level),
                        false,
                        false,
                        false
                ));
                player.setFreezeTicks(150 * level);
            });

    private final String id;
    @Nullable
    private final SpaceSuitStat stat;
    private final BiConsumer<Player, Integer> applier;

    public AtmosphericEffect(@Nonnull String id, @Nullable SpaceSuitStat stat, @Nonnull PotionEffectType effectType) {
        this(id, stat, (player, level) -> player.addPotionEffect(new PotionEffect(
                effectType,
                200,
                Math.min(200, level - 1), // i think the max is 255 but to be on the safe side
                false,
                false,
                false
        )));
    }

    @Override
    public String toString() {
        return ChatUtils.humanize(this.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
