package io.github.seggan.galactifun;

import io.github.mooy1.infinitylib.PluginUtils;
import io.github.seggan.galactifun.core.CelestialObjectListener;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.Objects;

public class Galactifun extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static Galactifun instance;
    
    @Override
    public void onEnable() {
        instance = this;

        PluginUtils.setup("galactifun", this, "Slimefun-Addon-Community/Galactifun/master", getFile());
        
        // temporary, replace with CommandManager.setup
        Objects.requireNonNull(getCommand("ptp")).setExecutor(this);

        CelestialObjectListener.setup();

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "Slimefun-Addon-Community/Galactifun/master/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }
    
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        // temporary
        if (!(sender instanceof Player)) {
            return true;
        }

        PaperLib.teleportAsync((Player) sender, Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());

        return true;
    }
}
