package io.github.addoncommunity.galactifun;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.bukkit.Bukkit;

import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.addoncommunity.galactifun.api.universe.world.BossAlien;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.addoncommunity.galactifun.core.commands.AlienSpawnCommand;
import io.github.addoncommunity.galactifun.core.commands.GalactiportCommand;
import io.github.addoncommunity.galactifun.core.commands.SphereCommand;
import io.github.addoncommunity.galactifun.core.commands.StructureCommand;
import io.github.addoncommunity.galactifun.core.structures.StructureRegistry;
import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.commands.AbstractCommand;

public final class Galactifun extends AbstractAddon {

    private static Galactifun instance;
    
    @Override
    public void onEnable() {
        instance = this;
        
        super.onEnable();

        StructureRegistry.loadStructureFolder(this);
        BaseRegistry.setup();
        CoreCategory.setup(this);
        BaseMats.setup();
        BaseItems.setup(this);

        // log after startup
        runSync(() -> log(
                "################# Galactifun " + getPluginVersion() + " #################",
                "",
                "Loaded " + AlienWorld.getEnabled().size() + " worlds: ",
                AlienWorld.getEnabled().toString(),
                "",
                "Galactifun is open source, you can contribute or report bugs at: ",
                getBugTrackerURL(),
                "Join the Slimefun Addon Community Discord: discord.gg/SqD3gg5SAU",
                "",
                "###################################################"
        ));
    }

    @Override
    protected Metrics setupMetrics() {
        return new Metrics(this, 10411);
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Slimefun-Addon-Community/Galactifun/master";
    }

    @Override
    protected List<AbstractCommand> getSubCommands() {
        return Arrays.asList(new GalactiportCommand(), new AlienSpawnCommand(), new SphereCommand(), new StructureCommand());
    }

    @Override
    public void onDisable() {
        // todo make better
        BossAlien.removeBossBars();
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onLoad() {
        // default to not logging world settings because its annoying with lots of worlds
        Bukkit.spigot().getConfig().set("world-settings.default.verbose", false);
    }

    public static Galactifun inst() {
        return instance;
    }

}
