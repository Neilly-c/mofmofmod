package com.indiv.neilly.objects.blocks;

import com.indiv.neilly.Main;
import com.indiv.neilly.entity.TileEntityMultiFurnace;
import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.util.IHasFacing;
import com.indiv.neilly.util.Reference;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockMultiFurnace extends BlockMachine implements ITileEntityProvider, IHasFacing {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    private final boolean isBurning;
    private static boolean keepInventory;

    public BlockMultiFurnace(String name, boolean isBurning) {
        super(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
        this.isBurning = isBurning;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlockInit.MULTI_FURNACE);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(BlockInit.MULTI_FURNACE);
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    @Override
    public void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState ibs = worldIn.getBlockState(pos.north());
            IBlockState ibs1 = worldIn.getBlockState(pos.south());
            IBlockState ibs2 = worldIn.getBlockState(pos.west());
            IBlockState ibs3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && ibs.isFullBlock() && !ibs1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && ibs1.isFullBlock() && !ibs.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && ibs2.isFullBlock() && !ibs3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && ibs3.isFullBlock() && !ibs2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        else {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMultiFurnace) {
                playerIn.openGui(Main.instance, Reference.GUI_MULTI_FURNACE, worldIn, pos.getX(), pos.getY(), pos.getZ());
                playerIn.addStat(StatList.FURNACE_INTERACTION);
            }
            return true;
        }
    }

    public static void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;
        if (active) {
            worldIn.setBlockState(pos, BlockInit.LIT_MULTI_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(POWERED, true), 3);
            worldIn.setBlockState(pos, BlockInit.LIT_MULTI_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(POWERED, true), 3);
        } else {
            worldIn.setBlockState(pos, BlockInit.MULTI_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(POWERED, false), 3);
            worldIn.setBlockState(pos, BlockInit.MULTI_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(POWERED, false), 3);
        }
        keepInventory = false;
        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(FACING)).getIndex();
    }

    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, POWERED});
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMultiFurnace();
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }


    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityMultiFurnace) {
                ((TileEntityMultiFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if(!keepInventory) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityMultiFurnace) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMultiFurnace) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}
