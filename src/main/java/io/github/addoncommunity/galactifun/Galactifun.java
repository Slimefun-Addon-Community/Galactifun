package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.core.AlienTicker;
import io.github.addoncommunity.galactifun.core.GalacticCategory;
import io.github.addoncommunity.galactifun.core.GalacticListener;
import io.github.addoncommunity.galactifun.core.GalacticTicker;
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
        
        GalacticListener.setup();

        GalacticCategory.setup(this);
        
        BaseRegistry.setup();
        
        PluginUtils.scheduleRepeatingSync(new GalacticTicker(), 10, GalacticTicker.INTERVAL);

        AlienTicker ticker = new AlienTicker();
        PluginUtils.scheduleRepeatingSync(ticker, ticker.INTERVAL, ticker.INTERVAL);
        
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
