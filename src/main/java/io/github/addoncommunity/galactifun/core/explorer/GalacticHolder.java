package io.github.addoncommunity.galactifun.core.explorer;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Represents a GalacticComponent that holds other GalacticComponents
 *
 * @author Mooy1
 */
public interface GalacticHolder<T extends GalacticComponent> {

    @Nonnull List<T> getComponents();
    @Nonnull String getName();

    default void add(T object) {
        getComponents().add(object);
    }
    
}
