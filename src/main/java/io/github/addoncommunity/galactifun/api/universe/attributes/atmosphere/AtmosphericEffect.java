package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * An effect that can be applied by an atmosphere
 *
 * @author Mooy1
 */
public record AtmosphericEffect(
    @Nonnull EffectType type, int level) {

    public AtmosphericEffect(@Nonnull EffectType type, int level) {
        Validate.notNull(type);
        Validate.isTrue(level > 0);

        this.type = type;
        this.level = level;
    }

    public void apply(@Nonnull Player p) {
        int protection = 0; // TODO replace with protection system
        this.type.getApplier().apply(p, this.level - protection);
    }

}
