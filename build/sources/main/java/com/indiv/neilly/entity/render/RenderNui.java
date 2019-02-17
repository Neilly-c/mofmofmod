package com.indiv.neilly.entity.render;

import com.indiv.neilly.entity.EntityNui;
import com.indiv.neilly.entity.model.ModelNui;
import com.indiv.neilly.util.Reference;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderNui extends RenderLiving<EntityNui>{

    public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/nui.png");

    public RenderNui(RenderManager manager){
        super(manager, new ModelNui(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityNui entity){
        return TEXTURES;
    }

    @Override
    protected void applyRotations(EntityNui entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}
