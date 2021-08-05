package io.github.addoncommunity.galactifun.core.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.codec.Charsets;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.structures.Structure;
import io.github.addoncommunity.galactifun.api.structures.StructureRotation;
import io.github.mooy1.infinitylib.commands.AbstractCommand;
import io.github.mooy1.infinitylib.persistence.PersistenceUtils;

public final class StructureCommand extends AbstractCommand {

    private final Map<String, Structure> savedStructures = new HashMap<>();
    private final NamespacedKey pos1;
    private final NamespacedKey pos2;
    private final File saveFolder;

    public StructureCommand(Galactifun galactifun) {
        super("structure", "The command for structures", true);

        this.saveFolder = new File(galactifun.getDataFolder(), "saved_structures");
        this.pos1 = galactifun.getKey("pos1");
        this.pos2 = galactifun.getKey("pos2");
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, String[] args) {
        if (args.length == 1 || !(sender instanceof Player p)) {
            return;
        }

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
            Structure struct = Structure.create(rotation, pos1.getBlock(), pos2.getBlock());

            File file = new File(this.saveFolder, this.name + ".gs");
            file.getParentFile().mkdirs();
            if (file.exists()) {
                try {
                    Files.writeString(file.toPath(), struct.saveToString(), Charsets.UTF_8);
                    this.savedStructures.put(this.name, struct);
                    p.sendMessage(ChatColor.GREEN + "Saved as '" + args[2] + "'!");
                } catch (IOException e) {
                    e.printStackTrace();
                    p.sendMessage(ChatColor.RED + "Error saving file! Check the console!");
                }
            }
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

            Structure saved = this.savedStructures.get(args[2]);

            if (saved == null) {
                saved = Structure.getByKey(args[2]);
                if (saved == null) {
                    p.sendMessage(ChatColor.RED + "Unknown structure '" + args[2] + "'!");
                    return;
                }
            }

            saved.paste(target, StructureRotation.fromFace(p.getFacing()));
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
            options.addAll(this.savedStructures.keySet());
            options.addAll(Structure.getLoadedKeys());
        }
    }

}
