package com.indiv.neilly.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMultiFurnace extends Container {
    private final IInventory tileMultiFurnace;
    private int furnaceBurnTime, currentItemBurnTime, cookTime, totalCookTime, pulverizerActiveTime, currentItemActiveTime, crushTime, totalCrushTime;


    public ContainerMultiFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory) {
        this.tileMultiFurnace = furnaceInventory;
        this.addSlotToContainer(new Slot(furnaceInventory, 0, 26, 17));
        this.addSlotToContainer(new Slot(furnaceInventory, 1, 80, 17));
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 2, 140, 35));
        this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 3, 53, 53));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }
    
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileMultiFurnace);
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.cookTime != this.tileMultiFurnace.getField(2)) {
                icontainerlistener.sendWindowProperty(this, 2, this.tileMultiFurnace.getField(2));
            }
            if (this.furnaceBurnTime != this.tileMultiFurnace.getField(0)) {
                icontainerlistener.sendWindowProperty(this, 0, this.tileMultiFurnace.getField(0));
            }
            if (this.currentItemBurnTime != this.tileMultiFurnace.getField(1)) {
                icontainerlistener.sendWindowProperty(this, 1, this.tileMultiFurnace.getField(1));
            }
            if (this.totalCookTime != this.tileMultiFurnace.getField(3)) {
                icontainerlistener.sendWindowProperty(this, 3, this.tileMultiFurnace.getField(3));
            }

            if (this.crushTime != this.tileMultiFurnace.getField(6)) {
                icontainerlistener.sendWindowProperty(this, 6, this.tileMultiFurnace.getField(6));
            }
            if (this.pulverizerActiveTime != this.tileMultiFurnace.getField(4)) {
                icontainerlistener.sendWindowProperty(this, 4, this.tileMultiFurnace.getField(4));
            }
            if (this.currentItemActiveTime != this.tileMultiFurnace.getField(5)) {
                icontainerlistener.sendWindowProperty(this, 5, this.tileMultiFurnace.getField(5));
            }
            if (this.totalCrushTime != this.tileMultiFurnace.getField(7)) {
                icontainerlistener.sendWindowProperty(this, 7, this.tileMultiFurnace.getField(7));
            }
        }

        this.cookTime = this.tileMultiFurnace.getField(2);
        this.furnaceBurnTime = this.tileMultiFurnace.getField(0);
        this.currentItemBurnTime = this.tileMultiFurnace.getField(1);
        this.totalCookTime = this.tileMultiFurnace.getField(3);

        this.crushTime = this.tileMultiFurnace.getField(6);
        this.pulverizerActiveTime = this.tileMultiFurnace.getField(4);
        this.currentItemActiveTime = this.tileMultiFurnace.getField(5);
        this.totalCrushTime = this.tileMultiFurnace.getField(7);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tileMultiFurnace.setField(id, data);
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileMultiFurnace.isUsableByPlayer(playerIn);
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 3 && index != 1 && index != 0) {
                if (!FurnaceRecipes.instance().getSmeltingResult(itemstack1).isEmpty()) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (TileEntityFurnace.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 3, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 4 && index < 31) {
                    if (!this.mergeItemStack(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 40 && !this.mergeItemStack(itemstack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
