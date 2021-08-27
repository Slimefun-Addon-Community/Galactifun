package io.github.addoncommunity.galactifun;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.Bukkit;

import io.github.addoncommunity.galactifun.base.BaseAlien;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.addoncommunity.galactifun.base.BaseUniverse;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.addoncommunity.galactifun.core.commands.AlienSpawnCommand;
import io.github.addoncommunity.galactifun.core.commands.ChunkverCommand;
import io.github.addoncommunity.galactifun.core.commands.EffectsCommand;
import io.github.addoncommunity.galactifun.core.commands.GalactiportCommand;
import io.github.addoncommunity.galactifun.core.commands.SealedCommand;
import io.github.addoncommunity.galactifun.core.commands.SphereCommand;
import io.github.addoncommunity.galactifun.core.commands.StructureCommand;
import io.github.addoncommunity.galactifun.core.managers.AlienManager;
import io.github.addoncommunity.galactifun.core.managers.ProtectionManager;
import io.github.addoncommunity.galactifun.core.managers.WorldManager;
import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.commands.AbstractCommand;


public final class Galactifun extends AbstractAddon {

    @Getter
    private static Galactifun instance;

    private AlienManager alienManager;
    private WorldManager worldManager;
    private ProtectionManager protectionManager;

    protected void enable() {
        instance = this;

        this.alienManager = new AlienManager(this);
        this.worldManager = new WorldManager(this);
        this.protectionManager = new ProtectionManager();

        BaseAlien.setup(this.alienManager);
        BaseUniverse.setup(this);
        CoreCategory.setup(this);
        BaseMats.setup();
        BaseItems.setup(this);

        // log after startup
        runSync(() -> log(
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
        this.alienManager.onDisable();

        // Do this last
        instance = null;
    }

    @Override
    public void onLoad() {
        // Default to not logging world settings
        Bukkit.spigot().getConfig().set("world-settings.default.verbose", false);
    }

    @Override
    protected Metrics setupMetrics() {
        return new Metrics(this, 11613);
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Slimefun-Addon-Community/Galactifun/master";
    }

    @Override
    protected List<AbstractCommand> setupSubCommands() {
        return Arrays.asList(
                new GalactiportCommand(),
                new AlienSpawnCommand(),
                new SphereCommand(),
                new StructureCommand(this),
                new SealedCommand(),
                new EffectsCommand(),
                new ChunkverCommand()
        );
    }

    @Nonnull
    @Override
    public String getAutoUpdatePath() {
        return "auto-update";
    }

    public static AlienManager alienManager() {
        return instance.alienManager;
    }

    public static WorldManager worldManager() {
        return instance.worldManager;
    }

    public static ProtectionManager protectionManager() {
        return instance.protectionManager;
    }

}
