package com.indiv.neilly.objects.items;

import com.indiv.neilly.Main;
import com.indiv.neilly.entity.EntityWaterMill;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.util.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemMill extends Item implements IHasModel {
    private final Class <? extends Entity > hangingEntityClass;

    public ItemMill(Class <? extends Entity> entityClass, String name){
        this.hangingEntityClass = entityClass;
        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Main.MOFTAB);
        this.maxStackSize = 16;
        ItemInit.ITEMS.add(this);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote){
            return EnumActionResult.SUCCESS;
        }
        ItemStack itemstack = player.getHeldItem(hand);
        BlockPos blockpos = pos.offset(facing);
        if(facing != EnumFacing.DOWN && facing != EnumFacing.UP && player.canPlayerEdit(blockpos, facing, itemstack)){
            EntityWaterMill waterMill = this.createEntity(worldIn, blockpos, facing);
            if (waterMill != null) {
                worldIn.spawnEntity(waterMill);
                itemstack.shrink(1);
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    private EntityWaterMill createEntity(World worldIn, BlockPos blockpos, EnumFacing facing) {
        double d0 = (double)blockpos.getX() + 0.5D;
        double d1 = (double)blockpos.getY();
        double d2 = (double)blockpos.getZ() + 0.5D;
        EntityWaterMill waterMill = new EntityWaterMill(worldIn, d0, d1, d2, facing);
        waterMill.setLocationAndAngles(d0, d1, d2, (facing.getHorizontalIndex() * 90.0F), 0);
        return waterMill;
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
