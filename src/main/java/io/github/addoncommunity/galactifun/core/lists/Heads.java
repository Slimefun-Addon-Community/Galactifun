package io.github.addoncommunity.galactifun.core.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@Getter
@AllArgsConstructor
public enum Heads {
    ROCKET("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2FlMzUyMjM5MmRhYzdlZTRkMTRkNjExYThmYTlhN2VjZDRiNjMzMThlMzMzYjliODNlNzhmMjRmNTlhMDFiIn19fQ=="),
    ;

    @Nonnull
    private final String texture;

    @Nonnull
    public ItemStack getAsItemStack() {
        return SkullItem.fromBase64(texture);
    }
}
