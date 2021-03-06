package io.github.addoncommunity.galactifun.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.SerializationException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@UtilityClass
public class SerialUtils {

    @Nonnull
    public static String serializeItemStack(@Nonnull ItemStack stack) {
        YamlConfiguration configuration = new YamlConfiguration();
        configuration.set("stack", stack);
        return configuration.saveToString();
    }

    @Nonnull
    public static ItemStack deserializeItemStack(@Nonnull String serialized) {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.loadFromString(serialized);
        } catch (InvalidConfigurationException e) {
            throw new SerializationException(e);
        }

        ItemStack stack = configuration.getItemStack("stack");
        if (stack == null) {
            throw new SerializationException("ItemStack null!");
        }

        return stack;
    }

    @Nonnull
    public static String serializeItemStacks(@Nonnull ItemStack... stacks) {
        FileConfiguration configuration = new YamlConfiguration();
        int i = 0;
        for (ItemStack stack : stacks) {
            configuration.set(String.valueOf(i++), stack);
        }
        return configuration.saveToString();
    }

    @Nonnull
    public static String serializeItemStacks(@Nonnull Collection<ItemStack> stacks) {
        return serializeItemStacks(stacks.toArray(new ItemStack[0]));
    }

    @Nonnull
    public static List<ItemStack> deserializeItemStacks(@Nonnull String serialized) {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.loadFromString(serialized);
        } catch (InvalidConfigurationException e) {
            throw new SerializationException(e);
        }

        List<ItemStack> stacks = new ArrayList<>();
        for (String key : configuration.getKeys(false)) {
            ItemStack item = configuration.getItemStack(key);
            if (item != null) {
                stacks.add(item);
            }
        }

        return stacks;
    }
}
