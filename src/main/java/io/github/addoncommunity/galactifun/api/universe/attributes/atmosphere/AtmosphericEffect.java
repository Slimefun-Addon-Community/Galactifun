package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;
import lombok.NonNull;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Ints;
import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuitStat;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;

/**
 * An effect that can be applied by an atmosphere
 *
 * @author Mooy1
 * @author Seggan
 */
public final class AtmosphericEffect {

    private static final Map<String, AtmosphericEffect> allEffects = new HashMap<>();

    public static final AtmosphericEffect RADIATION = new AtmosphericEffect("RADIATION",
            SpaceSuitStat.RADIATION_RESISTANCE, PotionEffectType.WITHER);
    public static final AtmosphericEffect HEAT = new AtmosphericEffect("HEAT",
            SpaceSuitStat.HEAT_RESISTANCE, (player, level) -> {
        player.setFireTicks(Ints.constrainToRange(240 * level, 0, player.getMaxFireTicks()));
        if (level > 3) {
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }
    });
    public static final AtmosphericEffect COLD = new AtmosphericEffect("COLD",
            SpaceSuitStat.COLD_RESISTANCE, (player, level) -> {
        player.damage(level * 2);
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SLOW,
                200,
                Math.min(200, level),
                false,
                false,
                false
        ));
        player.setFreezeTicks(Ints.constrainToRange(150 * level, 0, player.getMaxFreezeTicks()));
    });

    @Getter
    private final String id;
    @Getter
    @Nullable
    private final SpaceSuitStat stat;
    private final BiConsumer<Player, Integer> applier;

    public AtmosphericEffect(@NonNull String id, @Nullable SpaceSuitStat stat, @NonNull PotionEffectType effectType) {
        this(id, stat, (player, level) -> player.addPotionEffect(new PotionEffect(
                effectType,
                200,
                Math.min(200, level - 1), // i think the max is 255 but to be on the safe side
                false,
                false,
                false
        )));
    }

    public AtmosphericEffect(@NonNull String id, @Nullable SpaceSuitStat stat, @NonNull BiConsumer<Player, Integer> applier) {
        this.id = id;
        this.stat = stat;
        this.applier = applier;

        allEffects.put(id, this);
    }

    public static AtmosphericEffect getById(@NonNull String id) {
        return allEffects.get(id);
    }

    @Nonnull
    public static Set<AtmosphericEffect> allEffects() {
        return ImmutableSet.copyOf(allEffects.values());
    }

    public void apply(@NonNull Player p, int level) {
        if (level > 0) {
            p.sendMessage(ChatColor.RED + "You have been exposed to " + this + "!");
            this.applier.accept(p, level);
        }
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
