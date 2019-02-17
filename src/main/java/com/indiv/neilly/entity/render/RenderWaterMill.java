package com.indiv.neilly.entity.render;

import com.indiv.neilly.entity.EntityNui;
import com.indiv.neilly.entity.EntityWaterMill;
import com.indiv.neilly.entity.model.ModelWaterMill;
import com.indiv.neilly.util.Reference;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderWaterMill extends Render<EntityWaterMill> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/water_mill.png");
    protected ModelBase modelWaterMill = new ModelWaterMill();

    public RenderWaterMill(RenderManager renderManager) {
        super(renderManager);
    }

    public void doRender(EntityWaterMill entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y - 0.5F, z);
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.modelWaterMill.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityWaterMill entity) {
        return TEXTURES;
    }
}
