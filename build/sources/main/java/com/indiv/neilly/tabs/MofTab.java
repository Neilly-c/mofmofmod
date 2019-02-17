package com.indiv.neilly.tabs;

import com.indiv.neilly.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MofTab extends CreativeTabs{
    public MofTab(String label){
        super("MofTab");
        //this.setBackgroundImageName("mof.png");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemInit.NUI);
    }
}
