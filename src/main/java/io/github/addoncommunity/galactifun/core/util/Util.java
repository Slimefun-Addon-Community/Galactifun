package io.github.addoncommunity.galactifun.core.util;

import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;

/**
 * Utilities
 * 
 * @author Seggan
 * @author Mooy1
 */
@UtilityClass
public final class Util {

    public static final double LY_TO_KM = 9_700_000_000D;
    
    @Nonnull
    public static String stripUntranslatedColors(@Nonnull String string) {
        return ChatColor.stripColor(ChatColors.color(string));
    }
    
}
