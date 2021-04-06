package io.github.addoncommunity.galactifun.core.commands;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.structures.GalacticStructure;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import io.github.mooy1.infinitylib.persistence.PersistenceUtils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public final class StructureCommand extends AbstractCommand {

    private final NamespacedKey pos1;
    private final NamespacedKey pos2;

    public StructureCommand(Galactifun galactifun) {
        super("structure", "The command for structures", true);
        this.pos1 = galactifun.getKey("pos1");
        this.pos2 = galactifun.getKey("pos2");
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (args.length == 1 || !(sender instanceof Player)) {
            return;
        }

        Player p = (Player) sender;

        if (args[1].equals("save")) {
            if (args.length != 3) {
                p.sendMessage(ChatColor.RED + "Usage: /galactifun save <name>");
                return;
            }
            
            Block pos1 = p.getPersistentDataContainer().get(this.pos1, PersistenceUtils.BLOCK);
            if (pos1 == null) {
                p.sendMessage(ChatColor.RED + "pos1 not set!");
                return;
            }
            
            Block pos2 = p.getPersistentDataContainer().get(this.pos2, PersistenceUtils.BLOCK);
            if (pos2 == null) {
                p.sendMessage(ChatColor.RED + "pos2 not set!");
                return;
            }

            GalacticStructure.createStructure(args[2], pos1, pos2);
            p.sendMessage(ChatColor.GREEN + "Saved '" + args[2] + "'");
            return;
        }
        
        Block target = p.getTargetBlockExact(32);
        if (target == null || target.getType().isAir()) {
            p.sendMessage(ChatColor.RED + "You must target a block!");
            return;
        }

        if (args[1].equals("pos1")) {
            p.getPersistentDataContainer().set(this.pos1, PersistenceUtils.BLOCK, target);
            p.sendMessage(ChatColor.GREEN + "Set pos1 to " + toString(target));
            return;
        }

        if (args[1].equals("pos2")) {
            p.getPersistentDataContainer().set(this.pos2, PersistenceUtils.BLOCK, target);
            p.sendMessage(ChatColor.GREEN + "Set pos2 to " + toString(target));
            return;
        }
        
        if (args[1].equals("load")) {
            if (args.length != 3) {
                p.sendMessage(ChatColor.RED + "Usage: /galactifun load <name>");
                return;
            }
            
            GalacticStructure loaded = GalacticStructure.STRUCTURES.get(args[2]);

            if (loaded == null) {
                loaded = GalacticStructure.DEFAULT_STRUCTURES.get(args[2]);
                
                if (loaded == null) {
                    p.sendMessage(ChatColor.RED + "Unknown structure " + args[2]);
                    return;
                }
            }

            double time = System.nanoTime();
            
            loaded.paste(target);
            
            p.sendMessage(ChatColor.GREEN + "Pasted in " + Util.timeSince(time));
        }
    }
    
    private static String toString(Block l) {
        return l.getX() + "x" + l.getY() + "y" + l.getZ() + "z in " + l.getWorld().getName();
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] args, @Nonnull List<String> options) {
        if (args.length == 2) {
            options.addAll(Arrays.asList("pos1", "pos2", "save", "load"));
        } else if (args.length == 3 && args[1].equals("load")) {
            options.addAll(GalacticStructure.STRUCTURES.keySet());
            options.addAll(GalacticStructure.DEFAULT_STRUCTURES.keySet());
        }
    }
    
}
