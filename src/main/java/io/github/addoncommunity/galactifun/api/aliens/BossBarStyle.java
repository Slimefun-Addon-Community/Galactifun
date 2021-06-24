package io.github.addoncommunity.galactifun.api.aliens;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public final class BossBarStyle {

    private final BarColor color;
    private final BarStyle style;
    private final BarFlag[] flags;

    public BossBarStyle(BarColor color, BarStyle barStyle, BarFlag... barFlags) {
        this.color = color;
        this.style = barStyle;
        this.flags = barFlags;
    }

    @Nonnull
    public BossBar create(NamespacedKey key, String name) {
        return Bukkit.createBossBar(key, name, this.color, this.style, this.flags);
    }

}
