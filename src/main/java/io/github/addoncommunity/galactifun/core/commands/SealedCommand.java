package io.github.addoncommunity.galactifun.core.commands;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;

public final class SealedCommand extends AbstractCommand {

    public SealedCommand() {
        super("sealed", "Checks if the area is sealed", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player p)) return;
        if (strings.length != 2) return;

        double time = System.nanoTime();
        Optional<Set<BlockPosition>> filled = Util.floodFill(p.getLocation(), Integer.parseInt(strings[1]));
        if (filled.isPresent()) {
            p.sendMessage("Sealed");
        } else {
            p.sendMessage("Not sealed");
        }

        p.sendMessage(Util.timeSince(time));
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> list) {
    }
}
