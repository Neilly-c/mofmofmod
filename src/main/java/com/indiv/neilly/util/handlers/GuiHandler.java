package com.indiv.neilly.util.handlers;

import com.indiv.neilly.entity.TileEntityMultiFurnace;
import com.indiv.neilly.inventory.ContainerMultiFurnace;
import com.indiv.neilly.objects.gui.GuiMultiFurnace;
import com.indiv.neilly.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Reference.GUI_MULTI_FURNACE)
            return new ContainerMultiFurnace(player.inventory, (TileEntityMultiFurnace)world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Reference.GUI_MULTI_FURNACE)
            return new GuiMultiFurnace(player.inventory, (TileEntityMultiFurnace)world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}
