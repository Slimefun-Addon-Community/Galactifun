package io.github.addoncommunity.galactifun.core.commands;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.mooy1.infinitylib.commands.AbstractCommand;

public final class ChunkVerCommand extends AbstractCommand {

    public ChunkVerCommand() {
        super("chunkver", "Gets the chunk version of the current chunk", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (!(sender instanceof Player p)) return;

        Chunk chunk = p.getChunk();
        if (Galactifun.worldManager().getWorld(chunk.getWorld()) == null) {
            p.sendMessage(ChatColor.RED + "You are not in a Galactifun planet!");
            return;
        }

        p.sendMessage(ChatColor.GOLD + "Current chunk version: " + chunk.getPersistentDataContainer()
                .getOrDefault(
                        AlienWorld.CHUNK_VER_KEY,
                        PersistentDataType.INTEGER,
                        1
                ));
    }

    @Override
    public void onTab(@Nonnull CommandSender sender, @Nonnull String[] args, @Nonnull List<String> tabs) {

    }

}
