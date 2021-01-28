package io.github.seggan.galactifun.solarsystem.moon;

import io.github.seggan.galactifun.api.CelestialGenerator;
import io.github.seggan.galactifun.api.CelestialObject;
import lombok.NonNull;
import org.bukkit.potion.PotionEffectType;

public class Moon extends CelestialObject {

    public Moon(String name, int distance, boolean hasAtmosphere, float gravity, long radius) {
        super("Moon", 1, false, 0.16F, 1737100);
    }

    @NonNull
    @Override
    public CelestialGenerator getGenerator() {
        return null;
    }

    @NonNull
    @Override
    public PotionEffectType[] getEffects() {
        return new PotionEffectType[0];
    }

    @NonNull
    @Override
    public PotionEffectType[] getUnprotectedEffects() {
        return new PotionEffectType[0];
    }
}
