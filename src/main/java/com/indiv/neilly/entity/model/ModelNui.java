package com.indiv.neilly.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

/**
 * EntityNui - Neilly
 * Created using Tabula 7.0.0
 */
public class ModelNui extends ModelBase {
    public ModelRenderer body_height;
    public ModelRenderer body_width;
    public ModelRenderer body_length;
    public ModelRenderer face;
    public ModelRenderer ear_left;
    public ModelRenderer ear_right;
    public ModelRenderer eye_left;
    public ModelRenderer eye_right;
    public ModelRenderer hand_left;
    public ModelRenderer hand_right;
    public ModelRenderer leg_left;
    public ModelRenderer leg_right;

    public ModelNui() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.eye_left = new ModelRenderer(this, 0, 18);
        this.eye_left.setRotationPoint(0.0F, 22.0F, -4.0F);
        this.eye_left.addBox(1.7F, -0.3F, -2.2F, 1, 1, 1, 0.0F);
        this.ear_left = new ModelRenderer(this, 34, 9);
        this.ear_left.setRotationPoint(2.5F, 21.0F, -4.0F);
        this.ear_left.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
        this.setRotateAngle(ear_left, 0.0F, 0.0F, -0.5235987755982988F);
        this.leg_left = new ModelRenderer(this, 34, 9);
        this.leg_left.setRotationPoint(1.3F, 23.7F, 4.5F);
        this.leg_left.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.leg_right = new ModelRenderer(this, 34, 9);
        this.leg_right.setRotationPoint(-1.3F, 23.7F, 4.5F);
        this.leg_right.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.body_height = new ModelRenderer(this, 0, 0);
        this.body_height.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.body_height.addBox(-2.5F, -3.0F, -4.0F, 5, 3, 8, 0.0F);
        this.face = new ModelRenderer(this, 34, 0);
        this.face.setRotationPoint(0.0F, 22.0F, -4.0F);
        this.face.addBox(-2.0F, -1.5F, -1.5F, 4, 3, 3, 0.0F);
        this.hand_right = new ModelRenderer(this, 34, 9);
        this.hand_right.setRotationPoint(-1.3F, 23.7F, -4.5F);
        this.hand_right.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.eye_right = new ModelRenderer(this, 0, 18);
        this.eye_right.setRotationPoint(0.0F, 22.0F, -4.0F);
        this.eye_right.addBox(-2.7F, -0.3F, -2.2F, 1, 1, 1, 0.0F);
        this.body_width = new ModelRenderer(this, 0, 0);
        this.body_width.setRotationPoint(0.0F, 22.5F, 0.0F);
        this.body_width.addBox(-3.0F, -2.5F, -4.0F, 6, 4, 8, 0.0F);
        this.body_length = new ModelRenderer(this, 0, 0);
        this.body_length.setRotationPoint(0.0F, 22.2F, -0.5F);
        this.body_length.addBox(-2.5F, -2.5F, -4.0F, 5, 4, 9, 0.0F);
        this.hand_left = new ModelRenderer(this, 34, 9);
        this.hand_left.setRotationPoint(1.3F, 23.7F, -4.5F);
        this.hand_left.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.ear_right = new ModelRenderer(this, 34, 9);
        this.ear_right.setRotationPoint(-2.5F, 21.0F, -4.0F);
        this.ear_right.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 2, 0.0F);
        this.setRotateAngle(ear_right, 0.0F, 0.0F, 0.5235987755982988F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.eye_left.offsetX, this.eye_left.offsetY, this.eye_left.offsetZ);
        GlStateManager.translate(this.eye_left.rotationPointX * f5, this.eye_left.rotationPointY * f5, this.eye_left.rotationPointZ * f5);
        GlStateManager.scale(0.7D, 0.7D, 0.7D);
        GlStateManager.translate(-this.eye_left.offsetX, -this.eye_left.offsetY, -this.eye_left.offsetZ);
        GlStateManager.translate(-this.eye_left.rotationPointX * f5, -this.eye_left.rotationPointY * f5, -this.eye_left.rotationPointZ * f5);
        this.eye_left.render(f5);
        GlStateManager.popMatrix();
        this.ear_left.render(f5);
        this.leg_left.render(f5);
        this.leg_right.render(f5);
        this.body_height.render(f5);
        this.face.render(f5);
        this.hand_right.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.eye_right.offsetX, this.eye_right.offsetY, this.eye_right.offsetZ);
        GlStateManager.translate(this.eye_right.rotationPointX * f5, this.eye_right.rotationPointY * f5, this.eye_right.rotationPointZ * f5);
        GlStateManager.scale(0.7D, 0.7D, 0.7D);
        GlStateManager.translate(-this.eye_right.offsetX, -this.eye_right.offsetY, -this.eye_right.offsetZ);
        GlStateManager.translate(-this.eye_right.rotationPointX * f5, -this.eye_right.rotationPointY * f5, -this.eye_right.rotationPointZ * f5);
        this.eye_right.render(f5);
        GlStateManager.popMatrix();
        this.body_width.render(f5);
        this.body_length.render(f5);
        this.hand_left.render(f5);
        this.ear_right.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.face.rotateAngleX = netHeadYaw * 0.01F;
        this.eye_left.rotateAngleX = netHeadYaw * 0.01F;
        this.eye_right.rotateAngleX = netHeadYaw * 0.01F;
        this.face.rotateAngleY = netHeadYaw * 0.01F;
        this.eye_left.rotateAngleY = netHeadYaw * 0.01F;
        this.eye_right.rotateAngleY = netHeadYaw * 0.01F;

    }
}
