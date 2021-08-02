package io.github.addoncommunity.galactifun;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.Bukkit;

import io.github.addoncommunity.galactifun.api.aliens.AlienManager;
import io.github.addoncommunity.galactifun.api.aliens.BossAlien;
import io.github.addoncommunity.galactifun.api.structures.StructureManager;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.ProtectionManager;
import io.github.addoncommunity.galactifun.api.worlds.WorldManager;
import io.github.addoncommunity.galactifun.base.BaseAlien;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.addoncommunity.galactifun.base.BaseUniverse;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.addoncommunity.galactifun.core.commands.AlienSpawnCommand;
import io.github.addoncommunity.galactifun.core.commands.EffectsCommand;
import io.github.addoncommunity.galactifun.core.commands.GalactiportCommand;
import io.github.addoncommunity.galactifun.core.commands.SealedCommand;
import io.github.addoncommunity.galactifun.core.commands.SphereCommand;
import io.github.addoncommunity.galactifun.core.commands.StructureCommand;
import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.commands.AbstractCommand;


public final class Galactifun extends AbstractAddon {

    @Getter
    private static Galactifun instance;

    private StructureManager structureManager;
    private AlienManager alienManager;
    private WorldManager worldManager;
    private ProtectionManager protectionManager;

    protected void enable() {
        instance = this;

        this.structureManager = new StructureManager(this);
        this.alienManager = new AlienManager(this);
        this.worldManager = new WorldManager(this);
        this.protectionManager = new ProtectionManager();

        BaseAlien.setup(this.alienManager);
        BaseUniverse.setup(this.worldManager);
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
        // todo make better
        BossAlien.removeBossBars();

        instance = null; // REMEMBER TO KEEP THIS LAST
    }

    @Override
    public void onLoad() {
        // default to not logging world settings
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
                new EffectsCommand()
        );
    }

    @Nonnull
    @Override
    public String getAutoUpdatePath() {
        return "auto-update";
    }

    public static StructureManager structureManager() {
        return instance.structureManager;
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
