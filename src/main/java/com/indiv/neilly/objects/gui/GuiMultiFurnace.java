package com.indiv.neilly.objects.gui;

import com.indiv.neilly.inventory.ContainerMultiFurnace;
import com.indiv.neilly.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiMultiFurnace extends GuiContainer {
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/multi_furnace.png");
    private final InventoryPlayer inventoryPlayer;
    private final IInventory tileMultiFurnace;

    public GuiMultiFurnace(InventoryPlayer playerInv, IInventory furnaceInv) {
        super(new ContainerMultiFurnace(playerInv, furnaceInv));
        this.inventoryPlayer = playerInv;
        this.tileMultiFurnace = furnaceInv;
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
    
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.tileMultiFurnace.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.inventoryPlayer.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (TileEntityFurnace.isBurning(this.tileMultiFurnace)) {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }

    private int getCookProgressScaled(int pixels) {
        int i = this.tileMultiFurnace.getField(2);
        int j = this.tileMultiFurnace.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels) {
        int i = this.tileMultiFurnace.getField(1);

        if (i == 0)
        {
            i = 200;
        }

        return this.tileMultiFurnace.getField(0) * pixels / i;
    }
}
