package com.indiv.neilly.objects.blocks;

import com.indiv.neilly.Main;
import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNuiLight extends BlockAir {
    public BlockNuiLight(String name) {
        super();
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.MOFTAB);
        setLightLevel(1.0F);

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

}
