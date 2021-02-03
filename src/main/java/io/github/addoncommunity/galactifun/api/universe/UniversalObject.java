package io.github.addoncommunity.galactifun.api.universe;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Any object in the universe
 * 
 * @author Mooy1
 * 
 * @param <T> the object that it can hold
 */
public abstract class UniversalObject<T extends UniversalObject<?>> {
    
    @Nonnull @Getter
    protected final String name;
    
    @Nullable @Getter @Setter
    private UniversalObject<UniversalObject<T>> orbiting;
    
    @Nonnull @Getter
    private final List<UniversalObject<?>> orbiters = new ArrayList<>();
    
    @Nonnull @Getter
    private final ItemStack item;
    
    @SafeVarargs
    public UniversalObject(@Nonnull String name, @Nonnull ItemStack item, @Nonnull T... orbiters) {
        Validate.notNull(name);
        Validate.notNull(item);
        
        this.name = name;
        this.item = item;
        
        addOrbiters(orbiters);
    }
    
    @SafeVarargs
    public final void addOrbiters(@Nonnull T... orbiters) {
        Collections.addAll(this.orbiters, orbiters);
    }

    @Override
    public final int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof UniversalObject<?> && this.name.equals(((UniversalObject<?>) obj).name);
    }

}
