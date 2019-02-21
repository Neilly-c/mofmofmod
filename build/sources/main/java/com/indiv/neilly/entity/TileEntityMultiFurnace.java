package com.indiv.neilly.entity;

import com.indiv.neilly.inventory.ContainerMultiFurnace;
import com.indiv.neilly.objects.blocks.BlockMultiFurnace;
import com.indiv.neilly.objects.items.recipe.CrushRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime;
import static net.minecraft.tileentity.TileEntityFurnace.isItemFuel;

public class TileEntityMultiFurnace extends TileEntityLockable implements ITickable, ISidedInventory {
    private static final int[] SLOTS_TOP = new int[] {0, 1};
    private static final int[] SLOTS_BOTTOM = new int[] {1, 2};
    private static final int[] SLOTS_SIDES = new int[] {3};
    private NonNullList<ItemStack> furnaceItemStacks = NonNullList.withSize(4, ItemStack.EMPTY);
    private int furnaceBurnTime, currentItemBurnTime, cookTime, totalCookTime, pulverizerActiveTime, currentItemActiveTime, crushTime, totalCrushTime;
    private String furnaceCustomName;

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.furnaceItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
        this.furnaceBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(1));
        this.pulverizerActiveTime = compound.getInteger("ActiveTime");
        this.crushTime = compound.getInteger("CrushTime");
        this.totalCrushTime = compound.getInteger("CrushTimeTotal");
        this.currentItemActiveTime = getItemActiveTime(this.furnaceItemStacks.get(0));
        if (compound.hasKey("CustomName", 8)) {
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    private int getItemActiveTime(ItemStack itemStack) {
        return getItemBurnTime(itemStack);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.furnaceBurnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        compound.setInteger("ActiveTime", (short)this.pulverizerActiveTime);
        compound.setInteger("CrushTime", (short)this.crushTime);
        compound.setInteger("CrushTimeTotal", (short)this.totalCrushTime);
        ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);
        if (this.hasCustomName()) {
            compound.setString("CustomName", this.furnaceCustomName);
        }
        return compound;
    }

    public int getCookTime(ItemStack stack) {
        return 200;
    }

    public int getCrushTime(ItemStack stack) {
        return 200;
    }

    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }

    public boolean isActive()
    {
        return this.pulverizerActiveTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isActive(IInventory inventory)
    {
        return inventory.getField(4) > 0;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.DOWN) {
            return SLOTS_BOTTOM;
        } else {
            return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.furnaceItemStacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return this.furnaceItemStacks.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.furnaceItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.furnaceItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 1 && !flag) {
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
            this.markDirty();
        }

        if (index == 0 && !flag) {
            this.totalCrushTime = this.getCrushTime(stack);
            this.crushTime = 0;
            this.markDirty();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        switch (index){
            case 0:
                return true;
            case 1:
                return true;
            case 2:
                return false;
            case 3:
                ItemStack itemstack = this.furnaceItemStacks.get(3);
                return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
        return false;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            case 4:
                return this.pulverizerActiveTime;
            case 5:
                return this.currentItemActiveTime;
            case 6:
                return this.crushTime;
            case 7:
                return this.totalCrushTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
                break;
            case 4:
                this.pulverizerActiveTime = value;
                break;
            case 5:
                this.currentItemActiveTime = value;
                break;
            case 6:
                this.crushTime = value;
                break;
            case 7:
                this.totalCrushTime = value;

        }
    }

    @Override
    public int getFieldCount() {
        return 8;
    }

    @Override
    public void clear() {
        this.furnaceItemStacks.clear();
    }

    @Override
    public void update() {
        boolean flagA = this.isFurnaceBurning(), flagB = this.isPulverizerCrushing();
        boolean flag1 = false, flag2 = false;
        if (this.isFurnaceBurning()) {
            this.furnaceBurnTime--;
        }
        if(this.isPulverizerCrushing()){
            this.pulverizerActiveTime--;
        }

        if (!this.world.isRemote) {
            ItemStack fuelStack = this.furnaceItemStacks.get(3);

            if (this.isFurnaceBurning() || !fuelStack.isEmpty() && !(this.furnaceItemStacks.get(1)).isEmpty()) {
                if (!this.isFurnaceBurning() && this.canSmelt()) {
                    this.furnaceBurnTime = getItemBurnTime(fuelStack);
                    this.currentItemBurnTime = this.furnaceBurnTime;

                    if (this.isFurnaceBurning()) {
                        flag1 = true;

                        if (!fuelStack.isEmpty()) {
                            Item item = fuelStack.getItem();
                            fuelStack.shrink(1);

                            if (fuelStack.isEmpty()) {
                                ItemStack item1 = item.getContainerItem(fuelStack);
                                this.furnaceItemStacks.set(1, item1);
                            }
                        }
                    }
                }

                if (this.isFurnaceBurning() && this.canSmelt()) {
                    this.cookTime++;

                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.furnaceItemStacks.get(1));
                        this.smeltItem();
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isFurnaceBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (this.isPulverizerCrushing() || !fuelStack.isEmpty() && !(this.furnaceItemStacks.get(0)).isEmpty()) {
                if (!this.isPulverizerCrushing() && this.canCrush()) {
                    this.pulverizerActiveTime = getItemBurnTime(fuelStack);
                    this.currentItemActiveTime = this.pulverizerActiveTime;

                    if (this.isPulverizerCrushing()) {
                        flag2 = true;

                        if (!fuelStack.isEmpty()) {
                            Item item = fuelStack.getItem();
                            fuelStack.shrink(1);

                            if (fuelStack.isEmpty()) {
                                ItemStack item1 = item.getContainerItem(fuelStack);
                                this.furnaceItemStacks.set(1, item1);
                            }
                        }
                    }
                }

                if (this.isPulverizerCrushing() && this.canCrush()) {
                    this.crushTime++;

                    if (this.crushTime == this.totalCrushTime) {
                        this.crushTime = 0;
                        this.totalCrushTime = this.getCrushTime(this.furnaceItemStacks.get(0));
                        this.crushItem();
                        flag2 = true;
                    }
                } else {
                    this.crushTime = 0;
                }
            } else if (!this.isPulverizerCrushing() && this.crushTime > 0) {
                this.crushTime = MathHelper.clamp(this.crushTime - 2, 0, this.totalCrushTime);
            }

            if (flagA != this.isFurnaceBurning()) {
                flag1 = true;
                BlockMultiFurnace.setState(this.isFurnaceBurning(), this.world, this.pos);
            }
            if (flagB != this.isPulverizerCrushing()) {
                flag2 = true;
                BlockMultiFurnace.setState(this.isPulverizerCrushing(), this.world, this.pos);
            }
        }

        if (flag1 || flag2) {
            this.markDirty();
        }
    }

    public boolean isFurnaceBurning() {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isFurnaceBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    private boolean isPulverizerCrushing() {
        return this.pulverizerActiveTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isPulverizerCrushing(IInventory inventory){
        return inventory.getField(4) > 0;
    }

    private boolean canSmelt() {
        if ((this.furnaceItemStacks.get(1)).isEmpty()) {
            return false;
        } else {
            ItemStack itemToSmelt = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks.get(1));

            if (itemToSmelt.isEmpty()) {
                return false;
            } else {
                ItemStack itemSmeltRes = this.furnaceItemStacks.get(2);
                if (itemSmeltRes.isEmpty()) {
                    return true;
                } else if (!itemSmeltRes.isItemEqual(itemToSmelt)) {
                    return false;
                } else if (itemSmeltRes.getCount() + itemToSmelt.getCount() <= this.getInventoryStackLimit() && itemSmeltRes.getCount() + itemToSmelt.getCount() <= itemSmeltRes.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemSmeltRes.getCount() + itemToSmelt.getCount() <= itemToSmelt.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }

    private boolean canCrush() {
        if((this.furnaceItemStacks.get(0)).isEmpty()){
            return false;
        }else{
            ItemStack itemToCrush = CrushRecipes.instance().getCrushingResult(this.furnaceItemStacks.get(0));

            if(itemToCrush.isEmpty()){
                return false;
            }else{
                ItemStack itemCrushRes = this.furnaceItemStacks.get(1);
                if(itemCrushRes.isEmpty()){
                    return true;
                }else if(!itemCrushRes.isItemEqual(itemToCrush)){
                    return false;
                }else if(itemCrushRes.getCount() + itemToCrush.getCount() <= this.getInventoryStackLimit() && itemCrushRes.getCount() + itemToCrush.getCount() <= itemCrushRes.getMaxStackSize()){
                    return true;
                }else{
                    return itemCrushRes.getCount() + itemToCrush.getCount() <= itemCrushRes.getMaxStackSize();
                }
            }
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemToSmelt = this.furnaceItemStacks.get(1);
            ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemToSmelt);
            ItemStack itemSmeltRes = this.furnaceItemStacks.get(2);

            if (itemSmeltRes.isEmpty()) {
                this.furnaceItemStacks.set(2, itemstack1.copy());
            } else if (itemSmeltRes.getItem() == itemstack1.getItem()) {
                itemSmeltRes.grow(itemstack1.getCount());
            }

            if (itemToSmelt.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemToSmelt.getMetadata() == 1 && !(this.furnaceItemStacks.get(3)).isEmpty() && (this.furnaceItemStacks.get(3)).getItem() == Items.BUCKET) {
                this.furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemToSmelt.shrink(1);
        }
    }

    private void crushItem() {
        if(this.canCrush()){
            ItemStack itemToCrush = this.furnaceItemStacks.get(0);
            ItemStack itemstack1 = CrushRecipes.instance().getCrushingResult(itemToCrush);
            ItemStack itemCrushRes = this.furnaceItemStacks.get(1);

            if (itemCrushRes.isEmpty()) {
                this.furnaceItemStacks.set(1, itemstack1.copy());
            }else if(itemCrushRes.getItem() == itemstack1.getItem()){
                itemCrushRes.grow(itemstack1.getCount());
            }
            
            itemToCrush.shrink(1);
        }
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerMultiFurnace(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return "mof2mod:multi_furnace";
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.furnaceCustomName : "container.multi_furnace";
    }

    @Override
    public boolean hasCustomName() {
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String displayName) {
        this.furnaceCustomName = displayName;
    }
}
