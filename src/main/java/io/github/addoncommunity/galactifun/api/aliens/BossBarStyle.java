package io.github.addoncommunity.galactifun.api.aliens;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public final record BossBarStyle(String name, BarColor color, BarStyle style, BarFlag... flags) {

    public BossBar create(NamespacedKey key) {
        return Bukkit.createBossBar(key, this.name, this.color, this.style, this.flags);
    }

}
