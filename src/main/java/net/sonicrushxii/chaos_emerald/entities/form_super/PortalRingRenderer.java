package net.sonicrushxii.chaos_emerald.entities.form_super;

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

public class PortalRingRenderer extends EntityRenderer<PortalRingEntity> {
    private final EntityModel<PortalRingEntity> model;
    public PortalRingRenderer(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(new ModelLayerLocation(new ResourceLocation(ChaosEmerald.MOD_ID, "chaos_portal_ring_model"), "main"));
        this.model = new PortalRingModel<>(modelPart);
    }

    @Override //
    public ResourceLocation getTextureLocation(PortalRingEntity portalRingEntity) {
        return switch(portalRingEntity.getPortalType())
        {
            case 0 -> new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/super_form/chaos_portal_ring_texture.png");
            case 1 -> new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/hyper_form/super_portal_ring_texture.png");
            default -> new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/super_form/chaos_portal_ring_texture.png");
        };
    }

    @Override
    public void render(@NotNull PortalRingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        // Translate and rotate the pose stack as needed
        poseStack.pushPose();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity)));

        //poseStack.scale(1.0F,1.0F,1.0F);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.translate(0D,-1.4D,0D);

        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}