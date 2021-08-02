package io.github.addoncommunity.galactifun.core.commands;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.structures.GalacticStructure;
import io.github.addoncommunity.galactifun.api.structures.StructureRotation;
import io.github.addoncommunity.galactifun.core.managers.StructureManager;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import io.github.mooy1.infinitylib.persistence.PersistenceUtils;

public final class StructureCommand extends AbstractCommand {

    private final NamespacedKey pos1;
    private final NamespacedKey pos2;

    public StructureCommand(Galactifun galactifun) {
        super("structure", "The command for structures", true);

        this.pos1 = galactifun.getKey("pos1");
        this.pos2 = galactifun.getKey("pos2");
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, String[] args) {
        if (args.length == 1 || !(sender instanceof Player p)) {
            return;
        }

        StructureManager manager = Galactifun.structureManager();

        if (args[1].equals("save")) {
            if (args.length != 3) {
                p.sendMessage(ChatColor.RED + "Usage: /galactifun save <name>");
                return;
            }

            Location pos1 = p.getPersistentDataContainer().get(this.pos1, PersistenceUtils.LOCATION);
            if (pos1 == null) {
                p.sendMessage(ChatColor.RED + "pos1 not set!");
                return;
            }

            Location pos2 = p.getPersistentDataContainer().get(this.pos2, PersistenceUtils.LOCATION);
            if (pos2 == null) {
                p.sendMessage(ChatColor.RED + "pos2 not set!");
                return;
            }

            StructureRotation rotation = StructureRotation.fromFace(p.getFacing());
            GalacticStructure struct = manager.createStructure(rotation, pos1.getBlock(), pos2.getBlock());
            manager.saveStructure(args[2], struct);
            p.sendMessage(ChatColor.GREEN + "Saved as '" + args[2] + "'!");
            return;
        }

        Block target = p.getTargetBlockExact(32);
        if (target == null || target.getType().isAir()) {
            p.sendMessage(ChatColor.RED + "You must tar a block!");
            return;
        }

        if (args[1].equals("pos1")) {
            p.getPersistentDataContainer().set(this.pos1, PersistenceUtils.LOCATION, target.getLocation());
            p.sendMessage(ChatColor.GREEN + "Set pos1 to " + toString(target));
            return;
        }

        if (args[1].equals("pos2")) {
            p.getPersistentDataContainer().set(this.pos2, PersistenceUtils.LOCATION, target.getLocation());
            p.sendMessage(ChatColor.GREEN + "Set pos2 to " + toString(target));
            return;
        }

        if (args[1].equals("paste")) {
            if (args.length != 3) {
                p.sendMessage(ChatColor.RED + "Usage: /galactifun paste <name>");
                return;
            }

            GalacticStructure loaded = manager.getSaved(args[2]);

            if (loaded == null) {
                p.sendMessage(ChatColor.RED + "Unknown structure '" + args[2] + "'!");
                return;
            }

            loaded.paste(target, StructureRotation.fromFace(p.getFacing()));
            p.sendMessage(ChatColor.GREEN + "Pasted!");
        }
    }

    private static String toString(Block l) {
        return l.getX() + "x" + l.getY() + "y" + l.getZ() + "z in " + l.getWorld().getName();
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, String[] args, @Nonnull List<String> options) {
        if (args.length == 2) {
            options.addAll(Arrays.asList("pos1", "pos2", "save", "paste"));
        } else if (args.length == 3 && args[1].equals("paste")) {
            options.addAll(Galactifun.structureManager().getNames());
        }
    }

}
