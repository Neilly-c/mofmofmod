package com.indiv.neilly.util.handlers;

import com.indiv.neilly.entity.EntityNui;
import com.indiv.neilly.entity.EntityWaterMill;
import com.indiv.neilly.entity.render.RenderNui;
import com.indiv.neilly.entity.render.RenderWaterMill;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders(){
        RenderingRegistry.registerEntityRenderingHandler(EntityNui.class, manager -> new RenderNui(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntityWaterMill.class, RenderWaterMill::new);
    }
}
