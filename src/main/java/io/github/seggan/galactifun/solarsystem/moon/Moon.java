package io.github.seggan.galactifun.solarsystem.moon;

import io.github.seggan.galactifun.api.CelestialGenerator;
import io.github.seggan.galactifun.api.CelestialObject;
import org.bukkit.potion.PotionEffectType;

public class Moon extends CelestialObject {

    public Moon(String name, int distance, boolean hasAtmosphere, float gravity, long radius) {
        super("Moon", 1, false, 0.16F, 1737100);
    }

    @Override
    public CelestialGenerator getGenerator() {
        return new MoonGenerator();
    }

    @Override
    public PotionEffectType[] getEffects() {
        return new PotionEffectType[0];
    }

    @Override
    public PotionEffectType[] getUnprotectedEffects() {
        return new PotionEffectType[0];
    }
}
