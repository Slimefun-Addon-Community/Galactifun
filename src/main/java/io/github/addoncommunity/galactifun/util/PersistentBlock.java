package io.github.addoncommunity.galactifun.util;

import io.github.thebusybiscuit.slimefun4.utils.PatternUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public final class PersistentBlock implements PersistentDataType<String, Block> {

    public static final PersistentBlock BLOCK = new PersistentBlock();

    @Nonnull
    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Nonnull
    @Override
    public Class<Block> getComplexType() {
        return Block.class;
    }

    @Nonnull
    @Override
    public String toPrimitive(@Nonnull Block complex, @Nonnull PersistentDataAdapterContext context) {
        return complex.getWorld().getName() + ';' + complex.getX() + ';' + complex.getY() + ';' + complex.getZ();
    }

    @Nonnull
    @Override
    public Block fromPrimitive(@Nonnull String primitive, @Nonnull PersistentDataAdapterContext context) {
        String[] strings = PatternUtils.SEMICOLON.split(primitive);
        return new Location(
                Bukkit.getWorld(strings[0]),
                Integer.parseInt(strings[1]),
                Integer.parseInt(strings[2]),
                Integer.parseInt(strings[3])
        ).getBlock();
    }
}
