package io.github.addoncommunity.galactifun.core.commands;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.mooy1.infinitylib.commands.AbstractCommand;

public final class AlienSpawnCommand extends AbstractCommand {

    public AlienSpawnCommand() {
        super("spawn", "Spawns an alien", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player p) || strings.length != 2) {
            return;
        }

        Alien<?> alien = Galactifun.alienManager().getAlien(strings[1]);

        if (alien != null) {
            alien.spawn(p.getLocation(), p.getWorld());
        }
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> ids) {
        if (strings.length == 2) {
            for (Alien<?> alien : Galactifun.alienManager().aliens()) {
                ids.add(alien.id());
            }
        }
    }

}
