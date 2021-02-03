package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.api.universe.CelestialBody;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.World;

import javax.annotation.Nonnull;

/**
 * Any world that can be travelled to by rockets or other means
 * 
 * @author Mooy1
 */
public abstract class ACelestialWorld extends CelestialBody {

    @Getter @Nonnull
    protected final World world;

    public ACelestialWorld(@Nonnull String name, long distance, long surfaceArea, @Nonnull Gravity gravity,
                           @Nonnull Material material, @Nonnull DayCycle dayCycle, @Nonnull Terrain terrain, @Nonnull Atmosphere atmosphere) {
        super(name, distance, surfaceArea, gravity, material, dayCycle, terrain, atmosphere);
        this.world = createWorld();
    }

    @Nonnull
    protected abstract World createWorld();

}
