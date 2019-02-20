package com.indiv.neilly.entity;

import com.google.common.base.Predicate;
import com.indiv.neilly.init.BlockInit;
import com.indiv.neilly.init.EnchantInit;
import com.indiv.neilly.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityNui extends EntityAnimal {

    private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.<Integer>createKey(EntityNui.class, DataSerializers.VARINT);
    private static final DataParameter<BlockPos> NUILIGHT_LOCATION = EntityDataManager.<BlockPos>createKey(EntityNui.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Integer> LEVEL_ENCHANT_WANDER = EntityDataManager.<Integer>createKey(EntityNui.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> IS_ENCHANT_GLOWING = EntityDataManager.<Integer>createKey(EntityNui.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LEVEL_ENCHANT_SWEET_DREAM = EntityDataManager.<Integer>createKey(EntityNui.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LEVEL_ENCHANT_HAWKEYE = EntityDataManager.<Integer>createKey(EntityNui.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LEVEL_ENCHANT_POWER = EntityDataManager.<Integer>createKey(EntityNui.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LEVEL_ENCHANT_LOOTING = EntityDataManager.<Integer>createKey(EntityNui.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LEVEL_ENCHANT_PROTECTION = EntityDataManager.<Integer>createKey(EntityNui.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LEVEL_ENCHANT_PUNCH = EntityDataManager.<Integer>createKey(EntityNui.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_ENCHANT_ABSOLUTE = EntityDataManager.<Boolean>createKey(EntityNui.class, DataSerializers.BOOLEAN);
    private static final int ATTACK_DURATION = 6;
    private static float attackDamage = 5.0F;
    private int inLove;
    private EntityLivingBase targetedEntity;
    private static final IBlockState NUI_LIGHT = BlockInit.NUI_LIGHT.getDefaultState();
    private static final IBlockState NUI_BED = BlockInit.NUI_BED.getDefaultState();


    public EntityNui(World worldIn){
        super(worldIn);
        this.setSize(0.4F, 0.4F);
        this.inLove = 1;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    public void setEnchantmentWander(int level){
        this.dataManager.set(LEVEL_ENCHANT_WANDER, level);
    }

    public void setEnchantmentGlowing(int time){
        this.dataManager.set(IS_ENCHANT_GLOWING, time);
    }

    public void setEnchantmentSweetDream(int level){
        this.dataManager.set(LEVEL_ENCHANT_SWEET_DREAM, level);
    }

    public void setEnchantmentHawkeye(int level){
        this.dataManager.set(LEVEL_ENCHANT_HAWKEYE, level);
        try {
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D + 12.0 * this.getHawkeye());
        }catch (Exception e){

        }
    }

    public void setEnchantmentPower(int level){
        this.dataManager.set(LEVEL_ENCHANT_POWER, level);
        attackDamage = 5.0F + level * 2.5F;
    }

    public void setEnchantmentLooting(int level){
        this.dataManager.set(LEVEL_ENCHANT_LOOTING, level);
    }

    public void setEnchantmentProtection(int level){
        this.dataManager.set(LEVEL_ENCHANT_PROTECTION, level);
        try {
            this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D + 2.0 * this.getProtection());
        }catch (Exception e){

        }
    }

    public void setEnchantmentPunch(int level){
        this.dataManager.set(LEVEL_ENCHANT_PUNCH, level);
    }

    public void setEnchantmentAbsolute(boolean flag){
        this.dataManager.set(IS_ENCHANT_ABSOLUTE, flag);
    }

    public void setNuilightLocation(BlockPos pos){
        this.dataManager.set(NUILIGHT_LOCATION, pos);
    }

    public int getWander(){
        return this.dataManager.get(LEVEL_ENCHANT_WANDER);
    }

    public int getGlowing(){
        return this.dataManager.get(IS_ENCHANT_GLOWING);
    }

    public int getSweetDream(){
        return this.dataManager.get(LEVEL_ENCHANT_SWEET_DREAM);
    }

    public int getHawkeye(){
        return this.dataManager.get(LEVEL_ENCHANT_HAWKEYE);
    }

    public int getPower(){
        return this.dataManager.get(LEVEL_ENCHANT_POWER);
    }

    public int getLooting(){
        return this.dataManager.get(LEVEL_ENCHANT_LOOTING);
    }

    public int getProtection(){
        return this.dataManager.get(LEVEL_ENCHANT_PROTECTION);
    }

    public int getPunch(){
        return this.dataManager.get(LEVEL_ENCHANT_PUNCH);
    }

    public boolean getAbsolute(){
        return this.dataManager.get(IS_ENCHANT_ABSOLUTE);
    }

    public BlockPos getNuilightPosition(){
        return this.dataManager.get(NUILIGHT_LOCATION);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("levelEnchantWander", this.getWander());
        compound.setInteger("isEnchantGlowing", this.getGlowing());
        compound.setInteger("levelEnchantSweetDream", this.getSweetDream());
        compound.setInteger("levelEnchantHawkeye", this.getHawkeye());
        compound.setInteger("levelEnchantPower", this.getPower());
        compound.setInteger("levelEnchantLooting", this.getLooting());
        compound.setInteger("levelEnchantProtection", this.getProtection());
        compound.setInteger("levelEnchantPunch", this.getPunch());
        compound.setBoolean("isEnchantAbsolute", this.getAbsolute());
        BlockPos n = this.getNuilightPosition();
        compound.setIntArray("nuilightLocation", new int[]{n.getX(), n.getY(), n.getZ()});
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setEnchantmentWander(compound.getInteger("levelEnchantWander"));
        this.setEnchantmentGlowing(compound.getInteger("isEnchantGlowing"));
        this.setEnchantmentSweetDream(compound.getInteger("levelEnchantSweetDream"));
        this.setEnchantmentHawkeye(compound.getInteger("levelEnchantHawkeye"));
        this.setEnchantmentPower(compound.getInteger("levelEnchantPower"));
        this.setEnchantmentLooting(compound.getInteger("levelEnchantLooting"));
        this.setEnchantmentProtection(compound.getInteger("levelEnchantProtection"));
        this.setEnchantmentPunch(compound.getInteger("levelEnchantPunch"));
        this.setEnchantmentAbsolute(compound.getBoolean("isEnchantAbsolute"));
        int x = compound.getIntArray("nuilightLocation")[0];
        int y = compound.getIntArray("nuilightLocation")[1];
        int z = compound.getIntArray("nuilightLocation")[2];
        this.setNuilightLocation(new BlockPos(x, y, z));
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.BED;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.9D);
    }

    @Override
    public float getBrightness() {
        return this.getGlowing() >= 0 ? 1.0F : super.getBrightness();
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(LEVEL_ENCHANT_WANDER, 0);
        this.dataManager.register(IS_ENCHANT_GLOWING, -1);
        this.dataManager.register(LEVEL_ENCHANT_SWEET_DREAM, 0);
        this.dataManager.register(LEVEL_ENCHANT_HAWKEYE, 0);
        this.dataManager.register(LEVEL_ENCHANT_POWER, 0);
        this.dataManager.register(LEVEL_ENCHANT_LOOTING, 0);
        this.dataManager.register(LEVEL_ENCHANT_PROTECTION, 0);
        this.dataManager.register(LEVEL_ENCHANT_PUNCH, 0);
        this.dataManager.register(IS_ENCHANT_ABSOLUTE, false);
        this.dataManager.register(NUILIGHT_LOCATION, new BlockPos(0,0,0));
        this.dataManager.register(TARGET_ENTITY, 0);
    }

    @Override
    public float getEyeHeight() {
        return 0.3F;
    }

    protected void initEntityAI() {
        this.tasks.addTask(2, new AINuiBeam(this));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F, 1.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 10, false, false, new NuiBeamTargetSelector(this)));
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItemMainhand();
        if(itemstack.isEmpty() || itemstack.getItem() == ItemInit.NUI){
            nuiDrop();
            return true;
        }else if(itemstack.getItem() == Items.BED){
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
        }
        return false;
    }

    public void nuiDrop(){
        //ref from minecart
        this.setDead();
        if (this.world.getGameRules().getBoolean("doEntityDrops")) {
            ItemStack itemstack = new ItemStack(ItemInit.NUI, 1);
            if (this.hasCustomName()) {
                itemstack.setStackDisplayName(this.getCustomNameTag());
            }
            int w = this.getWander();
            boolean g = this.getGlowing() >= 0;
            int sd = this.getSweetDream();
            int h = this.getHawkeye();
            int p1 = this.getPower();
            int l = this.getLooting();
            int p2 = this.getProtection();
            int p3 = this.getPunch();
            boolean a = this.getAbsolute();
            if (w != 0) {
                itemstack.addEnchantment(EnchantInit.ENCHANT_WANDER, w);
            }
            if (g) {
                itemstack.addEnchantment(EnchantInit.ENCHANT_GLOWING, 1);
            }
            if (sd != 0) {
                itemstack.addEnchantment(EnchantInit.ENCHANT_SWEET_DREAM, sd);
            }
            if (h != 0) {
                itemstack.addEnchantment(EnchantInit.ENCHANT_HAWKEYE, h);
            }
            if (p1 != 0) {
                itemstack.addEnchantment(Enchantments.POWER, p1);
            }
            if (l != 0) {
                itemstack.addEnchantment(Enchantments.LOOTING, l);
            }
            if (p2 != 0) {
                itemstack.addEnchantment(Enchantments.PROTECTION, p2);
            }
            if (p3 != 0) {
                itemstack.addEnchantment(Enchantments.PUNCH, p3);
            }
            if(a){
                itemstack.addEnchantment(EnchantInit.ENCHANT_ABSOLUTE, 1);
            }

            this.entityDropItem(itemstack, 0.0F);
        }
    }

    @Override
    public void setDead() {
        super.setDead();
        BlockPos nuiLoc = this.getNuilightPosition();
        if(nuiLoc != null){
            if(world.getBlockState(nuiLoc).getBlock() instanceof BlockAir) {
                world.setBlockToAir(nuiLoc);
            }
        }
    }

    private boolean validFloor(BlockPos pos){
        if(world.getBlockState(pos.down()).getMaterial() == Material.CLOTH)
            return true;
        if(world.getBlockState(pos).getMaterial() == Material.CARPET)
            return true;
        if(world.getBlockState(pos.down()).getMaterial() == Material.AIR)
            return true;
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(isWet()){
            nuiDrop();
        }
        BlockPos pos = this.getPosition();
        if(getWander() <= 0) {
            if (!validFloor(pos)) {
                nuiDrop();
            }
        }
        if(getGlowing() != -1){
            this.setEnchantmentGlowing(getGlowing() + 1);
            if(getGlowing() > 20) {
                BlockPos nuiLoc = this.getNuilightPosition();
                if (pos != nuiLoc && world.getBlockState(pos).getBlock() instanceof BlockAir) {
                    if (world.getBlockState(nuiLoc).getBlock() instanceof BlockAir) {
                        world.setBlockToAir(nuiLoc);
                    }
                    world.setBlockState(pos, NUI_LIGHT);
                    this.setNuilightLocation(pos);
                }
                this.setEnchantmentGlowing(0);
            }
        }
    }

    public void onLivingUpdate() {
        if (this.world.isRemote) {
            if (this.hasTargetedEntity()) {

                EntityLivingBase entitylivingbase = this.getTargetedEntity();
                if(entitylivingbase != null && !entitylivingbase.isEntityAlive()){
                    setTargetedEntity(0);
                }else {
                    this.rotationYaw = this.rotationYawHead;

                    if (entitylivingbase != null) {
                        this.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);
                        this.getLookHelper().onUpdateLook();
                        double d0 = entitylivingbase.posX - this.posX;
                        double d1 = entitylivingbase.posY + (double) (entitylivingbase.height * 0.5F) - (this.posY + (double) this.getEyeHeight());
                        double d2 = entitylivingbase.posZ - this.posZ;
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 = d0 / d3;
                        d1 = d1 / d3;
                        d2 = d2 / d3;
                        double d4 = this.rand.nextDouble();

                        while (d4 < d3) {
                            d4 += 1.0D + this.rand.nextDouble() * 1.0D;
                            this.world.spawnParticle((attackDamage == 5.0F + getPower() * 2.5F ? EnumParticleTypes.VILLAGER_HAPPY : EnumParticleTypes.REDSTONE),
                                    this.posX + d0 * d4, this.posY + d1 * d4 + (double) this.getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }
        }
        super.onLivingUpdate();
    }

    private boolean hasTargetedEntity() {
        return this.dataManager.get(TARGET_ENTITY) != 0;
    }

    @Nullable
    public EntityLivingBase getTargetedEntity() {
        if (!this.hasTargetedEntity()) {
            return null;
        } else if (this.world.isRemote) {
            if (this.targetedEntity != null) {
                return this.targetedEntity;
            } else {
                Entity entity = this.world.getEntityByID(this.dataManager.get(TARGET_ENTITY));

                if (entity instanceof EntityLivingBase) {
                    this.targetedEntity = (EntityLivingBase)entity;
                    return this.targetedEntity;
                } else {
                    return null;
                }
            }
        } else {
            return this.getAttackTarget();
        }
    }

    private void setTargetedEntity(int entityId){
        this.dataManager.set(TARGET_ENTITY, entityId);
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);

        if (TARGET_ENTITY.equals(key)) {
            this.targetedEntity = null;
        }
    }

    static class AINuiBeam extends EntityAIBase{

        //ref from AIGuardianAttack

        private final EntityNui nui;
        private int tickCounter;
        private int powerCounter;
        private boolean isAbsolute;

        public AINuiBeam(EntityNui nui) {
            this.nui = nui;
            this.setMutexBits(3);
            this.powerCounter = 0;
            this.isAbsolute = nui.getAbsolute();
        }

        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.nui.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }

        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting();
        }

        public void startExecuting() {
            this.tickCounter = -4;
            this.nui.getNavigator().clearPath();
            this.nui.getLookHelper().setLookPositionWithEntity(this.nui.getAttackTarget(), 90.0F, 90.0F);
            this.nui.isAirBorne = true;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.nui.setTargetedEntity(0);
            this.nui.setAttackTarget(null);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            EntityLivingBase entitylivingbase = this.nui.getAttackTarget();
            this.nui.getNavigator().clearPath();
            this.nui.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);
            this.tickCounter++;

            if (this.tickCounter == 0) {
                this.nui.setTargetedEntity(this.nui.getAttackTarget().getEntityId());
                this.nui.world.setEntityState(this.nui, (byte)21);
            }
            else if (this.tickCounter >= ATTACK_DURATION) {
                if(isAbsolute) {
                    attackDamage = (powerCounter >= 10 ? (5.0F + (powerCounter - 10) * 1.0F) : 5.0F);
                    if (attackDamage > 500.0F) {
                        attackDamage = 500.0F;
                    }
                }
                entitylivingbase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.nui, this.nui), attackDamage);
                entitylivingbase.attackEntityFrom(DamageSource.causeIndirectDamage(this.nui, this.nui), attackDamage);
                powerCounter++;
                this.nui.setAttackTarget(null);
            }
            super.updateTask();

        }
    }

    static class NuiBeamTargetSelector implements Predicate<EntityLivingBase> {
        private final EntityNui parentEntity;

        public NuiBeamTargetSelector(EntityNui nui) {
            this.parentEntity = nui;
        }

        public boolean apply(@Nullable EntityLivingBase p) {
            return (p instanceof EntityMob);
        }
    }
}
