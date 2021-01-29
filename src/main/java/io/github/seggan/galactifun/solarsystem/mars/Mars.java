package io.github.seggan.galactifun.solarsystem.mars;

import lombok.NonNull;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

public class Mars extends LargeCelestialObject {

    public Mars() {
        super("Mars", 2, true, 0.3F, 3389500);

        new MarsListener();
    }

    @Nonnull
    @Override
    @NonNull
    public CelestialGenerator getGenerator() {
        return new MarsGenerator();
    }

    @Nonnull
    @Override
    @NonNull
    public PotionEffectType[] getNormalEffects() {
        return new PotionEffectType[0];
    }

    @Nonnull
    @Override
    @NonNull
    public PotionEffectType[] getUnprotectedEffects() {
        return new PotionEffectType[0];
    }
}
