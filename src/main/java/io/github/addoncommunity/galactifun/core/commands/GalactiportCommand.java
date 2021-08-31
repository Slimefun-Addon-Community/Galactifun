package io.github.addoncommunity.galactifun.core.commands;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.base.items.knowledge.KnowledgeLevel;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;

/**
 * Command to teleport to world spawns
 *
 * @author Seggan
 * @author Mooy1
 */
public final class GalactiportCommand extends AbstractCommand {

    public GalactiportCommand() {
        super("world", "Teleports you to the spawn of the specified world", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player p) || strings.length != 2) {
            return;
        }

        World world = Bukkit.getWorld(strings[1]);

        if (world == null) {
            p.sendMessage(ChatColor.RED + "Invalid World!");
            return;
        }

        PaperLib.teleportAsync(p, world.getSpawnLocation());

        PlanetaryWorld planetaryWorld = Galactifun.worldManager().getWorld(world);
        if (planetaryWorld != null && KnowledgeLevel.get(p, planetaryWorld) == KnowledgeLevel.NONE) {
            KnowledgeLevel.BASIC.set(p, planetaryWorld);
        }
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> worlds) {
        if (strings.length == 2) {
            for (World world : Bukkit.getWorlds()) {
                worlds.add(world.getName());
            }
        }
    }

}
