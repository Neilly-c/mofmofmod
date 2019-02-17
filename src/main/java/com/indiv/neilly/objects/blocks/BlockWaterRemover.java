package com.indiv.neilly.objects.blocks;

import com.google.common.collect.Lists;
import com.indiv.neilly.Main;
import com.indiv.neilly.entity.TileEntityWaterRemover;
import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockWaterRemover extends BlockMachine implements ITileEntityProvider {

    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public BlockWaterRemover(String name) {
        super(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, false));
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlockInit.WATER_REMOVER);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(!worldIn.isRemote) {
            boolean flag = state.getValue(POWERED);
            boolean flag1 = worldIn.isBlockPowered(pos);
            if (flag1 != flag) {
                worldIn.setBlockState(pos, state.withProperty(POWERED, flag1), 3);
                TileEntity tileentity = worldIn.getTileEntity(pos);
                if (tileentity instanceof TileEntityWaterRemover){
                    ((TileEntityWaterRemover) tileentity).setPowered(flag1);
                }
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityWaterRemover();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POWERED, meta > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(POWERED)? 1 : 0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {POWERED});
    }
}
