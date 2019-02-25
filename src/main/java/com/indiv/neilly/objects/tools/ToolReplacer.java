package com.indiv.neilly.objects.tools;

import com.indiv.neilly.Main;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToolReplacer extends Item implements IHasModel {
    public ToolReplacer(String name){
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.MOFTAB);
        this.setMaxStackSize(1);
        this.setMaxDamage(256);

        ItemInit.ITEMS.add(this);
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItemMain = player.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack heldItemOff = player.getHeldItem(EnumHand.OFF_HAND);
        if(heldItemMain.getItem() == this && heldItemOff.getItem() instanceof ItemBlock){
            ItemBlock itemBlock = new ItemBlock(((ItemBlock) heldItemOff.getItem()).getBlock());
            if (!heldItemOff.isEmpty() && player.canPlayerEdit(pos, facing, heldItemOff)) {
                int i = heldItemOff.getMetadata();
                IBlockState iBSToReplaced = worldIn.getBlockState(pos);
                IBlockState iBSHeld = ((ItemBlock) heldItemOff.getItem()).getBlock().getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);
                if(iBSToReplaced != iBSHeld) {

                    worldIn.destroyBlock(pos, !player.isCreative());

                    if (itemBlock.placeBlockAt(heldItemOff, player, worldIn, pos, facing, hitX, hitY, hitZ, iBSHeld)) {
                        if (!player.isCreative() && !worldIn.isRemote) {
                            heldItemOff.shrink(1);
                            heldItemMain.damageItem(1, player);
                        }
                    }
                }
            }

            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
