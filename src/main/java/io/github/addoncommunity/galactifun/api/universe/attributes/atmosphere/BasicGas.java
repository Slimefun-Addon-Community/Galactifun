package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.addoncommunity.galactifun.base.items.DiamondAnvil;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.generators.CombustionGenerator;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.Freezer;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;

public class BasicGas {
    public final static Gas OXYGEN = new Gas("OXYGEN", "氧气", "5b3ad76aadb80ecf4b4cdbe76b8704b0f2dc090b49b65c36d87ed879f1065ef2");
    public final static Gas NITROGEN = new Gas("NITROGEN", "氮气", "5b3ad76aadb80ecf4b4cdbe76b8704b0f2dc090b49b65c36d87ed879f1065ef2");
    public final static Gas CARBON_DIOXIDE = new Gas("CARBON_DIOXIDE", "二氧化碳", "5b3ad76aadb80ecf4b4cdbe76b8704b0f2dc090b49b65c36d87ed879f1065ef2");
    public final static Gas WATER = new Gas("WATER", "水蒸气", "5b3ad76aadb80ecf4b4cdbe76b8704b0f2dc090b49b65c36d87ed879f1065ef2");
    public final static Gas HELIUM = new Gas("HELIUM", "氦气", "93dfa904fe3d0306666a573c22eec1dd0a8051e32a38ea2d19c4b5867e232a49");
    public final static Gas ARGON = new Gas("ARGON", "氩气", "ea005531b6167a86fb09d6c0f3db60f2650162d0656c2908d07b377111d8f2a2");
    public final static Gas METHANE = new Gas("METHANE", "甲烷", "ea005531b6167a86fb09d6c0f3db60f2650162d0656c2908d07b377111d8f2a2");
    public final static Gas HYDROCARBONS = new Gas("HYDROCARBONS", "碳氢化合物", "725691372e0734bfb57bb03690490661a83f053a3488860df3436ce1caa24d11");
    public final static Gas HYDROGEN = new Gas("HYDROGEN", "氢气", "725691372e0734bfb57bb03690490661a83f053a3488860df3436ce1caa24d11");
    public final static Gas SULFUR = new Gas("SULFUR", "硫", "c7a1ece691ad28d17bbbcecb22270c85e1c9581485806264c676de67c272e2d0");
    public final static Gas AMMONIA = new Gas("AMMONIA", "氨气", "c7a1ece691ad28d17bbbcecb22270c85e1c9581485806264c676de67c272e2d0");
    public final static Gas OTHER = new Gas("OTHER", "其他气体");

    static {
        if (SlimefunItems.FREEZER_2.getItem() instanceof Freezer freezer) {
            freezer.registerRecipe(10, BasicGas.NITROGEN.item(), SlimefunItems.REACTOR_COOLANT_CELL.asQuantity(4));
        }
        if (SlimefunItems.COMBUSTION_REACTOR.getItem() instanceof CombustionGenerator generator) {
            generator.registerFuel(new MachineFuel(15, BasicGas.HYDROGEN.item()));
            generator.registerFuel(new MachineFuel(20, BasicGas.SULFUR.item()));
            generator.registerFuel(new MachineFuel(30, BasicGas.HYDROCARBONS.item()));
            generator.registerFuel(new MachineFuel(70, BasicGas.AMMONIA.item()));
            generator.registerFuel(new MachineFuel(200, BasicGas.METHANE.item()));
        }
        if (BaseItems.DIAMOND_ANVIL.getItem() instanceof DiamondAnvil anvil) {
            anvil.registerRecipe(10, BasicGas.HYDROGEN.item().asQuantity(4), BasicGas.HELIUM.item());
            anvil.registerRecipe(10, BasicGas.HELIUM.item().asQuantity(4), BaseMats.FUSION_PELLET);
        }
    }

}
