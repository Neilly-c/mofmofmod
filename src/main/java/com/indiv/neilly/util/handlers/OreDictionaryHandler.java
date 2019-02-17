package com.indiv.neilly.util.handlers;

import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.objects.blocks.BlockOres;
import net.minecraft.block.Block;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryHandler {
    public static void registerOreDictionary() {

        OreDictionary.registerOre("oreCopper", BlockInit.ORE_COPPER.getDefaultState().getBlock());
        OreDictionary.registerOre("oreTin", BlockInit.ORE_TIN.getDefaultState().getBlock());
        OreDictionary.registerOre("oreAluminum", BlockInit.ORE_ALUMINIUM.getDefaultState().getBlock());
        OreDictionary.registerOre("oreSilver", BlockInit.ORE_SILVER.getDefaultState().getBlock());
        OreDictionary.registerOre("oreLead", BlockInit.ORE_LEAD.getDefaultState().getBlock());
        OreDictionary.registerOre("oreIridium", BlockInit.ORE_IRIDIUM.getDefaultState().getBlock());
        OreDictionary.registerOre("orePlatinum", BlockInit.ORE_PLATINUM.getDefaultState().getBlock());
    }
}
