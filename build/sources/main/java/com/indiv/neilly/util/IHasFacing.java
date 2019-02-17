package com.indiv.neilly.util;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHasFacing {
    void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state);

}
