package com.indiv.neilly.objects.blocks;

import com.indiv.neilly.entity.EntityNui;
import com.indiv.neilly.entity.TileEntityNuiBed;
import com.indiv.neilly.init.BlockInit;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

public class BlockNuiBed extends BlockBed {
    public BlockNuiBed(String name){
        super();
        setUnlocalizedName(name);
        setRegistryName("minecraft:bed");

        BlockInit.BLOCKS.add(this);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if (isNuiBed(worldIn, pos) != EnumSleepWithNui.EnumType.NO_NUI) {
                playerIn.sendStatusMessage(new TextComponentTranslation("tile.NuiBed.notification", new Object[0]), true);
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    private EnumSleepWithNui.EnumType isNuiBed(World worldIn, BlockPos pos){
        List<EntityNui> nuinui =  worldIn.getEntitiesWithinAABB(EntityNui.class, this.getDectectionBox(worldIn, pos));
        if(nuinui.isEmpty()){
            return EnumSleepWithNui.EnumType.NO_NUI;
        }else{
            int levelSweetDream = 0;
            for(EntityNui nui : nuinui){
                levelSweetDream = (nui.getSweetDream() > levelSweetDream) ? nui.getSweetDream() : levelSweetDream;
            }
            return EnumSleepWithNui.EnumType.getTypeFromMeta(levelSweetDream);
        }
    }

    private AxisAlignedBB getDectectionBox(World worldIn, BlockPos pos1) {
        EnumFacing facing = worldIn.getBlockState(pos1).getValue(FACING);
        BlockPos pos2 = pos1.offset((worldIn.getBlockState(pos1).getValue(PART) == EnumPartType.FOOT) ? facing : facing.getOpposite());
        float x1 = Math.min(pos1.getX(), pos2.getX());
        float y1 = Math.min(pos1.getY(), pos2.getY());
        float z1 = Math.min(pos1.getZ(), pos2.getZ());
        float x2 = Math.max(pos1.getX(), pos2.getX());
        float y2 = Math.max(pos1.getY(), pos2.getY());
        float z2 = Math.max(pos1.getZ(), pos2.getZ());
        return new AxisAlignedBB((double)(x1 + 0.2F), (double)(y1 + 0.5F), (double)(z1 + 0.2F), (double)((x2 + 1) - 0.2F), (double)(y2 + 1), (double)(z2 + 1) - 0.2F);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityNuiBed();
    }

    public static class EnumSleepWithNui{
        public enum EnumType{
            NO_NUI(-1),
            NUI(0),
            NUI_SWEET1(1),
            NUI_SWEET2(2),
            NUI_SWEET3(3);

            private final int meta;

            EnumType(int meta){
                this.meta = meta;
            }

            public static int getMeta(EnumType e){
                return e.meta;
            }

            public static EnumType getTypeFromMeta(int m){
                for(EnumType e : values()){
                    if(e.meta == m){
                        return e;
                    }
                }
                return NUI;
            }
        }
    }
}
