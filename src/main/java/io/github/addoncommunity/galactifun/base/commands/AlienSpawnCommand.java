package io.github.addoncommunity.galactifun.base.commands;

import io.github.addoncommunity.galactifun.api.universe.world.Alien;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public final class AlienSpawnCommand extends AbstractCommand {

    public AlienSpawnCommand() {
        super("spawn", "spawns an alien", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player) || strings.length != 2) {
            return;
        }

        Player p = (Player) commandSender;
        Alien alien = Alien.getByID(strings[1]);

        if (alien != null) {
            alien.spawn(p.getLocation(), p.getWorld());
        }
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> ids) {
        if (strings.length == 2) {
            ids.addAll(Alien.ALIENS.keySet());
        }
    }
    
}
