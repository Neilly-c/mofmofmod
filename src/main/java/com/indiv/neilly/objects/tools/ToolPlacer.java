package com.indiv.neilly.objects.tools;

import com.indiv.neilly.Main;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToolPlacer extends Item implements IHasModel{

    public ToolPlacer(String name){
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.MOFTAB);
        this.maxStackSize = 1;

        ItemInit.ITEMS.add(this);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack1 = player.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack itemStack2 = player.getHeldItem(EnumHand.OFF_HAND);
        if(itemStack1.getItem() == this && itemStack2.getItem() instanceof ItemBlock){
            ItemBlock itemBlock = new ItemBlock(((ItemBlock) itemStack2.getItem()).getBlock());
            EnumFacing enumfacing = player.getHorizontalFacing();
            pos = pos.offset(facing);
            for(int a=0;a<8;a++) {
                if (!itemStack2.isEmpty() && player.canPlayerEdit(pos, facing, itemStack2) && worldIn.mayPlace(((ItemBlock) itemStack2.getItem()).getBlock() , pos, false, facing, null)) {
                    int i = itemStack2.getMetadata();
                    IBlockState iBlockState2 = ((ItemBlock) itemStack2.getItem()).getBlock().getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

                    if (itemBlock.placeBlockAt(itemStack2, player, worldIn, pos, facing, hitX, hitY, hitZ, iBlockState2)) {
                        iBlockState2 = worldIn.getBlockState(pos);
                        if(a==0) {
                            SoundType soundtype = iBlockState2.getBlock().getSoundType(iBlockState2, worldIn, pos, player);
                            worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                        }
                        if(!player.isCreative())
                            itemStack2.shrink(1);
                    }
                } else {
                    break;
                }
                pos = pos.offset(enumfacing);
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
