package com.indiv.neilly.objects.tools;

import com.indiv.neilly.Main;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;

public class ToolHoe extends ItemHoe implements IHasModel {
    public ToolHoe(String name, ToolMaterial material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.MOFTAB);

        ItemInit.ITEMS.add(this);

    }

    @Override
    public void registerModels(){
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
