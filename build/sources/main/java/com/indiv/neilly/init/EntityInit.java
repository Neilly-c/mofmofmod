package com.indiv.neilly.init;

import com.indiv.neilly.Main;
import com.indiv.neilly.entity.EntityNui;
import com.indiv.neilly.entity.EntityWaterMill;
import com.indiv.neilly.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit {

    public static void registerEntities(){
        registrerEntity("nui", EntityNui.class, Reference.ENTITY_NUI, 50, 16772520, 000000);
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":water_mill"), EntityWaterMill.class, "water_mill", Reference.ENTITY_WATER_MILL, Main.instance, 50, 1, true);
    }

    private static void registrerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2){
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, Main.instance, range, 1, true, color1, color2);
    }

}
