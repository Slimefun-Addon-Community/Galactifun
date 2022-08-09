package io.github.addoncommunity.galactifun.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.World;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;

final class BlockPositionsDataType implements PersistentDataType<long[], Set<BlockPosition>> {

    // Used for reconstructing the BlockPositions from the long array. I don't feel like using a string.
    // This class is only accessible to ChunkStorage anyway, so it's fine.
    World currentWorld;

    @Nonnull
    @Override
    public Class<long[]> getPrimitiveType() {
        return long[].class;
    }

    @Nonnull
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Class<Set<BlockPosition>> getComplexType() {
        return (Class) Set.class;
    }

    @Nonnull
    @Override
    public long[] toPrimitive(@Nonnull Set<BlockPosition> complex, @Nonnull PersistentDataAdapterContext context) {
        long[] array = new long[complex.size()];
        Iterator<BlockPosition> iterator = complex.iterator();
        for (int i = 0; i < array.length; i++) {
            BlockPosition pos = iterator.next();
            array[i] = pos.getPosition();
        }
        return array;
    }

    @Nonnull
    @Override
    public Set<BlockPosition> fromPrimitive(@Nonnull long[] primitive, @Nonnull PersistentDataAdapterContext context) {
        Set<BlockPosition> positions = new HashSet<>();
        for (long l : primitive) {
            positions.add(new BlockPosition(currentWorld, l));
        }
        return positions;
    }
}
