package io.github.addoncommunity.galactifun.implementation.lists;

import io.github.addoncommunity.galactifun.implementation.machines.AssemblyTable;
import io.github.mooy1.infinitylib.PluginUtils;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;

@UtilityClass
public class RecipeTypes {

    public static final RecipeType ASSEMBLY_TABLE = new RecipeType(PluginUtils.getKey("assembly_table"), GalactifunItems.ASSEMBLY_TABLE, (stacks, stack) -> {
        SlimefunItemStack item = (SlimefunItemStack) stack;
        AssemblyTable.RECIPES.put(stacks, item);
        AssemblyTable.ITEMS.put(item.getItemId(), new Pair<>(item, stacks));
        AssemblyTable.IDS.add(item.getItemId());
    });
}
