package io.github.addoncommunity.galactifun.util;

import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A utility class that lets you choose between using a material or head texture for an item
 * 
 * @author Mooy1
 */
public final class ItemChoice {

    @Getter
    @Nonnull
    protected final ItemStack item;

    public ItemChoice(@Nonnull String texture) {
        Validate.notNull(texture);
        this.item = SkullItem.fromBase64(texture);
    }

    public ItemChoice(@Nonnull Material material) {
        Validate.notNull(material);
        this.item = new ItemStack(material);
    }

}