package io.github.addoncommunity.galactifun.implementation.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@Getter
@AllArgsConstructor
public enum Heads {
    ROCKET("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2FlMzUyMjM5MmRhYzdlZTRkMTRkNjExYThmYTlhN2VjZDRiNjMzMThlMzMzYjliODNlNzhmMjRmNTlhMDFiIn19fQ=="),
    CORE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM5MzY1NjQyYzZlZGRjZmVkZjViNWUxNGUyYmM3MTI1N2Q5ZTRhMzM2M2QxMjNjNmYzM2M1NWNhZmJmNmQifX19"),
    OXYGEN_REGENERATOR("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJlN2JjNjYzODlkOTZhOWM1ZjRhYTVhODc1NzllZTY2MWU4OGFkYTZmN2I4YmUxNzRiZjM5ODJhMzRmODQifX19"),
    FAN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcyNzcxZmMxMTczNzA2M2M4MWViNzU5NzNlOWI4MjU5MDk1MWUzNjc0ZjMxYWNjZThjZGRkMWFhZDFiIn19fQ=="),
    ;

    @Nonnull
    private final String texture;

    @Nonnull
    public ItemStack getAsItemStack() {
        return SkullItem.fromBase64(texture);
    }
}
