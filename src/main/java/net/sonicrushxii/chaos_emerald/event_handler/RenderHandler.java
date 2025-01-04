package net.sonicrushxii.chaos_emerald.event_handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.entities.aqua.ChaosBubbleModel;
import net.sonicrushxii.chaos_emerald.entities.form_super.SuperFormFlightModel;
import net.sonicrushxii.chaos_emerald.entities.green.ChaosDivePlayerModel;
import net.sonicrushxii.chaos_emerald.entities.yellow.ChaosGambitPlayerModel;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = ChaosEmerald.MOD_ID, value= Dist.CLIENT)
public class RenderHandler {
    @SubscribeEvent
    public static void onPreRenderLiving(RenderLivingEvent.Pre<?, ?> event)
    {
        PoseStack poseStack = event.getPoseStack();
        LivingEntity entity = event.getEntity();

        if(entity instanceof Player player)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                //Grey Chaos Emerald
                if(chaosEmeraldCap.greyChaosUse > 1)
                {
                    float yaw = player.getYRot();
                    float pitch = player.getXRot();

                    //Rotate Player
                    poseStack.pushPose();
                    // Translate the model to the player's position
                    poseStack.translate(0.0D, 1.0D, 0.0D); // Adjust as needed to center rotation at the player's position
                    // First, rotate the model horizontally based on the yaw
                    poseStack.mulPose(Axis.YP.rotationDegrees(-yaw+45F*(chaosEmeraldCap.greyChaosUse %8)));
                    // Then, rotate the model so it lies horizontally in the direction of the pitch
                    poseStack.mulPose(Axis.XP.rotationDegrees(pitch + 90F));
                    // Translate back to original position
                    poseStack.translate(0.0D, -1.0D, 0.0D); // Undo the initial translation
                }

                //Green Super Emerald
                if (chaosEmeraldCap.greenSuperUse > 0)
                {
                    poseStack.pushPose();

                    // Scale
                    poseStack.scale(1.0f, 1.0f, 1.0f);

                    //Apply Rotation & Translation
                    poseStack.mulPose(Axis.YP.rotationDegrees(-player.getYRot()));
                    poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                    poseStack.translate(0D, -1.5D, 0D);

                    //Render The Custom Model
                    ModModelRenderer.renderPlayerModel(ChaosDivePlayerModel.class, event, poseStack, (modelPart)->
                    {
                        modelPart.getChild("PlayerModel").xRot += (chaosEmeraldCap.greenSuperUse <= 5)?0:Math.min(20,chaosEmeraldCap.greenSuperUse-5)*(8f*(float)Math.PI/180);
                    });

                    poseStack.popPose();
                    event.setCanceled(true);
                }

                //Yellow Super Emerald
                if (chaosEmeraldCap.yellowSuperUse > 10)
                {
                    poseStack.pushPose();

                    // Scale
                    poseStack.scale(1.0f, 1.0f, 1.0f);

                    //Apply Rotation & Translation
                    poseStack.mulPose(Axis.YP.rotationDegrees(-chaosEmeraldCap.atkRotPhaseY));
                    poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                    poseStack.translate(0D, -1.5D, 0D);

                    //Render The Custom Model
                    ModModelRenderer.renderPlayerModel(ChaosGambitPlayerModel.class, event, poseStack, null);

                    poseStack.popPose();
                    event.setCanceled(true);
                }

                //Super Form Flight
                if(chaosEmeraldCap.superFormTimer > 0 && player.isSprinting())
                {
                    Vec3 deltaMovement = player.getDeltaMovement();
                    double movementCoefficient = Math.abs(deltaMovement.x) + Math.abs(deltaMovement.y) + Math.abs(deltaMovement.z);

                    if(movementCoefficient > 2.0 || player.getAbilities().flying)
                    {
                        poseStack.pushPose();

                        // Scale
                        poseStack.scale(1.0f, 1.0f, 1.0f);

                        //Apply Rotation & Translation
                        poseStack.mulPose(Axis.YP.rotationDegrees(-player.getYRot()));
                        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                        poseStack.translate(0D, -1.5D, 0D);

                        //Create a Custom Transform
                        Consumer<ModelPart> customTransform = SuperFormFlightModel.getCustomTransform(player);

                        //Render The Custom Model
                        ModModelRenderer.renderPlayerModel(SuperFormFlightModel.class, event, poseStack, customTransform);

                        poseStack.popPose();
                        event.setCanceled(true);
                    }
                }
            });
        }

    }



    @SubscribeEvent
    public static void onPostRenderLiving(RenderLivingEvent.Post<?, ?> event)
    {
        LivingEntity targetEntity = event.getEntity();
        PoseStack poseStack = event.getPoseStack();

        //Aqua Chaos Emerald
        {
            if(targetEntity.hasEffect(ModEffects.CHAOS_BIND.get()) && targetEntity.getEffect(ModEffects.CHAOS_BIND.get()).getDuration() > 0)
            {
                poseStack.pushPose();

                //Get Entity Bounding Box
                AABB entityBox = targetEntity.getBoundingBox();
                final float bubbleHeight = Math.max(
                        Math.max((float)Math.abs(entityBox.minX-entityBox.maxX),
                                 (float)Math.abs(entityBox.minY-entityBox.maxY)
                        ), (float)Math.abs(entityBox.minZ-entityBox.maxZ)) + 3f;

                //Translate
                poseStack.translate(-Math.abs(entityBox.minX-entityBox.maxX)*0.2,
                        -bubbleHeight/1.65,
                        -Math.abs(entityBox.minZ-entityBox.maxZ)*0.2);

                //Scale
                poseStack.scale(bubbleHeight,bubbleHeight,bubbleHeight);

                //Render The Custom Model
                ModModelRenderer.renderModel(ChaosBubbleModel.class, event, poseStack);
                poseStack.popPose();
            }
        }


        if(targetEntity instanceof Player player)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                //Rotate Player
                if(chaosEmeraldCap.greyChaosUse > 1) {
                    poseStack.popPose();
                }
            });
        }

    }
}
