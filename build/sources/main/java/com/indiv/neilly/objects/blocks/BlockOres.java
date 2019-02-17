package com.indiv.neilly.objects.blocks;

import com.indiv.neilly.Main;
import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.util.IHasModel;
import com.indiv.neilly.util.IMetaName;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class BlockOres extends BlockOre implements IHasModel, IMetaName {

    public BlockOres(String name){
        super();
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.MOFTAB);

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return super.getItemDropped(state, rand, fortune);
    }

    @Override
    public void registerModels(){
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return null;
    }
}
