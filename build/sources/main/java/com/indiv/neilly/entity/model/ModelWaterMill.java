package com.indiv.neilly.entity.model;

import com.indiv.neilly.entity.EntityWaterMill;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelWaterMill - neilly
 * Created using Tabula 7.0.0
 */
public class ModelWaterMill extends ModelBase {
    public ModelRenderer shaft;
    public ModelRenderer blade1;
    public ModelRenderer blade2;
    public ModelRenderer blade3;
    public ModelRenderer blade4;
    public ModelRenderer ring1;
    public ModelRenderer ring2;
    public ModelRenderer ring3;
    public ModelRenderer ring4;
    public ModelRenderer ring5;
    public ModelRenderer ring6;
    public ModelRenderer ring7;
    public ModelRenderer ring8;

    public ModelWaterMill() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.ring2 = new ModelRenderer(this, 0, 0);
        this.ring2.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.ring2.addBox(8.0F, -4.0F, -6.0F, 1, 8, 12, -0.3F);
        this.setRotateAngle(ring2, 0.0F, 0.0F, 1.1780972450961724F);
        this.shaft = new ModelRenderer(this, 0, 0);
        this.shaft.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.shaft.addBox(-1.0F, -1.0F, -8.0F, 2, 2, 16, 0.0F);
        this.ring7 = new ModelRenderer(this, 0, 0);
        this.ring7.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.ring7.addBox(8.0F, -4.0F, -6.0F, 1, 8, 12, -0.3F);
        this.setRotateAngle(ring7, 0.0F, 0.0F, -1.1780972450961724F);
        this.blade3 = new ModelRenderer(this, 32, 0);
        this.blade3.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.blade3.addBox(-0.5F, -22.0F, -6.0F, 1, 44, 12, -0.3F);
        this.setRotateAngle(blade3, 0.0F, 0.0F, 1.5707963267948966F);
        this.blade1 = new ModelRenderer(this, 32, 0);
        this.blade1.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.blade1.addBox(-0.5F, -22.0F, -6.0F, 1, 44, 12, -0.3F);
        this.ring3 = new ModelRenderer(this, 0, 0);
        this.ring3.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.ring3.addBox(8.0F, -4.0F, -6.0F, 1, 8, 12, -0.3F);
        this.setRotateAngle(ring3, 0.0F, 0.0F, 1.9634954084936207F);
        this.ring1 = new ModelRenderer(this, 0, 0);
        this.ring1.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.ring1.addBox(8.0F, -4.0F, -6.0F, 1, 8, 12, -0.3F);
        this.setRotateAngle(ring1, 0.0F, 0.0F, 0.39269908169872414F);
        this.ring6 = new ModelRenderer(this, 0, 0);
        this.ring6.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.ring6.addBox(8.0F, -4.0F, -6.0F, 1, 8, 12, -0.3F);
        this.setRotateAngle(ring6, 0.0F, 0.0F, -1.9634954084936207F);
        this.ring8 = new ModelRenderer(this, 0, 0);
        this.ring8.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.ring8.addBox(8.0F, -4.0F, -6.0F, 1, 8, 12, -0.3F);
        this.setRotateAngle(ring8, 0.0F, 0.0F, -0.39269908169872414F);
        this.ring4 = new ModelRenderer(this, 0, 0);
        this.ring4.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.ring4.addBox(8.0F, -4.0F, -6.0F, 1, 8, 12, -0.3F);
        this.setRotateAngle(ring4, 0.0F, 0.0F, 2.748893571891069F);
        this.ring5 = new ModelRenderer(this, 0, 0);
        this.ring5.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.ring5.addBox(8.0F, -4.0F, -6.0F, 1, 8, 12, -0.3F);
        this.setRotateAngle(ring5, 0.0F, 0.0F, -2.748893571891069F);
        this.blade2 = new ModelRenderer(this, 32, 0);
        this.blade2.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.blade2.addBox(-0.5F, -22.0F, -6.0F, 1, 44, 12, -0.3F);
        this.setRotateAngle(blade2, 0.0F, 0.0F, 0.7853981633974483F);
        this.blade4 = new ModelRenderer(this, 32, 0);
        this.blade4.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.blade4.addBox(-0.5F, -22.0F, -6.0F, 1, 44, 12, -0.3F);
        this.setRotateAngle(blade4, 0.0F, 0.0F, 2.356194490192345F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GlStateManager.pushMatrix();
        switch (((EntityWaterMill)entity).getFacing()){
            case SOUTH:
                GlStateManager.rotate(0.0F, 0, 1, 0);
                break;
            case WEST:
                GlStateManager.rotate(90.0F, 0, 1, 0);
                break;
            case NORTH:
                GlStateManager.rotate(180.0F, 0, 1, 0);
                break;
            case EAST:
                GlStateManager.rotate(270.0F, 0, 1, 0);
                break;
        }
        float f6 = ((EntityWaterMill)entity).getRotateValue();
        GlStateManager.translate(0, 1, 0);
        GlStateManager.rotate(f6, 0, 0, 1);
        GlStateManager.translate(0, -1, 0);
        this.ring2.render(f5);
        this.shaft.render(f5);
        this.ring7.render(f5);
        this.blade3.render(f5);
        this.blade1.render(f5);
        this.ring3.render(f5);
        this.ring1.render(f5);
        this.ring6.render(f5);
        this.ring8.render(f5);
        this.ring4.render(f5);
        this.ring5.render(f5);
        this.blade2.render(f5);
        this.blade4.render(f5);
        GlStateManager.popMatrix();
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
