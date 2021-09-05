package io.github.addoncommunity.galactifun.core.commands;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.mooy1.infinitylib.commands.SubCommand;

public final class AlienSpawnCommand extends SubCommand {

    public AlienSpawnCommand() {
        super("spawn", "Spawns an alien", true);
    }

    @Override
    public void execute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player p) || strings.length != 1) {
            return;
        }

        Alien<?> alien = Galactifun.alienManager().getAlien(strings[0]);

        if (alien != null) {
            alien.spawn(p.getLocation(), p.getWorld());
        }
    }

    @Override
    public void complete(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> ids) {
        if (strings.length == 1) {
            for (Alien<?> alien : Galactifun.alienManager().aliens()) {
                ids.add(alien.id());
            }
        }
    }

}
