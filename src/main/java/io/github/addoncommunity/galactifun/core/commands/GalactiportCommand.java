package io.github.addoncommunity.galactifun.core.commands;

import io.github.mooy1.infinitylib.command.AbstractCommand;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Command to teleport to world spawns
 * 
 * @author Seggan
 * @author Mooy1
 */
public final class GalactiportCommand extends AbstractCommand {

    public GalactiportCommand() {
        super("world", "teleports you to the spawn of the specified world", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player) || strings.length != 2) {
            return;
        }
        
        Player p = (Player) commandSender;

        World world = Bukkit.getWorld(strings[1]);
        
        if (world == null) {
            p.sendMessage(ChatColor.RED + "Invalid World!");
            return;
        }
        
        PaperLib.teleportAsync(p, world.getSpawnLocation());
    }

    @Nonnull
    @Override
    public List<String> onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        List<String> worlds = new ArrayList<>();
        if (strings.length == 2) {
            for (World world : Bukkit.getWorlds()) {
                worlds.add(world.getName());
            }
        }
        return worlds;
    }

}
