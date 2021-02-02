package io.github.addoncommunity.galactifun.core.commands;

import io.github.addoncommunity.galactifun.api.mob.AbstractAlien;
import io.github.addoncommunity.galactifun.core.GalacticRegistry;
import io.github.mooy1.infinitylib.command.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AlienSpawnCommand extends AbstractCommand {

    public AlienSpawnCommand() {
        super("spawn", "spawns a custom mob", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player p = (Player) commandSender;
        AbstractAlien alien = GalacticRegistry.getAlien(strings[1]);

        if (alien != null) {
            alien.spawn(p.getLocation());
        }
    }

    @Nonnull
    @Override
    public List<String> onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        List<String> ids = new ArrayList<>();
        if (strings.length == 2) {
            ids.addAll(GalacticRegistry.ALIENS.keySet());
        }
        return ids;
    }
}
