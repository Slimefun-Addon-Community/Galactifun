package io.github.addoncommunity.galactifun;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.mooy1.infinitylib.metrics.bukkit.Metrics;
import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;


public final class Galactifun extends AbstractAddon {

    private static Galactifun instance;

    private boolean shouldDisable = false;

    public Galactifun() {
        super("Slimefun-Addon-Community", "Galactifun", "master", "auto-update");
    }

    public Galactifun(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file, "Slimefun-Addon-Community", "Galactifun", "master", "auto-update");
    }

    @Override
    protected void enable() {
        instance = this;

        if (!PaperLib.isPaper()) {
            log(Level.SEVERE, "Galactifun only supports Paper and its forks (i.e. Airplane and Purpur)");
            log(Level.SEVERE, "Please use Paper or a fork of Paper");
            shouldDisable = true;
        }
        if (Slimefun.getMinecraftVersion().isBefore(MinecraftVersion.MINECRAFT_1_17)) {
            log(Level.SEVERE, "Galactifun only supports Minecraft 1.17 and above");
            log(Level.SEVERE, "Please use Minecraft 1.17 or above");
            shouldDisable = true;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("ClayTech")) {
            log(Level.SEVERE, "Galactifun will not work properly with ClayTech");
            log(Level.SEVERE, "Please disable ClayTech");
            shouldDisable = true;
        }

        if (shouldDisable) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        new Metrics(this, 11613);

        // log after startup
        Scheduler.run(() -> log(Level.INFO,
                "################# Galactifun " + getPluginVersion() + " #################",
                "",
                "Galactifun is open source, you can contribute or report bugs at: ",
                getBugTrackerURL(),
                "Join the Slimefun Addon Community Discord: discord.gg/SqD3gg5SAU",
                "",
                "###################################################"
        ));
    }

    @Override
    protected void disable() {
        if (shouldDisable) return;

        // Do this last
        instance = null;
    }

    @Override
    public void load() {
        // Default to not logging world settings
        Bukkit.spigot().getConfig().set("world-settings.default.verbose", false);
    }
}
