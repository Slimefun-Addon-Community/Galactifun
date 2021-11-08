package io.github.addoncommunity.galactifun.api.items;

import java.util.Set;

import javax.annotation.Nonnull;

import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;

/**
 * A resource that is exclusive to certain {@link PlanetaryWorld}s
 *
 * @author Seggan
 */
public interface ExclusiveGEOResource extends GEOResource {

    /**
     * Returns the {@link PlanetaryWorld}s that this resource is exclusive to
     *
     * @return a {@link Set} of {@link PlanetaryWorld}s. The set <b>must</b> be treated as immutable
     */
    @Nonnull
    Set<PlanetaryWorld> getWorlds();

    @Override
    default void register() {
        GEOResource.super.register();

        for (PlanetaryWorld world : getWorlds()) {
            world.registerGEOResource(this);
        }
    }

}
