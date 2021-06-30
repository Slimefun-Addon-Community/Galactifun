package io.github.addoncommunity.galactifun.core.commands;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.items.ProtectingBlock;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.mooy1.infinitylib.commands.AbstractCommand;

public final class EffectsCommand extends AbstractCommand {

    public EffectsCommand() {
        super("effects", "Gets all the effects of the current block (taking in protections)", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player p)) return;

        PlanetaryWorld world = Galactifun.worldManager().getWorld(p.getWorld());
        if (world == null) {
            p.sendMessage(ChatColor.RED + "You must be in a Galactifun world to execute this command");
            return;
        }

        Map<AtmosphericEffect, Integer> effects = world.getAtmosphere().getEffects();
        for (Map.Entry<AtmosphericEffect, Integer> entry : effects.entrySet()) {
            int level = entry.getValue() - ProtectingBlock.getProtectionFor(p.getLocation(), entry.getKey());
            if (level > 0) {
                p.sendMessage(ChatColor.YELLOW + String.format("Effect: %s, Level: %d",
                        entry.getKey().toString(),
                        level
                ));
            }
        }
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> list) {
    }
}
