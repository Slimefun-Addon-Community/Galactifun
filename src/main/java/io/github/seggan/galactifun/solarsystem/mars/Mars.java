package io.github.seggan.galactifun.solarsystem.mars;

import io.github.seggan.galactifun.api.CelestialGenerator;
import io.github.seggan.galactifun.api.CelestialObject;
import lombok.NonNull;
import org.bukkit.potion.PotionEffectType;

public class Mars extends CelestialObject {

    public Mars() {
        super("Mars", 2, true, 0.3F, 3389500);

        new MarsListener();
    }

    @Override
    @NonNull
    public CelestialGenerator getGenerator() {
        return new MarsGenerator();
    }

    @Override
    @NonNull
    public PotionEffectType[] getEffects() {
        return new PotionEffectType[0];
    }

    @Override
    @NonNull
    public PotionEffectType[] getUnprotectedEffects() {
        return new PotionEffectType[0];
    }
}
