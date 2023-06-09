package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

import lombok.Getter;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Gas {
    @Getter
    protected SlimefunItemStack item;

    protected String id;
    protected String name;

    public Gas(String id, String name, String texture) {
        this.id = id;
        this.name = name;

        this.item = new SlimefunItemStack(
                "ATMOSPHERIC_GAS_" + name(),
                SlimefunUtils.getCustomHead(texture),
                "&f" + ChatUtils.humanize(name) + " 气罐"
        );

        new SlimefunItem(CoreItemGroup.ITEMS, this.item, RecipeType.NULL, new ItemStack[9]).register(Galactifun.instance());
    }

    public Gas(String id, String name) {
        this.id = id;
        this.name = name;
        this.item = null;
    }


    @Nonnull
    @Override
    public String toString() {
        return ChatUtils.humanize(this.name());
    }

    public String name() {
        return id;
    }

    public String getName() {
        return name;
    }

}
