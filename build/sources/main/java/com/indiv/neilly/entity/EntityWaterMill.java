package com.indiv.neilly.entity;

import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.init.ItemInit;
import com.indiv.neilly.objects.blocks.BlockCenter;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityWaterMill extends Entity {

    private static final DataParameter<Integer> FACING = EntityDataManager.createKey(EntityWaterMill.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ROTATION_SPEED = EntityDataManager.createKey(EntityWaterMill.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ROTATE_VALUE = EntityDataManager.createKey(EntityWaterMill.class, DataSerializers.VARINT);

    public EntityWaterMill(World worldIn) {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(1.0F, 1.0F);
        IBlockState center = BlockInit.MILL_CENTER.getDefaultState();
        worldIn.setBlockState(this.getPosition(), center);
        worldIn.scheduleUpdate(this.getPosition(), center.getBlock(), 5);
        this.setAlwaysRenderNameTag(false);
    }

    public EntityWaterMill(World worldIn, double posX, double posY, double posZ, EnumFacing facing) {
        this(worldIn);
        this.setPosition(posX, posY, posZ);
        this.setFacing(facing);
        IBlockState center = BlockInit.MILL_CENTER.getDefaultState();
        worldIn.setBlockState(this.getPosition(), center);
        worldIn.scheduleUpdate(this.getPosition(), center.getBlock(), 5);
        this.setAlwaysRenderNameTag(false);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(FACING, 0);
        this.dataManager.register(ROTATION_SPEED, 0);
        this.dataManager.register(ROTATE_VALUE, 0);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (!this.world.isRemote) {
            if(!isValidAttach(this.getPosition(), getFacing())){
                if(isValidAttach(this.getPosition(), getFacing().getOpposite())){
                    setFacing(getFacing().getOpposite());
                }else{
                    this.setDead();
                    if (this.world.getGameRules().getBoolean("doEntityDrops")) {
                        ItemStack itemstack = new ItemStack(ItemInit.WATER_MILL, 1);
                        this.entityDropItem(itemstack, 0);
                    }
                }
            }
            BlockPos[] surrounding = new BlockPos[8];
            int[] sWater = new int[8];
            surrounding[0] = this.getPosition().down();
            surrounding[1] = surrounding[0].offset(getFacing().rotateYCCW());
            surrounding[2] = surrounding[0].offset(getFacing().rotateYCCW().getOpposite());
            surrounding[3] = this.getPosition().up();
            surrounding[4] = this.getPosition().offset(getFacing().rotateYCCW());
            surrounding[5] = this.getPosition().offset(getFacing().rotateYCCW().getOpposite());
            surrounding[6] = surrounding[4].up();
            surrounding[7] = surrounding[5].up();
            int i=0;
            for(BlockPos pos : surrounding){
                IBlockState sBlock = world.getBlockState(pos);
                if(sBlock.getBlock() instanceof BlockLiquid){
                    sWater[i] = sBlock.getValue(BlockLiquid.LEVEL);
                    if(sWater[i] >= 8)
                        sWater[i] = 0;
                }else{
                    sWater[i] = 16;
                }
                i++;
            }
            int rotateV = 0;
            if(sWater[0] != 16 && sWater[1] != 16 && sWater[0] - sWater[1] != 0){
                rotateV += (sWater[0] - sWater[1] > 0) ? 2 : -2;
            }
            if(sWater[0] != 16 && sWater[2] != 16 && sWater[0] - sWater[2] != 0){
                rotateV += (sWater[0] - sWater[2] > 0) ? -2 : 2;
            }
            if(sWater[3] != 16 && sWater[6] != 16 && sWater[3] - sWater[6] != 0){
                rotateV += (sWater[3] - sWater[6] > 0) ? 2 : -2;
            }
            if(sWater[3] != 16 && sWater[7] != 16 && sWater[3] - sWater[7] != 0){
                rotateV += (sWater[3] - sWater[7] > 0) ? -2 : 2;
            }
            if(sWater[1] != 16 && sWater[4] != 16){
                rotateV -= 5;
            }
            if(sWater[6] != 16 && sWater[4] != 16){
                rotateV -= 5;
            }
            if(sWater[2] != 16 && sWater[5] != 16){
                rotateV += 5;
            }
            if(sWater[7] != 16 && sWater[5] != 16){
                rotateV += 5;
            }
            if(world.isRainingAt(surrounding[6])){
                rotateV ++;
            }
            if(world.isRainingAt(surrounding[7])){
                rotateV --;
            }
            if(rotateV != 0){
                if(getRotationSpeed() == 0){
                    world.scheduleUpdate(this.getPosition(), world.getBlockState(this.getPosition()).getBlock(), 5);
                }
                setRotationSpeed(rotateV);
            }else{
                if(getRotationSpeed() != 0){
                    world.scheduleUpdate(this.getPosition(), world.getBlockState(this.getPosition()).getBlock(), 5);
                }
                setRotationSpeed(0);
            }
        }
        this.addRotateValue(getRotationSpeed());
    }

    private boolean isValidAttach(BlockPos pos, EnumFacing facing){
        BlockPos attachedPos = pos.offset(facing.getOpposite());
        IBlockState attachedBlockState = world.getBlockState(attachedPos);
        return (!attachedBlockState.getBlock().isAir(attachedBlockState, world, attachedPos) && attachedBlockState.getBlockFaceShape(world, attachedPos, facing) == BlockFaceShape.SOLID);
    }

    public void setFacing(EnumFacing facing) {
        this.dataManager.set(FACING, facing.getHorizontalIndex());
    }

    public void setRotationSpeed(int speed){
        this.dataManager.set(ROTATION_SPEED, speed);
    }

    public void setRotateValue(int value){
        this.dataManager.set(ROTATE_VALUE, value);
    }

    public EnumFacing getFacing() {
        return EnumFacing.getHorizontal(this.dataManager.get(FACING));
    }

    public int getRotationSpeed(){
        return this.dataManager.get(ROTATION_SPEED);
    }

    public int getRotateValue(){
        return this.dataManager.get(ROTATE_VALUE);
    }

    private void addRotateValue(int i){
        this.dataManager.set(ROTATE_VALUE, MathHelper.wrapDegrees(this.dataManager.get(ROTATE_VALUE) + i));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setDouble("x", posX);
        compound.setDouble("y", posY);
        compound.setDouble("z", posZ);
        compound.setInteger("facing", getFacing().getHorizontalIndex());
        compound.setInteger("rotation_speed", getRotationSpeed());
        compound.setInteger("rotate_value", getRotateValue());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.setPosition(compound.getDouble("x"), compound.getDouble("y"), compound.getDouble("z"));
        setFacing(EnumFacing.getHorizontal(compound.getInteger("facing")));
        setRotationSpeed(compound.getInteger("rotation_speed"));
        setRotateValue(compound.getInteger("rotate_value"));
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return null;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {

    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.world.isRemote && !this.isDead) {
            if (this.isEntityInvulnerable(source)) {
                return false;
            }else{
                this.setDead();
                if (this.world.getGameRules().getBoolean("doEntityDrops")) {
                    ItemStack itemstack = new ItemStack(ItemInit.WATER_MILL, 1);
                    this.entityDropItem(itemstack, 0);
                }
                return true;
            }
        }else{
            return false;
        }
    }

    @Override
    public void setDead() {
        super.setDead();
        world.setBlockState(this.getPosition(), Blocks.AIR.getDefaultState());
    }

    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    public void setAlwaysRenderNameTag(boolean alwaysRenderNameTag) {
        super.setAlwaysRenderNameTag(alwaysRenderNameTag);
    }
}
