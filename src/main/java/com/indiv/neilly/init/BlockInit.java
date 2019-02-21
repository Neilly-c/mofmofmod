package com.indiv.neilly.init;

import com.indiv.neilly.objects.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block BLOCK_COPPER = new BlockBase("block_copper", Material.IRON);
    public static final Block BLOCK_ALUMINIUM = new BlockBase("block_aluminium", Material.IRON);
    public static final Block BLOCK_TIN = new BlockBase("block_tin", Material.IRON);
    public static final Block BLOCK_LEAD = new BlockBase("block_lead", Material.IRON);
    public static final Block BLOCK_SILVER = new BlockBase("block_silver", Material.IRON);
    public static final Block BLOCK_IRIDIUM = new BlockBase("block_iridium", Material.IRON);
    public static final Block BLOCK_PLATINUM = new BlockBase("block_platinum", Material.IRON);
    public static final Block BLOCK_BRONZE = new BlockBase("block_bronze", Material.IRON);
    public static final Block BLOCK_STEEL = new BlockBase("block_steel", Material.IRON);

    public static final Block ORE_COPPER = new BlockOres("ore_copper");
    public static final Block ORE_ALUMINIUM = new BlockOres("ore_aluminium");
    public static final Block ORE_TIN = new BlockOres("ore_tin");
    public static final Block ORE_LEAD = new BlockOres("ore_lead");
    public static final Block ORE_SILVER = new BlockOres("ore_silver");
    public static final Block ORE_IRIDIUM = new BlockOres("ore_iridium");
    public static final Block ORE_PLATINUM = new BlockOres("ore_platinum");


    public static final Block PLANKS = new BlockPlank("planks");
    public static final Block LOGS = new BlockLogs("log");
    public static final Block LEAVES = new BlockLeaf("leaves");
    public static final Block SAPLINGS = new BlockSaplings("sapling");

    public static final Block NUI_LIGHT = new BlockNuiLight("nui_light");
    public static final Block NUI_BED = new BlockNuiBed("nui_bed");

    public static final Block MILL_CENTER = new BlockCenter("mill_center");
    public static final Block WATER_REMOVER = new BlockWaterRemover("water_remover");
    public static final Block MULTI_FURNACE = new BlockMultiFurnace("multi_furnace", false);
    public static final Block LIT_MULTI_FURNACE = new BlockMultiFurnace("lit_multi_furnace", true);

    public static void initTools(){

    }

}
