package io.github.addoncommunity.galactifun.api.universe;

import io.github.addoncommunity.galactifun.core.GalacticRegistry;
import io.github.addoncommunity.galactifun.core.explorer.GalacticComponent;
import io.github.addoncommunity.galactifun.core.explorer.GalacticHolder;
import io.github.addoncommunity.galactifun.core.explorer.TheUniverse;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A galaxy filled with star systems
 *
 * @author Mooy1
 *
 */
@Getter
public class Galaxy implements GalacticHolder<StarSystem>, GalacticComponent {
    
    private final String name;
    private final List<StarSystem> systems;
    
    public Galaxy(@Nonnull String name, @Nonnull StarSystem... systems) {
        this.systems = new ArrayList<>(Arrays.asList(systems));
        this.name = name;
        GalacticRegistry.register(this);
        TheUniverse.INSTANCE.add(this);
    }
    
    @Nonnull
    @Override
    public ItemStack getDisplayItem() {
        return null;
    }

    @Nonnull
    @Override
    public List<StarSystem> getComponents() {
        return this.systems;
    }

}
