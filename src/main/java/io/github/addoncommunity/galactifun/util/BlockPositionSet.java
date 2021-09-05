package io.github.addoncommunity.galactifun.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;

import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;

/**
 * Utility class to avoid parameterized types in the {@link PersistentBlockPositions}
 */
public final class BlockPositionSet implements Set<BlockPosition> {

    private final Set<BlockPosition> delegate = new HashSet<>();

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    @Nonnull
    @Override
    public Iterator<BlockPosition> iterator() {
        return delegate.iterator();
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return delegate.toArray(new BlockPosition[0]);
    }

    @Nonnull
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        return delegate.toArray(a);
    }

    @Override
    public boolean add(BlockPosition position) {
        return delegate.add(position);
    }

    @Override
    public boolean remove(Object o) {
        return delegate.remove(o);
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        return delegate.containsAll(c);
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends BlockPosition> c) {
        return delegate.addAll(c);
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        return delegate.retainAll(c);
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        return delegate.removeAll(c);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

}
