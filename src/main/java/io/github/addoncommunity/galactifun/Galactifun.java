package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.api.mob.Alien;
import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.core.listener.AlienListener;
import io.github.addoncommunity.galactifun.core.tasks.AlienTicker;
import io.github.addoncommunity.galactifun.core.GalacticCategory;
import io.github.addoncommunity.galactifun.core.listener.GalacticListener;
import io.github.addoncommunity.galactifun.core.tasks.GalacticTicker;
import io.github.addoncommunity.galactifun.core.commands.AlienSpawnCommand;
import io.github.addoncommunity.galactifun.core.commands.GalactiportCommand;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.command.CommandManager;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class Galactifun extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static Galactifun instance;
    
    @Override
    public void onEnable() {
        instance = this;

        PluginUtils.setup("galactifun", this, "Slimefun-Addon-Community/Galactifun/master", getFile());

        CommandManager.setup("galactifun", "galactifun.admin", "/gf, /galactic",
                new GalactiportCommand(), new AlienSpawnCommand()
        );
        
        new GalacticListener();
        new AlienListener();

        GalacticCategory.setup(this);
        
        BaseRegistry.setup();
        
        PluginUtils.scheduleRepeatingSync(new GalacticTicker(), 10, GalacticTicker.INTERVAL);
        PluginUtils.scheduleRepeatingSync(new AlienTicker(), 10, AlienTicker.INTERVAL);
        
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
    
}
