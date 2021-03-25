package io.github.addoncommunity.galactifun.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

public class PersistentLocation implements PersistentDataType<String, Location> {

    public static final PersistentLocation TYPE = new PersistentLocation();

    @Nonnull
    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Nonnull
    @Override
    public Class<Location> getComplexType() {
        return Location.class;
    }

    @Nonnull
    @Override
    public String toPrimitive(@Nonnull Location complex, @Nonnull PersistentDataAdapterContext context) {
        return String.format("%s %d %d %d",
            complex.getWorld().getName(),
            complex.getBlockX(),
            complex.getBlockY(),
            complex.getBlockZ()
        );
    }

    @Nonnull
    @Override
    public Location fromPrimitive(@Nonnull String primitive, @Nonnull PersistentDataAdapterContext context) {
        String[] strings = Util.SPACE_PATTERN.split(primitive);

        int x = Integer.parseInt(strings[1]);
        int y = Integer.parseInt(strings[2]);
        int z = Integer.parseInt(strings[3]);

        return new Location(Bukkit.getWorld(strings[0]), x, y, z);
    }
}
