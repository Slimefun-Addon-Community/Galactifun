package io.github.addoncommunity.galactifun.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class PeristentBlock implements PersistentDataType<String, Block> {

    public static final PeristentBlock TYPE = new PeristentBlock();

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
        Location l = complex.getLocation();
        return String.format("%s %d %d %d",
            l.getWorld().getName(),
            l.getBlockX(),
            l.getBlockY(),
            l.getBlockZ()
        );
    }

    @Nonnull
    @Override
    public Block fromPrimitive(@Nonnull String primitive, @Nonnull PersistentDataAdapterContext context) {
        String[] strings = Util.SPACE_PATTERN.split(primitive);

        int x = Integer.parseInt(strings[1]);
        int y = Integer.parseInt(strings[2]);
        int z = Integer.parseInt(strings[3]);

        return new Location(Bukkit.getWorld(strings[0]), x, y, z).getBlock();
    }
}
