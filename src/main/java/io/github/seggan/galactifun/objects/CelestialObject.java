package io.github.seggan.galactifun.objects;

import io.github.seggan.galactifun.solarsystem.mars.Mars;
import io.github.seggan.galactifun.solarsystem.moon.Moon;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.apache.commons.lang.Validate;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * A class representing any celestial object (planet, asteroid, moon, etc)
 *
 * @author Seggan
 * @author Mooy1
 *
 */
@Getter
public abstract class CelestialObject extends ChunkGenerator {
    
    public static final CelestialObject MARS = new Mars();
    public static final CelestialObject THE_MOON = new Moon();
    
    @Nonnull private final String name;
    @Nonnull private final World world;
    @Nonnull private final SolarType type;
    @Nonnull private final Atmosphere atmosphere;
    @Nonnull private final CelestialObject[] orbiters;
    private final long distance;
    private final int gravity;
    private final int radius;

    public CelestialObject(@Nonnull String name, long distance, int gravity, int radius,
                           @Nonnull SolarType type, @Nonnull Atmosphere atmosphere, @Nonnull CelestialObject... orbiters) {
        
        Validate.notNull(name);
        Validate.notNull(type);
        Validate.notNull(atmosphere);
        Validate.notNull(orbiters);
        
        this.name = name;
        this.distance = distance;
        this.gravity = gravity;
        this.radius = radius;
        this.orbiters = orbiters;
        this.type = type;
        this.atmosphere = atmosphere;
        
        // will fetch the world if its already been loaded
        World world = new WorldCreator(name.toLowerCase(Locale.ROOT).replace(' ', '_'))
                .generator(this)
                .environment(atmosphere.getEnvironment())
                .createWorld();

        Validate.notNull(world, "There was an error loading the world for " + name);
        
        type.apply(world);
        atmosphere.applyEffects(world);
        
        WorldBorder border = world.getWorldBorder();
        border.setSize(Math.min(60_000_000L, (long) (2 * Math.PI * this.radius)));
        border.setCenter(0, 0);
        // TODO maybe change up border damage stuff?
        
        if (BlockStorage.getStorage(world) == null) {
            new BlockStorage(world);
        }
        
        this.world = world;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CelestialObject && ((CelestialObject) obj).name.equals(this.name);
    }

}
