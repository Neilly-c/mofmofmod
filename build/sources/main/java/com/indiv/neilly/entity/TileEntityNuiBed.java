package com.indiv.neilly.entity;

import com.indiv.neilly.objects.blocks.BlockNuiBed;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.block.BlockBed.PART;
import static net.minecraft.block.BlockHorizontal.FACING;

public class TileEntityNuiBed extends TileEntityBed implements ITickable{

    private EnumDyeColor color = EnumDyeColor.WHITE;

    public TileEntityNuiBed(){
        super();
    }

    @Override
    public void update() {
        if(!world.isRemote){
            EntityPlayer p = getPlayerInBed(world, pos);
            if(p != null) {
                switch (isNuiBed(world, pos)) {
                    case NUI_SWEET3:
                        p.getFoodStats().addStats(1, 1);
                    case NUI_SWEET2:
                        p.getFoodStats().addStats(1, 0);
                    case NUI_SWEET1:
                        p.setHealth(p.getHealth() + 0.5F);
                        break;
                    default:
                }
            }
        }
    }

    public void setItemValues(ItemStack p_193051_1_) {
        this.setColor(EnumDyeColor.byMetadata(p_193051_1_.getMetadata()));
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey("color")) {
            this.color = EnumDyeColor.byMetadata(compound.getInteger("color"));
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("color", this.color.getMetadata());
        return compound;
    }

    public EnumDyeColor getColor() {
        return this.color;
    }

    @Override
    public void setColor(EnumDyeColor color) {
        this.color = color;
        this.markDirty();
    }

    @Nullable
    private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos) {
        for (EntityPlayer entityplayer : worldIn.playerEntities) {
            if (entityplayer.isPlayerSleeping() && entityplayer.bedLocation.equals(pos)) {
                return entityplayer;
            }
        }
        return null;
    }

    private BlockNuiBed.EnumSleepWithNui.EnumType isNuiBed(World worldIn, BlockPos pos){
        List<EntityNui> nuinui =  worldIn.getEntitiesWithinAABB(EntityNui.class, this.getDectectionBox(worldIn, pos));
        if(nuinui.isEmpty()){
            return BlockNuiBed.EnumSleepWithNui.EnumType.NO_NUI;
        }else{
            int levelSweetDream = 0;
            for(EntityNui nui : nuinui){
                levelSweetDream = (nui.getSweetDream() > levelSweetDream) ? nui.getSweetDream() : levelSweetDream;
            }
            return BlockNuiBed.EnumSleepWithNui.EnumType.getTypeFromMeta(levelSweetDream);
        }
    }

    private AxisAlignedBB getDectectionBox(World worldIn, BlockPos pos1) {
        EnumFacing facing = worldIn.getBlockState(pos1).getValue(FACING);
        BlockPos pos2 = pos1.offset((worldIn.getBlockState(pos1).getValue(PART) == BlockBed.EnumPartType.FOOT) ? facing : facing.getOpposite());
        float x1 = Math.min(pos1.getX(), pos2.getX());
        float y1 = Math.min(pos1.getY(), pos2.getY());
        float z1 = Math.min(pos1.getZ(), pos2.getZ());
        float x2 = Math.max(pos1.getX(), pos2.getX());
        float y2 = Math.max(pos1.getY(), pos2.getY());
        float z2 = Math.max(pos1.getZ(), pos2.getZ());
        return new AxisAlignedBB((double)(x1 + 0.2F), (double)(y1 + 0.5F), (double)(z1 + 0.2F), (double)((x2 + 1) - 0.2F), (double)(y2 + 1), (double)(z2 + 1) - 0.2F);
    }

}
