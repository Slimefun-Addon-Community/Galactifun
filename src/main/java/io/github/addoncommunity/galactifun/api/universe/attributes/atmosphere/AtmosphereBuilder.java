package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import org.apache.commons.lang.Validate;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;

import javax.annotation.Nonnull;

/**
 * Utility class for making atmospheres
 * 
 * @author Mooy1
 */
public final class AtmosphereBuilder {

    private double oxygenPercentage;
    private double carbonDioxidePercentage;
    private boolean weatherCycle;
    private boolean storming;
    private boolean thundering;
    private boolean flammable;
    @Nonnull
    private World.Environment environment = World.Environment.NORMAL;
    @Nonnull
    private AtmosphericEffect[] effects = new AtmosphericEffect[0];
    
    public AtmosphereBuilder setNether() {
        this.environment = World.Environment.NETHER;
        return this;
    }

    /**
     * Note that adding this method <b>will</b> spawn the {@link EnderDragon}
     *
     * @return this object
     */
    public AtmosphereBuilder setEnd() {
        this.environment = World.Environment.THE_END;
        return this;
    }
    
    public AtmosphereBuilder addEffects(@Nonnull AtmosphericEffect... effects) {
        Validate.notNull(effects);
        this.effects = effects;
        return this;
    }
    
    public AtmosphereBuilder addOxygen(double percentage) {
        Validate.isTrue(percentage >= 0 && percentage + this.carbonDioxidePercentage <= 100);
        this.oxygenPercentage = percentage;
        return this;
    }
    
    public AtmosphereBuilder addCarbonDioxide(double percentage) {
        Validate.isTrue(percentage >= 0 && percentage + this.oxygenPercentage <= 100);
        this.carbonDioxidePercentage = percentage;
        return this;
    }
    
    public AtmosphereBuilder enableWeather() {
        this.weatherCycle = true;
        return this;
    }

    public AtmosphereBuilder addStorm() {
        this.storming = true;
        return this;
    }

    public AtmosphereBuilder addThunder() {
        this.thundering = true;
        return this;
    }

    public AtmosphereBuilder enableFire() {
        this.flammable = true;
        return this;
    }
    
    @Nonnull
    public Atmosphere build() {
        return new Atmosphere(this.oxygenPercentage, this.carbonDioxidePercentage, this.weatherCycle,
                this.storming, this.thundering, this.flammable, this.environment, this.effects);
    }
    
}
