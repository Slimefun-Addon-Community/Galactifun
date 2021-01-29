package io.github.seggan.galactifun.solarsystem.moon;

import lombok.NonNull;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

public class Moon extends LargeCelestialObject {

    public Moon(String name, int distance, boolean hasAtmosphere, float gravity, long radius) {
        super("Moon", 1, false, 0.16F, 1737100);
    }

    @Nonnull
    @NonNull
    @Override
    public CelestialGenerator getGenerator() {
        return null;
    }

    @Nonnull
    @NonNull
    @Override
    public PotionEffectType[] getNormalEffects() {
        return new PotionEffectType[0];
    }

    @Nonnull
    @NonNull
    @Override
    public PotionEffectType[] getUnprotectedEffects() {
        return new PotionEffectType[0];
    }
}
