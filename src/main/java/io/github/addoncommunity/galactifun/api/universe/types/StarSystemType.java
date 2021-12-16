package io.github.addoncommunity.galactifun.api.universe.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import lombok.NonNull;

import com.google.common.collect.ImmutableSet;

/**
 * Star system types
 *
 * @author Mooy1
 */
public final class StarSystemType extends UniversalType {

    private static final Map<String, StarSystemType> allTypes = new HashMap<>();

    public static final StarSystemType NORMAL = new StarSystemType("Normal", "NORMAL");
    public static final StarSystemType BINARY = new StarSystemType("Binary", "BINARY");

    public StarSystemType(String name, String id) {
        super(name, id);
        allTypes.put(id, this);
    }

    public static StarSystemType getById(@NonNull String id) {
        return allTypes.get(id);
    }

    @Nonnull
    public static Set<StarSystemType> allTypes() {
        return ImmutableSet.copyOf(allTypes.values());
    }

}
