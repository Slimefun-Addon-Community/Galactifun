package io.github.addoncommunity.galactifun.implementation.lists;

import io.github.addoncommunity.galactifun.implementation.machines.AssemblyTable;
import io.github.mooy1.infinitylib.PluginUtils;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

@UtilityClass
public class RecipeTypes {

    public static final RecipeType ASSEMBLY_TABLE = new RecipeType(PluginUtils.getKey("assembly_table"), GalactifunItems.ASSEMBLY_TABLE, (stacks, stack) -> {
        SlimefunItemStack item = (SlimefunItemStack) stack;
        AssemblyTable.RECIPES.put(stacks, item);
        AssemblyTable.ITEMS.put(item.getItemId(), new Pair<>(item, stacks));
        AssemblyTable.IDS.add(item.getItemId());
    });

    // this is needed as using RecipeType.MOB_DROP looks up vanilla mobs
    public static final RecipeType CUSTOM_MOB_DROP = new RecipeType(PluginUtils.getKey("gf_mob_drop"), RecipeType.MOB_DROP.toItem());

    public static final RecipeType CIRCUIT_PRESS = new RecipeType(PluginUtils.getKey("circuit_press"), GalactifunItems.CIRCUIT_PRESS);

    public static final RecipeType PLANET = new RecipeType(PluginUtils.getKey("mars"), new CustomItem(
        Material.END_STONE,
        "&fNaturally Occurring",
        "",
        "&7Find this block on a planet"
    ));
}
