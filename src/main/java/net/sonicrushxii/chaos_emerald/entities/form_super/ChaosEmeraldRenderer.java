package net.sonicrushxii.chaos_emerald.entities.form_super;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.entities.false_super.ChaosSpaz;
import net.sonicrushxii.chaos_emerald.entities.false_super.ChaosSpazModel;
import org.jetbrains.annotations.NotNull;

public class ChaosEmeraldRenderer extends EntityRenderer<ChaosEmeraldEntity> {
    private final EntityModel<ChaosEmeraldEntity> model;


    public ChaosEmeraldRenderer(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelPart = context.bakeLayer(new ModelLayerLocation(new ResourceLocation(ChaosEmerald.MOD_ID, "chaos_emerald_model"), "main"));
        this.model = new ChaosEmeraldModel<>(modelPart);
    }

    @Override //
    public ResourceLocation getTextureLocation(ChaosEmeraldEntity chaosEmeraldEntity) {
        return switch(chaosEmeraldEntity.getEmeraldType())
        {
            case AQUA_EMERALD -> new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/chaos_emerald/aqua_entity_texture.png");
            case BLUE_EMERALD -> new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/chaos_emerald/blue_entity_texture.png");
            case GREEN_EMERALD -> new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/chaos_emerald/green_entity_texture.png");
            case GREY_EMERALD -> new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/chaos_emerald/grey_entity_texture.png");
            case PURPLE_EMERALD -> new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/chaos_emerald/purple_entity_texture.png");
            case RED_EMERALD -> new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/chaos_emerald/red_entity_texture.png");
            case YELLOW_EMERALD -> new ResourceLocation(ChaosEmerald.MOD_ID, "textures/custom_model/chaos_emerald/yellow_entity_texture.png");
            default -> null;
        };
    }

    @Override
    public void render(@NotNull ChaosEmeraldEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
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