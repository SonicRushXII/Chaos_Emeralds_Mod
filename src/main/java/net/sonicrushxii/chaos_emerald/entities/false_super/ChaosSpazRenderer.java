package net.sonicrushxii.chaos_emerald.entities.false_super;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import org.jetbrains.annotations.NotNull;

public class ChaosSpazRenderer extends EntityRenderer<ChaosSpaz> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/false_super/chaos_spaz.png");
    private final EntityModel<ChaosSpaz> model;

    public ChaosSpazRenderer(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(new ModelLayerLocation(new ResourceLocation(ChaosEmerald.MOD_ID, "false_super_chaos_spaz"), "main"));
        this.model = new ChaosSpazModel<>(modelPart);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(ChaosSpaz entity) {
        return TEXTURE;
    }

    @Override
    public void render(@NotNull ChaosSpaz entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        // Translate and rotate the pose stack as needed
        poseStack.pushPose();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity)));

        poseStack.scale(1.25F,1.25F,1.25F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
        poseStack.translate(0D,-1.0D,0D);

        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}