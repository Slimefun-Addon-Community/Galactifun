package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.addoncommunity.galactifun.api.universe.world.BossAlien;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.addoncommunity.galactifun.core.commands.AlienSpawnCommand;
import io.github.addoncommunity.galactifun.core.commands.GalactiportCommand;
import io.github.addoncommunity.galactifun.core.commands.GenSphereCommand;
import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public final class Galactifun extends AbstractAddon {

    private static Galactifun instance;
    
    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        
        BaseRegistry.setup();
        CoreCategory.setup(this);
        BaseMats.setup();
        BaseItems.setup(this);
        
        // log after startup
        runSync(() -> log(
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
    protected int getMetricsID() {
        return 10411;
    }

    @Override
    protected String getGithubPath() {
        return "Slimefun-Addon-Community/Galactifun/master";
    }

    @Override
    protected List<AbstractCommand> getSubCommands() {
        return Arrays.asList(new GalactiportCommand(), new AlienSpawnCommand(), new GenSphereCommand());
    }

    @Override
    public void onDisable() {
        // todo make better
        BossAlien.removeBossBars();
    }

    @Override
    public void onLoad() {
        // default to not logging world settings
        Bukkit.spigot().getConfig().set("world-settings.default.verbose", false);
    }

    public static Galactifun inst() {
        return instance;
    }

}
