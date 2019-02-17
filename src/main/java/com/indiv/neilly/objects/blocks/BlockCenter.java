package com.indiv.neilly.objects.blocks;

import com.indiv.neilly.Main;
import com.indiv.neilly.entity.EntityWaterMill;
import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.BlockLever;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockCenter extends Block {

    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public BlockCenter(String name) {
        super(Material.WOOD);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.MOFTAB);
        setHardness(2.5F);
        setSoundType(SoundType.WOOD);
        setResistance(4.0F);

        BlockInit.BLOCKS.add(this);
        this.translucent = true;
        this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, false));
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue(IBlockState state){
        return 1.0F;
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {

    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POWERED, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(POWERED) ? 1 : 0;
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.scheduleUpdate(pos, this, 5);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        List entityMill = worldIn.getEntitiesWithinAABB(EntityWaterMill.class, new AxisAlignedBB(pos.getX() + 0.4D, pos.getY() + 0.4D, pos.getZ() + 0.4D, pos.getX() + 0.6D, pos.getY() + 0.6D, pos.getZ() + 0.6D));
        if(!entityMill.isEmpty() && ((EntityWaterMill)entityMill.get(0)).getRotationSpeed() != 0){
            state = state.withProperty(POWERED, true);
        }else{
            state = state.withProperty(POWERED, false);
        }
        worldIn.setBlockState(pos, state, 3);
        worldIn.notifyNeighborsOfStateChange(pos, this, false);
    }

    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return this.getWeakPower(blockState, blockAccess,pos, side);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        if (state.getValue(POWERED)) {
            worldIn.notifyNeighborsOfStateChange(pos, this, false);
        }
        List entityMill = worldIn.getEntitiesWithinAABB(EntityWaterMill.class, new AxisAlignedBB(pos.getX() + 0.4D, pos.getY() + 0.4D, pos.getZ() + 0.4D, pos.getX() + 0.6D, pos.getY() + 0.6D, pos.getZ() + 0.6D));
        ((EntityWaterMill)entityMill.get(0)).attackEntityFrom(DamageSource.OUT_OF_WORLD, 20.0F);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{POWERED});
    }
}
