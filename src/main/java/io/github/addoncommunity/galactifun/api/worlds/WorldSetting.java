package io.github.addoncommunity.galactifun.api.worlds;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.addoncommunity.galactifun.Galactifun;
import org.apache.commons.lang.Validate;

/**
 * @param <T> the type of stored data. <b>Must be a {@link String}, {@link Integer}, {@link Boolean},
 *            {@link Long}, or {@link Double}</b>
 */
@ToString
@EqualsAndHashCode
public final class WorldSetting<T> {

    private final CelestialWorld world;
    private final String key;
    private final T defaultValue;

    private final Class<T> clazz;

    @Getter
    @Nonnull
    private T value;

    @SuppressWarnings("unchecked")
    @ParametersAreNonnullByDefault
    public WorldSetting(CelestialWorld world, String key, T defaultValue, Class<T> clazz) {
        this.world = world;
        this.key = key;
        this.defaultValue = defaultValue;
        this.clazz = clazz;

        Validate.isTrue(clazz.equals(String.class) || clazz.equals(Integer.class) ||
                        clazz.equals(Boolean.class) || clazz.equals(Long.class) ||
                        clazz.equals(Double.class),
                "Type parameter of WorldSetting must a String, Integer, Boolean, Long, or Double");


        // gets value or saves the default
        FileConfiguration config = Galactifun.inst().getWorldConfig();
        ConfigurationSection section = config.getConfigurationSection(this.world.getId());
        if (section == null) {
            section = config.createSection(this.world.getId());
        }

        // strings are handled differently
        if (this.clazz.equals(String.class)) {
            String s = section.getString(this.key);
            if (s == null) {
                section.set(this.key, this.defaultValue);
                this.value = this.defaultValue;
            } else {
                this.value = (T) s; // safe cast
            }
        } else {
            T val = section.getObject(this.key, this.clazz);
            if (val == null) {
                section.set(this.key, this.defaultValue);
                this.value = this.defaultValue;
            } else {
                this.value = val;
            }
        }
    }

    public void setValue(@Nonnull T newValue) {
        Validate.notNull(newValue, "newValue must not be null");
        this.value = newValue;
    }

    public void save(@Nonnull FileConfiguration config) {
        config.set(world.getId() + '.' + key, value);
    }

    public static void saveAll() {
        FileConfiguration config = Galactifun.inst().getWorldConfig();
        for (CelestialWorld world : Galactifun.inst().getWorldManager().getAllWorlds()) {
            for (WorldSetting<?> worldSetting : world.getWorldSettings()) {
                worldSetting.save(config);
            }
        }
    }
}
