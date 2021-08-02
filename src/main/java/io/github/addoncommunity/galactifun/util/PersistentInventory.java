package io.github.addoncommunity.galactifun.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public final class PersistentInventory implements PersistentDataType<byte[], Inventory> {

    public static final PersistentInventory INSTANCE = new PersistentInventory();

    private PersistentInventory() {}

    @Nonnull
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Nonnull
    @Override
    public Class<Inventory> getComplexType() {
        return Inventory.class;
    }

    @Nonnull
    @Override
    public byte[] toPrimitive(@Nonnull Inventory complex, @Nonnull PersistentDataAdapterContext context) {
        ByteArrayOutputStream barrOut = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new BukkitObjectOutputStream(barrOut)) {
            out.write(complex.getSize());
            for (ItemStack stack : complex.getContents()) {
                out.writeObject(stack);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return barrOut.toByteArray();
    }

    @Nonnull
    @Override
    public Inventory fromPrimitive(@Nonnull byte[] primitive, @Nonnull PersistentDataAdapterContext context) {
        Inventory inventory;
        ByteArrayInputStream barrIn = new ByteArrayInputStream(primitive);
        try (ObjectInputStream in = new BukkitObjectInputStream(barrIn)) {
            inventory = Bukkit.createInventory(null, in.readInt());
            for (int i = 0; i < inventory.getSize(); i++) {
                Object o = in.readObject();
                if (o != null) {
                    inventory.setItem(i, (ItemStack) o);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        return inventory;
    }
}
