package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.addoncommunity.galactifun.api.universe.world.BossAlien;
import io.github.addoncommunity.galactifun.api.universe.world.PersistentAlien;
import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.core.GalacticProfile;
import io.github.addoncommunity.galactifun.core.commands.AlienSpawnCommand;
import io.github.addoncommunity.galactifun.core.commands.GalactiportCommand;
import io.github.addoncommunity.galactifun.core.commands.GenSphereCommand;
import io.github.addoncommunity.galactifun.core.commands.StructureCommand;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.command.CommandManager;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import org.bukkit.World;
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
                new GalactiportCommand(), new AlienSpawnCommand(), new GenSphereCommand(), new StructureCommand()
        );
        
        PluginUtils.setupMetrics(10411);

        GalacticProfile.loadAll();
        
        Categories.setup(this);

        Setup.setup(this);
        
        BaseRegistry.setup();

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
        
        // load entities after aliens are created
        PluginUtils.runSync(PersistentAlien::loadAll);

        // Schedule time tickers for the enabled worlds after world classes are set up
        PluginUtils.runSync(() -> {
            for (AlienWorld world : AlienWorld.getEnabled()) {
                if (!world.getDayCycle().isEternal() && world.getWorld().getEnvironment() == World.Environment.NORMAL) {
                    PluginUtils.scheduleRepeatingSync(() -> {
                        world.getDayCycle().tick(world.getWorld());
                    }, 1, 1);
                }
            }
        });
    }

    @Override
    public void onDisable() {
        GalacticProfile.unloadAll();
        GalacticProfile.saveAll();
        PersistentAlien.saveAll();
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
