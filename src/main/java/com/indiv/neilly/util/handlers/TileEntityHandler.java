package com.indiv.neilly.util.handlers;

import com.indiv.neilly.entity.TileEntityMultiFurnace;
import com.indiv.neilly.entity.TileEntityNuiBed;
import com.indiv.neilly.entity.TileEntityWaterRemover;
import com.indiv.neilly.util.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
    public static void registerTileEntities(){
        GameRegistry.registerTileEntity(TileEntityNuiBed.class, Reference.MODID + ":nuiBed");
        GameRegistry.registerTileEntity(TileEntityWaterRemover.class, Reference.MODID + ":water_remover");
        GameRegistry.registerTileEntity(TileEntityMultiFurnace.class, Reference.MODID + ":multi_furnace");
    }
}
