package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.api.mobs.Mob;
import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.core.GalacticListener;
import io.github.addoncommunity.galactifun.core.GalacticCategory;
import io.github.addoncommunity.galactifun.core.GalacticTicker;
import io.github.addoncommunity.galactifun.core.MobManager;
import io.github.addoncommunity.galactifun.core.commands.GalactiportCommand;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.command.CommandManager;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
                new GalactiportCommand()
        );
        
        GalacticListener.setup();

        GalacticCategory.setup(this);
        
        BaseRegistry.setup();
        
        PluginUtils.scheduleRepeatingSync(new GalacticTicker(), 10, GalacticTicker.INTERVAL);

        setupTicker();
        
    }

    private void setupTicker() { // TODO move this to its own runnable class...
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {

            for (Mob mob : MobManager.INSTANCE.getRegistry().values()) {
                mob.onUniqueTick();
            }

            for (World world : Bukkit.getWorlds()) {
                for (Entity e : world.getEntities()) {
                    if (e instanceof LivingEntity) {
                        String id = PersistentDataAPI.getString(e, MobManager.MOB_ID);
                        Mob mobx = MobManager.INSTANCE.getById(id);
                        if (id != null && mobx != null) {
                            mobx.onMobTick((LivingEntity) e);
                        }
                    }
                }
            }

        }, 1, 1);
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
