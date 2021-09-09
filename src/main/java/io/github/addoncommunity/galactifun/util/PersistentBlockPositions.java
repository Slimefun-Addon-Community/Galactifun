package io.github.addoncommunity.galactifun.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.CommonPatterns;

public final class PersistentBlockPositions implements PersistentDataType<String, Set<BlockPosition>> {

    public static final PersistentBlockPositions INSTANCE = new PersistentBlockPositions();

    private PersistentBlockPositions() {}

    @Nonnull
    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Nonnull
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Class<Set<BlockPosition>> getComplexType() {
        return (Class) Set.class;
    }

    @Nonnull
    @Override
    public String toPrimitive(@Nonnull Set<BlockPosition> complex, @Nonnull PersistentDataAdapterContext context) {
        if (complex.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();
        for (BlockPosition pos : complex) {
            builder.append(pos.getWorld().getUID());
            builder.append(',');
            builder.append(pos.getPosition());
            builder.append(';');
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    @Nonnull
    @Override
    public Set<BlockPosition> fromPrimitive(@Nonnull String primitive, @Nonnull PersistentDataAdapterContext context) {
        Set<BlockPosition> positions = new HashSet<>();
        if (!primitive.isEmpty()) {
            for (String s : CommonPatterns.SEMICOLON.split(primitive)) {
                String[] split = CommonPatterns.COMMA.split(s);
                positions.add(new BlockPosition(
                        Bukkit.getWorld(UUID.fromString(split[0])),
                        Long.parseLong(split[1])
                ));
            }
        }

        return positions;
    }

}
