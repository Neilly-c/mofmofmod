package com.indiv.neilly.entity;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class TileEntityWaterRemover extends TileEntity implements ITickable {

    EnumFacing[] facing = new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST};
    private List<BlockPos> list;
    private Queue<BlockPos> queue;
    private int delay;
    private boolean powered;
    private final Minecraft MINECRAFT = Minecraft.getMinecraft();

    @Override
    public void update() {
        if(powered) {
            if (list != null) {
                delay++;
                if (delay == 20) {
                    delay = 0;
                    if (runRemover(world))
                        MINECRAFT.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 0, 0.05D, 0);
                }
            }
        }else{
            delay = 0;
        }
    }

    public void setPowered(boolean powered){
        this.powered = powered;
        if(powered){
            define(world, pos);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.delay = compound.getInteger("delay");
        this.powered = compound.getBoolean("powered");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("delay", delay);
        compound.setBoolean("powered", powered);
        return compound;
    }

    public void define(World worldIn, BlockPos machine){
        this.delay = 0;
        list = Lists.newArrayList();
        queue = Lists.newLinkedList();
        BlockPos pos = machine.down();
        list.add(pos);
        queue.add(pos);
        while(!queue.isEmpty()) {
            pos = queue.poll();
            for (EnumFacing f : facing) {
                BlockPos pos1 = pos.offset(f);
                if(list.contains(pos1))
                    continue;
                if(!(worldIn.getBlockState(pos1).getBlock() == Blocks.WATER || worldIn.getBlockState(pos1).getMaterial() == Material.ICE || worldIn.getBlockState(pos1).getMaterial() == Material.AIR))
                    continue;
                if(Math.abs(pos1.getX() - machine.getX()) > 12 || Math.abs(pos1.getZ() - machine.getZ()) > 12)
                    continue;
                list.add(pos1);
                queue.add(pos1);
            }
        }
    }

    private boolean runRemover(World worldIn){
        if(list.isEmpty())
            return false;
        boolean flag = false;
        int dNum = 0, count = 0;
        do {
            for (int i=0,j=list.size();i<j;i++) {
                if(i >= list.size())
                    break;
                BlockPos pos = list.get(i);
                BlockPos pos1 = pos.down(dNum);
                if(worldIn.getBlockState(pos1).getMaterial() == Material.AIR){
                    continue;
                }
                if (!(worldIn.getBlockState(pos1).getBlock() == Blocks.WATER || worldIn.getBlockState(pos1).getMaterial() == Material.ICE)) {
                    list.remove(i);
                    continue;
                }
                flag = absorb(worldIn, pos1);
                count++;
                if(count >= 32)
                    return true;
            }
            if(list.isEmpty()){
                return false;
            }
            dNum++;
        }while (!flag);
        return true;
    }

    private boolean absorb(World worldIn, BlockPos pos){
        if(worldIn.getBlockState(pos).getBlock() == Blocks.ICE){
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            for (EnumFacing f : facing){
                if(!list.contains(pos.offset(f)) && worldIn.getBlockState(pos.offset(f)).getBlock() == Blocks.WATER){
                    worldIn.setBlockState(pos.offset(f), Blocks.ICE.getDefaultState(), 2);
                    MINECRAFT.world.spawnParticle(EnumParticleTypes.CLOUD, pos.offset(f).getX() + 0.5F, pos.getY(), pos.offset(f).getZ() + 0.5F, 0, 0.05D, 0);
                }
            }
            if(worldIn.getBlockState(pos.up()).getMaterial() == Material.WATER){
                worldIn.setBlockState(pos.up(), Blocks.ICE.getDefaultState(), 2);
            }
            MINECRAFT.world.spawnParticle(EnumParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 0, 0.05D, 0);
            return true;
        }else if(worldIn.getBlockState(pos).getBlock() == Blocks.WATER){
            worldIn.setBlockState(pos, Blocks.ICE.getDefaultState(), 2);
            MINECRAFT.world.spawnParticle(EnumParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 0, 0.05D, 0);
            return true;
        }
        return false;
    }
}
