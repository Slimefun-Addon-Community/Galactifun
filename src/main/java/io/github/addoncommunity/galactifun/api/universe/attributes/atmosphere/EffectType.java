package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

/**
 * Types of atmospheric effects
 * 
 * @author Mooy1
 */
public final class EffectType {
    
    public static final EffectType WITHER = new EffectType("Poison", PotionEffectType.WITHER);
    public static final EffectType HEAT = new EffectType("Heat", (p, level) -> {
        if (level > 0) {
            // TODO find a way to increase fire damage?
            p.setFireTicks(200);
        }
    });
    
    @Getter
    @Nonnull
    private final String name;
    
    @Getter
    @Nonnull
    private final EffectApplier applier;
    
    public EffectType(@Nonnull String name, @Nonnull EffectApplier applier) {
        Validate.notNull(name);
        Validate.notNull(applier);
        
        this.name = name;
        this.applier = applier;
    }
    
    public EffectType(@Nonnull String name, @Nonnull PotionEffectType type) {
        Validate.notNull(name);
        Validate.notNull(type);
        
        this.name = name;
        this.applier = (p, level) -> {
            if (level > 0) {
                p.addPotionEffect(new PotionEffect(type, 200, level - 1, false, false));
            }
        };
    }
    
    @FunctionalInterface
    public interface EffectApplier {
        void apply(@Nonnull Player p, int level);
    }
    
}
