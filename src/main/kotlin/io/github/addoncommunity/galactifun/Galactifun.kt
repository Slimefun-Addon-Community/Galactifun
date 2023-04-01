package io.github.addoncommunity.galactifun

import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class Galactifun : AbstractAddon() {

    companion object;

    override fun onLoad() {
        Bukkit.spigot().config["world-settings.default.verbose"] = false
    }

    override fun onEnable() {
        pluginInstance = this

        var shouldDisable = false
        if (!PaperLib.isPaper()){
            log(Level.SEVERE, "Galactifun only supports Paper and its forks (i.e. Airplane and Purpur)")
            log(Level.SEVERE, "Please use Paper or a fork of Paper")
            shouldDisable = true
        }
        if (Slimefun.getMinecraftVersion().isBefore(MinecraftVersion.MINECRAFT_1_18)){
            log(Level.SEVERE, "Galactifun only supports Minecraft 1.18 and above")
            log(Level.SEVERE, "Please use Minecraft 1.18 or above")
            shouldDisable = true
        }
        if (Bukkit.getPluginManager().isPluginEnabled("ClayTech")){
            log(Level.SEVERE, "Galactifun will not work properly with ClayTech")
            log(Level.SEVERE, "Please disable ClayTech")
            shouldDisable = true
        }

        if (shouldDisable){
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        Metrics(this, 11613)

        Galactifun.runTask {
            log(
                Level.INFO,
                """################# Galactifun $pluginVersion #################
                    
                    Galactifun is open source, you can contribute or report bugs at $bugTrackerURL
                    Join the Slimefun Addon Community Discord: discord.gg/SqD3gg5SAU
                    
                    ###################################################""".trimIndent()
            )
        }
    }

    override fun getJavaPlugin(): JavaPlugin = this

    override fun getBugTrackerURL(): String = "https://github.com/Slimefun-Addon-Community/Galactifun/issues"
}

lateinit var pluginInstance: Galactifun
    private set

fun Galactifun.Companion.log(level: Level, message: String) {
    pluginInstance.logger.log(level, message)
}

fun Galactifun.Companion.runTask(runnable: Runnable) {
    pluginInstance.server.scheduler.runTask(pluginInstance, runnable)
}