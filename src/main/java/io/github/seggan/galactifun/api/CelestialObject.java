package io.github.seggan.galactifun.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.World;
import org.bukkit.potion.PotionEffectType;

import java.util.Locale;

/**
 * A class representing a celestial object (planet, moon, etc)
 *
 * @author Seggan
 */
@Getter
@AllArgsConstructor
public abstract class CelestialObject {

    @NonNull
    private final String name;
    private final int distance;
    /**
     * Used for determining whether plants can grow
     */
    private final boolean hasAtmosphere;
    /**
     * Used to determine whether to apply jump boost/slowness
     */
    private final float gravity;
    /**
     * If {@code true}, you can be on the surface of this body without a space suit
     */
    private final boolean isEarthLike;

    public CelestialObject(String name, int distance, boolean hasAtmosphere, float gravity) {
        this(name, distance, hasAtmosphere, gravity, false);
    }

    /**
     * Gets the chunk generator for the celestial
     *
     * @return a CelestialGenerator for the celestial
     */
    @NonNull
    public abstract CelestialGenerator getGenerator();

    /**
     * A list of potion effects to apply when on the celestial's surface
     *
     * @return a list of potion effects
     */
    @NonNull
    public abstract PotionEffectType[] getEffects();

    /**
     * A list of additional potion effects to apply when on the celestial's surface without wearing a space suit
     *
     * @return a list of potion effects
     */
    public abstract PotionEffectType[] getUnprotectedEffects();

    @NonNull
    public final String getWorldName(@NonNull World earth) {
        return earth.getName() + name.toLowerCase(Locale.ROOT).replace(" ", "_");
    }
}
