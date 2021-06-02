package io.github.addoncommunity.galactifun;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import lombok.Getter;

import io.github.addoncommunity.galactifun.api.aliens.AlienManager;
import io.github.addoncommunity.galactifun.api.aliens.BossAlien;
import io.github.addoncommunity.galactifun.api.universe.TheUniverse;
import io.github.addoncommunity.galactifun.api.worlds.WorldManager;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.addoncommunity.galactifun.core.GalacticExplorer;
import io.github.addoncommunity.galactifun.core.commands.AlienSpawnCommand;
import io.github.addoncommunity.galactifun.core.commands.GalactiportCommand;
import io.github.addoncommunity.galactifun.core.commands.SphereCommand;
import io.github.addoncommunity.galactifun.core.commands.StructureCommand;
import io.github.addoncommunity.galactifun.core.structures.StructureRegistry;
import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.commands.AbstractCommand;

@Getter
public final class Galactifun extends AbstractAddon {

    private static Galactifun instance;

    private AlienManager alienManager;
    private WorldManager worldManager;
    private TheUniverse theUniverse;
    private GalacticExplorer galacticExplorer;

    protected void enable() {
        instance = this;

        this.theUniverse = new TheUniverse();
        this.alienManager = new AlienManager(this);
        this.worldManager = new WorldManager(this, this.alienManager);
        this.galacticExplorer = new GalacticExplorer(this.theUniverse, this.worldManager);

        StructureRegistry.loadStructureFolder(this);
        BaseRegistry.setup();
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
    protected List<AbstractCommand> setupSubCommands() {
        return Arrays.asList(
                new GalactiportCommand(),
                new AlienSpawnCommand(this.alienManager),
                new SphereCommand(),
                new StructureCommand(this)
        );
    }

    @Nonnull
    @Override
    public String getAutoUpdatePath() {
        return "auto-update";
    }

    public static Galactifun inst() {
        return instance;
    }

}
