package io.github.addoncommunity.galactifun.util;

import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import io.github.thebusybiscuit.slimefun4.utils.PatternUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;

public final class PersistentBlockPositions implements PersistentDataType<String, BlockPositionSet> {

    public static final PersistentBlockPositions INSTANCE = new PersistentBlockPositions();

    private PersistentBlockPositions() {}

    @Nonnull
    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Nonnull
    @Override
    public Class<BlockPositionSet> getComplexType() {
        return BlockPositionSet.class;
    }

    @Nonnull
    @Override
    public String toPrimitive(@Nonnull BlockPositionSet complex, @Nonnull PersistentDataAdapterContext context) {
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
    public BlockPositionSet fromPrimitive(@Nonnull String primitive, @Nonnull PersistentDataAdapterContext context) {
        BlockPositionSet positions = new BlockPositionSet();
        if (!primitive.isEmpty()) {
            for (String s : PatternUtils.SEMICOLON.split(primitive)) {
                String[] split = PatternUtils.COMMA.split(s);
                positions.add(new BlockPosition(
                        Bukkit.getWorld(UUID.fromString(split[0])),
                        Long.parseLong(split[1])
                ));
            }
        }

        return positions;
    }

}
