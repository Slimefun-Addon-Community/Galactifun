package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.alien.PersistentDataHoldingAlien;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.core.CoreCategories;
import io.github.addoncommunity.galactifun.core.commands.AlienSpawnCommand;
import io.github.addoncommunity.galactifun.core.commands.GalactiportCommand;
import io.github.addoncommunity.galactifun.core.commands.GenSphereCommand;
import io.github.addoncommunity.galactifun.core.listener.AlienListener;
import io.github.addoncommunity.galactifun.core.listener.CelestialListener;
import io.github.addoncommunity.galactifun.core.profile.GalacticProfile;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.command.CommandManager;
import io.github.mooy1.infinitylib.config.ConfigUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.UUID;

public class Galactifun extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static Galactifun instance;
    
    @Override
    public void onEnable() {
        instance = this;

        PluginUtils.setup("galactifun", this, "Slimefun-Addon-Community/Galactifun/master", getFile());

        CommandManager.setup("galactifun", "galactifun.admin", "/gf, /galactic",
                new GalactiportCommand(), new AlienSpawnCommand(), new GenSphereCommand()
        );
        
        GalacticProfile.loadAll();
        
        CoreCategories.setup(this);
        
        BaseRegistry.setup();
        
        registerListeners();
        
        scheduleTasks();
        
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
                "Join the Slimefun Addon Community Discord: Discord.gg/V2cJR9ADFU",
                "",
                "###################################################",
                ""
        ));
        
        PluginUtils.runSync(() -> GalacticProfile.get(UUID.fromString("0629ebca-3a33-4a4d-bd29-fafe4aa32719")), 100);

        for (World world : this.getServer().getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                Alien alien = Alien.getByEntity(entity);
                if (alien instanceof PersistentDataHoldingAlien) {
                    ((PersistentDataHoldingAlien) alien).load(entity);
                }
            }
        }
    }
    
    private static void registerListeners() {
        new CelestialListener();
        new AlienListener();
    }
    
    private static void scheduleTasks() {
        PluginUtils.scheduleRepeatingSync(AlienWorld::tickWorlds, 100);
        PluginUtils.scheduleRepeatingSync(AlienWorld::tickAliens, ConfigUtils.getInt("aliens.tick-interval", 1, 20, 4));
        PluginUtils.scheduleRepeatingSync(GalacticProfile::saveAll, 12000);
    }

    @Override
    public void onDisable() {
        GalacticProfile.unloadAll();
        GalacticProfile.saveAll();

        PluginUtils.log("Saving entity data...");
        for (World world : this.getServer().getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                Alien alien = Alien.getByEntity(entity);
                if (alien instanceof PersistentDataHoldingAlien) {
                    ((PersistentDataHoldingAlien) alien).save(entity);
                }
            }
        }

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
