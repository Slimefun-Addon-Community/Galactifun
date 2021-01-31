package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.core.GalacticRegistry;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A star system filled with celestial objects
 *
 * @author Mooy1
 *
 */
@Getter
public class StarSystem {
    
    // TODO add stats of star like heat, size, special effects over planets, etc
    
    private final String name;
    private final List<CelestialObject> objects;
    
    public StarSystem(@Nonnull String name, CelestialObject... objects) {
        this.objects = new ArrayList<>(Arrays.asList(objects));
        this.name = name;
        GalacticRegistry.register(this);
    }

    public final void addObjects(@Nonnull CelestialObject... objects) {
        this.objects.addAll(Arrays.asList(objects));
    }
    
}
