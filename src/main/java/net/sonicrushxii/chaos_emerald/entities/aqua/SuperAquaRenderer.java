package net.sonicrushxii.chaos_emerald.entities.aqua;

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
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import org.jetbrains.annotations.NotNull;

public class SuperAquaRenderer extends EntityRenderer<SuperAquaBubbleEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/aqua/chaos_bubble.png");
    private final EntityModel<SuperAquaBubbleEntity> model;

    public SuperAquaRenderer(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(new ModelLayerLocation(new ResourceLocation(ChaosEmerald.MOD_ID, "chaos_bubble"), "main"));
        this.model = new ChaosBubbleModel<>(modelPart);
    }

    @Override
    public ResourceLocation getTextureLocation(SuperAquaBubbleEntity entity) {
        return TEXTURE;
    }

    @Override
    public Vec3 getRenderOffset(SuperAquaBubbleEntity pEntity, float pPartialTicks) {
        return super.getRenderOffset(pEntity, pPartialTicks);
    }

    @Override
    public void render(SuperAquaBubbleEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        // Translate and rotate the pose stack as needed
        poseStack.pushPose();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));

        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.translate(0D,-1.0D,0D);

        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}