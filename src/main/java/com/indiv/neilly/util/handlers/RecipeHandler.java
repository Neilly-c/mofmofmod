package com.indiv.neilly.util.handlers;

import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.init.ItemInit;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeHandler {
    public static void registerSmelting(){
        GameRegistry.addSmelting(BlockInit.ORE_COPPER, new ItemStack(ItemInit.INGOT_COPPER), 0.7f);
        GameRegistry.addSmelting(BlockInit.ORE_TIN, new ItemStack(ItemInit.INGOT_TIN), 0.7f);
        GameRegistry.addSmelting(BlockInit.ORE_ALUMINIUM, new ItemStack(ItemInit.INGOT_ALUMINIUM), 0.7f);
        GameRegistry.addSmelting(BlockInit.ORE_LEAD, new ItemStack(ItemInit.INGOT_LEAD), 0.7f);
        GameRegistry.addSmelting(BlockInit.ORE_SILVER, new ItemStack(ItemInit.INGOT_SILVER), 0.9f);
        GameRegistry.addSmelting(BlockInit.ORE_PLATINUM, new ItemStack(ItemInit.INGOT_PLATINUM), 1.2f);
        GameRegistry.addSmelting(BlockInit.ORE_IRIDIUM, new ItemStack(ItemInit.INGOT_IRIDIUM), 2.0f);
        GameRegistry.addSmelting(ItemInit.DUST_COPPER, new ItemStack(ItemInit.INGOT_COPPER), 0);
        GameRegistry.addSmelting(ItemInit.DUST_TIN, new ItemStack(ItemInit.INGOT_TIN), 0);
        GameRegistry.addSmelting(ItemInit.DUST_ALUMINIUM, new ItemStack(ItemInit.INGOT_ALUMINIUM), 0);
        GameRegistry.addSmelting(ItemInit.DUST_LEAD, new ItemStack(ItemInit.INGOT_LEAD), 0);
        GameRegistry.addSmelting(ItemInit.DUST_SILVER, new ItemStack(ItemInit.INGOT_SILVER), 0);
        GameRegistry.addSmelting(ItemInit.DUST_PLATINUM, new ItemStack(ItemInit.INGOT_PLATINUM), 0);
        GameRegistry.addSmelting(ItemInit.DUST_IRIDIUM, new ItemStack(ItemInit.INGOT_IRIDIUM), 0);
    }
}
