package io.github.addoncommunity.galactifun.implementation.lists;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@Getter
@AllArgsConstructor
public enum Heads {
    ROCKET("cae3522392dac7ee4d14d611a8fa9a7ecd4b63318e333b9b83e78f24f59a01b"),
    CORE("dc9365642c6eddcfedf5b5e14e2bc71257d9e4a3363d123c6f33c55cafbf6d"),
    OXYGEN_REGENERATOR("8be7bc66389d96a9c5f4aa5a87579ee661e88ada6f7b8be174bf3982a34f84"),
    FAN("172771fc11737063c81eb75973e9b82590951e3674f31acce8cddd1aad1b"),
    BOX("b2a1d4f4b563827a441f7734e4df3c3b89fc229268c863f1dceedcadfde6423a"),
    LIFE_SUPPORT_MODULE("3625be5ba96fc11448aaddb915a390391c3f92f4af81a4a2adb0e3f4f65"),
    CAN("eb2e58962331676f67a67c0273ed29ba4465986b44e1598e0b8a29c04f82d21a"),
    ;

    @Nonnull
    private final String texture;

    @Nonnull
    public ItemStack getAsItemStack() {
        return SlimefunUtils.getCustomHead(texture);
    }
}
