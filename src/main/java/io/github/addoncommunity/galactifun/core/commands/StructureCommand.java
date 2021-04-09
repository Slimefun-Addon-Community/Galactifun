package io.github.addoncommunity.galactifun.core.commands;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.structures.GalacticStructure;
import io.github.addoncommunity.galactifun.core.structures.StructureLoader;
import io.github.addoncommunity.galactifun.core.structures.StructureRotation;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import io.github.mooy1.infinitylib.persistence.PersistenceUtils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class StructureCommand extends AbstractCommand {

    private static final File USER_STRUCTURE_FOLDER = new File(Galactifun.inst().getDataFolder(), "structures");
    private static final NamespacedKey POS1 = Galactifun.inst().getKey("pos1");
    private static final NamespacedKey POS2 = Galactifun.inst().getKey("pos2");

    public StructureCommand() {
        super("structure", "The command for structures", true);
        
        // load user structures
        if (!USER_STRUCTURE_FOLDER.mkdir()) {
            for (File file : Objects.requireNonNull(USER_STRUCTURE_FOLDER.listFiles())) {
                StructureLoader.loadFromFilePath(file.getPath());
            }
        }
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
            
            Block pos1 = p.getPersistentDataContainer().get(StructureCommand.POS1, PersistenceUtils.BLOCK);
            if (pos1 == null) {
                p.sendMessage(ChatColor.RED + "pos1 not set!");
                return;
            }
            
            Block pos2 = p.getPersistentDataContainer().get(StructureCommand.POS2, PersistenceUtils.BLOCK);
            if (pos2 == null) {
                p.sendMessage(ChatColor.RED + "pos2 not set!");
                return;
            }

            double time = System.nanoTime();
            StructureLoader.save(StructureLoader.create(args[2], StructureRotation.fromFace(p.getFacing()), pos1, pos2), USER_STRUCTURE_FOLDER);
            p.sendMessage(ChatColor.GREEN + "Saved as '" + args[2] + "' in " + Util.timeSince(time));
            return;
        }
        
        Block target = p.getTargetBlockExact(32);
        if (target == null || target.getType().isAir()) {
            p.sendMessage(ChatColor.RED + "You must target a block!");
            return;
        }

        if (args[1].equals("pos1")) {
            p.getPersistentDataContainer().set(POS1, PersistenceUtils.BLOCK, target);
            p.sendMessage(ChatColor.GREEN + "Set pos1 to " + toString(target));
            return;
        }

        if (args[1].equals("pos2")) {
            p.getPersistentDataContainer().set(POS2, PersistenceUtils.BLOCK, target);
            p.sendMessage(ChatColor.GREEN + "Set pos2 to " + toString(target));
            return;
        }
        
        if (args[1].equals("paste")) {
            if (args.length != 3) {
                p.sendMessage(ChatColor.RED + "Usage: /galactifun paste <name>");
                return;
            }
            
            GalacticStructure loaded = StructureLoader.getFromPath(args[2]);

            if (loaded == null) {
                p.sendMessage(ChatColor.RED + "Unknown structure '" + args[2] + "'!");
                return;
            }

            double time = System.nanoTime();
            
            loaded.paste(target, StructureRotation.fromFace(p.getFacing()));
            
            p.sendMessage(ChatColor.GREEN + "Pasted in " + Util.timeSince(time));
        }
    }
    
    private static String toString(Block l) {
        return l.getX() + "x" + l.getY() + "y" + l.getZ() + "z in " + l.getWorld().getName();
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] args, @Nonnull List<String> options) {
        if (args.length == 2) {
            options.addAll(Arrays.asList("pos1", "pos2", "save", "paste"));
        } else if (args.length == 3 && args[1].equals("paste")) {
            options.addAll(StructureLoader.structurePaths());
        }
    }
    
}
