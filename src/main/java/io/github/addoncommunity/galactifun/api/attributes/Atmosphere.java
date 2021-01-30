package io.github.addoncommunity.galactifun.api.attributes;

import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

/**
 * An atmosphere of a celestial object
 *
 * @author Mooy1
 *
 */
@Getter
public class Atmosphere {

    public static final Atmosphere EARTH_LIKE = new Atmosphere(
            21, true, false, false, true, World.Environment.NORMAL
    );
    public static final Atmosphere MARS_LIKE = new Atmosphere(
            0, false, false, false, false, World.Environment.NETHER
    );
    public static final Atmosphere MOON_LIKE = new Atmosphere(
            0, false, false, false, false,World.Environment.NORMAL
    );

    private final int oxygenPercentage;
    private final boolean weatherCycle;
    private final boolean storming;
    private final boolean thundering;
    private final boolean flammable;
    @Nonnull private final PotionEffectType[] normalEffects;
    @Nonnull private final PotionEffectType[] unprotectedEffects;
    @Nonnull private final World.Environment environment;
    
    public Atmosphere(int oxygenRatio, boolean weatherCycle, boolean storming, boolean thundering, boolean flammable, @Nonnull World.Environment environment,
                      @Nonnull PotionEffectType[] normalEffects, @Nonnull PotionEffectType[] unprotectedEffects) {
        
        Validate.isTrue(oxygenRatio <= 100 && oxygenRatio >= 0);
        
        this.oxygenPercentage = oxygenRatio;
        this.weatherCycle = weatherCycle;
        this.environment = environment;
        this.thundering = thundering;
        this.storming = storming;
        this.flammable = flammable;
        
        // TODO these should be improved to be in a tiered system with types of effects such as heat, radioactivity, acid
        this.normalEffects = normalEffects;
        this.unprotectedEffects = unprotectedEffects;
    }
    
    public Atmosphere(int oxygenRatio, boolean weatherCycle, boolean storming, boolean thundering, boolean flammable, @Nonnull World.Environment environment) {
        this(oxygenRatio, weatherCycle, storming, thundering, flammable, environment, new PotionEffectType[0], new PotionEffectType[0]);
    }
    
    public void applyEffects(@Nonnull World world) {
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, this.weatherCycle);
        world.setStorm(this.storming);
        world.setThundering(this.thundering);
        world.setGameRule(GameRule.DO_FIRE_TICK, this.flammable);
    }
    
}
