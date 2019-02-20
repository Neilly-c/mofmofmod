package com.indiv.neilly.objects.items;

import com.indiv.neilly.Main;
import com.indiv.neilly.enchantment.*;
import com.indiv.neilly.entity.EntityNui;
import com.indiv.neilly.init.EnchantInit;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemNui extends Item implements IHasModel {
    public ItemNui(String name){
        setUnlocalizedName(name);
        setRegistryName(name);
        this.setCreativeTab(Main.MOFTAB);
        this.maxStackSize = 1;
        ItemInit.ITEMS.add(this);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote) {
            return EnumActionResult.SUCCESS;
        }else if(worldIn.getBlockState(pos).getMaterial() != Material.CLOTH && worldIn.getBlockState(pos).getMaterial() != Material.CARPET){
            return EnumActionResult.FAIL;
        }else{
            ItemStack itemstack = player.getHeldItem(hand);
            EntityNui entityNui = new EntityNui(worldIn);
            if(itemstack.isItemEnchanted()){
                int levelEnchantWander = EnchantmentHelper.getEnchantmentLevel(EnchantInit.ENCHANT_WANDER, itemstack);
                boolean isEnchantGlowing = EnchantmentHelper.getEnchantmentLevel(EnchantInit.ENCHANT_GLOWING, itemstack) == 1;
                int levelEnchantSweetDream = EnchantmentHelper.getEnchantmentLevel(EnchantInit.ENCHANT_SWEET_DREAM, itemstack);
                int levelEnchantHawkeye = EnchantmentHelper.getEnchantmentLevel(EnchantInit.ENCHANT_HAWKEYE, itemstack);
                int levelEnchantPower = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemstack);
                int levelEnchantLooting = EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, itemstack);
                int levelEnchantProtection = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, itemstack);
                int levelEnchantPunch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, itemstack);
                boolean isEnchantAbsolute = EnchantmentHelper.getEnchantmentLevel(EnchantInit.ENCHANT_ABSOLUTE, itemstack) == 1;
                entityNui.setEnchantmentWander(levelEnchantWander);
                entityNui.setEnchantmentGlowing(isEnchantGlowing? 0 : -1);
                entityNui.setEnchantmentSweetDream(levelEnchantSweetDream);
                entityNui.setEnchantmentHawkeye(levelEnchantHawkeye);
                entityNui.setEnchantmentPower(levelEnchantPower);
                entityNui.setEnchantmentLooting(levelEnchantLooting);
                entityNui.setEnchantmentProtection(levelEnchantProtection);
                entityNui.setEnchantmentPunch(levelEnchantPunch);
                entityNui.setEnchantmentAbsolute(isEnchantAbsolute);
            }
            entityNui.setPosition((double)((float)pos.getX() + hitX), (double)((float)pos.getY() + hitY), (double)((float)pos.getZ() + hitZ));
            worldIn.spawnEntity(entityNui);
            itemstack.shrink(1);
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment e) {
        return e instanceof EnchantWander
                || e instanceof EnchantGlowing
                || e instanceof EnchantSweetDream
                || e instanceof EnchantHawkeye
                || e == Enchantments.PUNCH
                || e == Enchantments.POWER
                || e == Enchantments.LOOTING
                || e instanceof EnchantAbsolute;
    }

    @Override
    public int getItemEnchantability() {
        return 15;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
