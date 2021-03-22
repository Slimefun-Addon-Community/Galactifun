package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.addoncommunity.galactifun.api.universe.world.BossAlien;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.core.GalacticProfile;
import io.github.addoncommunity.galactifun.core.commands.AlienSpawnCommand;
import io.github.addoncommunity.galactifun.core.commands.GalactiportCommand;
import io.github.addoncommunity.galactifun.core.commands.GenSphereCommand;
import io.github.addoncommunity.galactifun.core.categories.CoreCategories;
import io.github.mooy1.infinitylib.commands.CommandManager;
import io.github.mooy1.infinitylib.core.PluginUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public final class Galactifun extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static Galactifun instance;
    
    @Override
    public void onEnable() {
        instance = this;

        PluginUtils.setup("galactifun", this, "Slimefun-Addon-Community/Galactifun/master", getFile());

        CommandManager.setup("galactifun", "/gf, /galactic",
                new GalactiportCommand(), new AlienSpawnCommand(), new GenSphereCommand()
        );
        
        PluginUtils.setupMetrics(10411);

        // todo remove these
        GalacticProfile.loadAll();
        ItemSetup.setup(this);
        
        CoreCategories.setup(this);
        BaseRegistry.setup();
        BaseItems.setup(this);
        
        // log after startup
        PluginUtils.runSync(() -> PluginUtils.log(
                "",
                "################# Galactifun " + getPluginVersion() + " #################",
                "",
                "Loaded " + AlienWorld.getEnabled().size() + " worlds: ",
                AlienWorld.getEnabled().toString(),
                "",
                "Galactifun is open source, you can contribute or report bugs at: ",
                getBugTrackerURL(),
                "Join the Slimefun Addon Community Discord: discord.gg/SqD3gg5SAU",
                "",
                "###################################################",
                ""
        ));
    }

    @Override
    public void onDisable() {
        GalacticProfile.unloadAll();
        GalacticProfile.saveAll();
        
        
        BossAlien.removeBossBars();
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
