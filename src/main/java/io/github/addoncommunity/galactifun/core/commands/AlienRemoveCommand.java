package io.github.addoncommunity.galactifun.core.commands;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.mooy1.infinitylib.commands.SubCommand;

public class AlienRemoveCommand extends SubCommand {
    public AlienRemoveCommand() {
        super("remove", "Removes an alien based on uuid", true);
    }

    @Override
    public void execute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player p) || strings.length != 1) {
            return;
        }

        if (strings[0].equalsIgnoreCase("all")) {
            for (UUID uuid : new HashSet<>(Galactifun.alienManager().alienIds())) {
                Entity entity = Bukkit.getEntity(uuid);
                if (entity != null && Galactifun.alienManager().getAlien(entity) != null) {
                    entity.remove();
                }
            }
            return;
        }

        try {
            UUID uuid = UUID.fromString(strings[0]);
            Entity entity = Bukkit.getEntity(uuid);

            if (entity == null || Galactifun.alienManager().getAlien(entity) == null) {
                throw new IllegalArgumentException();
            }

            entity.remove();
        } catch (IllegalArgumentException ignored) {
            p.sendMessage(ChatColor.RED + "Invalid UUID!");
        }
    }

    @Override
    public void complete(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> ids) {
        if (strings.length == 1) {
            ids.add("all");
            for (UUID uuid : new HashSet<>(Galactifun.alienManager().alienIds())) {
                ids.add(uuid.toString());
            }
        }
    }

}
