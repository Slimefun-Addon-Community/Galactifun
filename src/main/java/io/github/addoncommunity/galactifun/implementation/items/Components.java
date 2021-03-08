package io.github.addoncommunity.galactifun.implementation.items;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.Heads;
import io.github.addoncommunity.galactifun.implementation.lists.RecipeTypes;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import lombok.AccessLevel;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@Getter
public enum Components {
    REINFORCED_CHANNEL("Reinforced Channel", new ItemChoice(Material.BAMBOO), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        SlimefunItems.REINFORCED_PLATE, null, SlimefunItems.REINFORCED_PLATE,
        SlimefunItems.REINFORCED_PLATE, null, SlimefunItems.REINFORCED_PLATE,
        SlimefunItems.REINFORCED_PLATE, null, SlimefunItems.REINFORCED_PLATE,
    }, 8),
    NOZZLE("Nozzle", new ItemChoice(Material.IRON_TRAPDOOR), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        SlimefunItems.STEEL_INGOT, null, SlimefunItems.STEEL_INGOT,
        SlimefunItems.STEEL_INGOT, null, SlimefunItems.STEEL_INGOT,
        null, new ItemStack(Material.IRON_TRAPDOOR), null
    }, 2),
    ROCKET_ENGINE("Rocket Engine", new ItemChoice(Material.FLINT_AND_STEEL), RecipeTypes.ASSEMBLY_TABLE, new ItemStack[]{
        null, REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), null,
        null, null, REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), null, null,
        null, null, NOZZLE.getItem(), NOZZLE.getItem(), null, null,
        null, SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.FLINT_AND_STEEL), new ItemStack(Material.FLINT_AND_STEEL), SlimefunItems.REINFORCED_PLATE, null,
        SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE,
        SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE,
    }),
    ADVANCED_PROCESSING_UNIT("&4Advanced Processing Unit", new ItemChoice(Heads.CORE.getTexture()), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        Circuits.REDSTONE.getItem(), Circuits.GLOWSTONE.getItem(), Circuits.REDSTONE.getItem(),
        Circuits.DIAMOND.getItem(), SlimefunItems.ADVANCED_CIRCUIT_BOARD, Circuits.DIAMOND.getItem(),
        Circuits.REDSTONE.getItem(), Circuits.LAPIS.getItem(), Circuits.REDSTONE.getItem()
    })
    ;
    private final String name;
    private final RecipeType recipeType;
    private final ItemStack[] recipe;
    private final SlimefunItemStack item;
    @Getter(AccessLevel.NONE)
    private final int amount;

    Components(@Nonnull String name, @Nonnull ItemChoice material, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe, int amount) {
        Validate.notNull(name);
        Validate.notNull(material);
        Validate.notNull(recipeType);
        Validate.notNull(recipe);

        // makes name white if no color codes
        if (name.contains("&")) {
            this.name = ChatColor.translateAlternateColorCodes('&', name);
        } else {
            this.name = ChatColor.translateAlternateColorCodes('&', "&f" + name);
        }

        this.recipeType = recipeType;
        this.recipe = recipe;
        this.item = new SlimefunItemStack(
            this.name(),
            material.getItem(),
            this.name,
            "",
            "&7A component in construction"
        );
        this.amount = amount;
    }

    Components(@Nonnull String name, @Nonnull ItemChoice material, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        this(name, material, recipeType, recipe, 1);
    }

    public static void setup(Galactifun addon) {
        for (Components component : Components.values()) {
            SlimefunItem item = new UnplaceableBlock(Categories.COMPONENTS, component.item, component.recipeType, component.recipe, new SlimefunItemStack(component.item, component.amount));
            item.register(addon);
            if (component.recipeType.equals(RecipeTypes.ASSEMBLY_TABLE)) {
                item.setHidden(true);
            }
        }
    }
}
