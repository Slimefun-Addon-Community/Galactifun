package io.github.seggan.galactifun.api;

import io.github.seggan.galactifun.Galactifun;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
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
     * The radius of the body int meters. Used for determining the world border size
     */
    private final long radius;
    /**
     * If {@code true}, you can be on the surface of this body without a space suit
     */
    private final boolean isEarthLike;

    public CelestialObject(String name, int distance, boolean hasAtmosphere, float gravity, long radius) {
        this(name, distance, hasAtmosphere, gravity, radius, false);
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
    @NonNull
    public abstract PotionEffectType[] getUnprotectedEffects();

    @NonNull
    public final String getWorldName() {
        return name.toLowerCase(Locale.ROOT).replace(" ", "_");
    }

    public void register(Galactifun galactifun) {
        galactifun.addCelestial(this);

        World world = Bukkit.getWorld(this.getWorldName());
        if (world != null) return;

        world = new WorldCreator(this.getWorldName())
            .generator(this.getGenerator())
            .createWorld();

        assert world != null;

        if (!this.hasAtmosphere) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setTime(18000L);
        }

        long newBorder = (long) ((long) 2 * Math.PI * radius);
        if (newBorder < 60000000) {
            WorldBorder border = world.getWorldBorder();
            border.setCenter(0, 0);
            border.setSize(newBorder);
        }
    }
}
