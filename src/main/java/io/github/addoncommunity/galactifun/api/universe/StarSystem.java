package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.core.GalacticRegistry;
import io.github.addoncommunity.galactifun.core.explorer.GalacticComponent;
import io.github.addoncommunity.galactifun.core.explorer.GalacticHolder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

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
public class StarSystem implements GalacticHolder<CelestialObject>, GalacticComponent {
    
    // TODO add stats of star like heat, size, special effects over planets, etc
    
    private final String name;
    private final List<CelestialObject> objects;
    
    public StarSystem(@Nonnull String name, CelestialObject... objects) {
        this.objects = new ArrayList<>(Arrays.asList(objects));
        this.name = name;
        GalacticRegistry.register(this);
    }
    
    @Nonnull
    @Override
    public ItemStack getDisplayItem() {
        return null;
    }

    @Nonnull
    @Override
    public List<CelestialObject> getComponents() {
        return this.objects;
    }

}
