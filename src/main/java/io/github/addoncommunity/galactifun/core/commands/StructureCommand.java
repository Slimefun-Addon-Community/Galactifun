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
import io.github.mooy1.infinitylib.commands.SubCommand;
import io.github.mooy1.infinitylib.common.PersistentType;

public final class StructureCommand extends SubCommand {

    private final Map<String, Structure> savedStructures = new HashMap<>();
    private final NamespacedKey pos1;
    private final NamespacedKey pos2;
    private final File saveFolder;

    public StructureCommand(Galactifun galactifun) {
        super("structure", "世界结构命令", true);

        this.saveFolder = new File(galactifun.getDataFolder(), "saved_structures");
        this.pos1 = Galactifun.createKey("pos1");
        this.pos2 = Galactifun.createKey("pos2");
    }

    @Override
    public void execute(@Nonnull CommandSender sender, String[] args) {
        if (args.length == 1 || !(sender instanceof Player p)) {
            return;
        }

        if (args[0].equals("save")) {
            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "用法: /galactifun save <名字>");
                return;
            }

            Location pos1 = p.getPersistentDataContainer().get(this.pos1, PersistentType.LOCATION);
            if (pos1 == null) {
                p.sendMessage(ChatColor.RED + "坐标点1没有设置!");
                return;
            }

            Location pos2 = p.getPersistentDataContainer().get(this.pos2, PersistentType.LOCATION);
            if (pos2 == null) {
                p.sendMessage(ChatColor.RED + "坐标点2没有设置!");
                return;
            }

            StructureRotation rotation = StructureRotation.fromFace(p.getFacing());
            Structure struct = Structure.create(rotation, pos1.getBlock(), pos2.getBlock());

            File file = new File(this.saveFolder, args[1] + ".gs");
            file.getParentFile().mkdirs();
            if (file.exists()) {
                try {
                    Files.writeString(file.toPath(), struct.saveToString(), Charsets.UTF_8);
                    this.savedStructures.put(args[1], struct);
                    p.sendMessage(ChatColor.GREEN + "已保存为 '" + args[1] + "'!");
                } catch (IOException e) {
                    e.printStackTrace();
                    p.sendMessage(ChatColor.RED + "文件保存错误! 让服主看控制台!");
                }
            }
            return;
        }

        Block target = p.getTargetBlockExact(32);
        if (target == null || target.getType().isAir()) {
            p.sendMessage(ChatColor.RED + "你必须选择一个方块!");
            return;
        }

        if (args[0].equals("pos1")) {
            p.getPersistentDataContainer().set(this.pos1, PersistentType.LOCATION, target.getLocation());
            p.sendMessage(ChatColor.GREEN + "设置坐标点1为 " + toString(target));
            return;
        }

        if (args[0].equals("pos2")) {
            p.getPersistentDataContainer().set(this.pos2, PersistentType.LOCATION, target.getLocation());
            p.sendMessage(ChatColor.GREEN + "设置坐标点2为 " + toString(target));
            return;
        }

        if (args[0].equals("paste")) {
            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "用法: /galactifun paste <名称>");
                return;
            }

            Structure saved = this.savedStructures.get(args[1]);

            if (saved == null) {
                saved = Structure.getByKey(args[1]);
                if (saved == null) {
                    p.sendMessage(ChatColor.RED + "未知结构 '" + args[1] + "'!");
                    return;
                }
            }

            saved.paste(target, StructureRotation.fromFace(p.getFacing()));
            p.sendMessage(ChatColor.GREEN + "已粘贴!");
        }
    }

    private static String toString(Block l) {
        return l.getX() + "x" + l.getY() + "y" + l.getZ() + "z in " + l.getWorld().getName();
    }

    @Override
    public void complete(@Nonnull CommandSender commandSender, String[] args, @Nonnull List<String> options) {
        if (args.length == 1) {
            options.addAll(Arrays.asList("pos1", "pos2", "save", "paste"));
        } else if (args.length == 2 && args[0].equals("paste")) {
            options.addAll(this.savedStructures.keySet());
            options.addAll(Structure.getLoadedKeys());
        }
    }

}
