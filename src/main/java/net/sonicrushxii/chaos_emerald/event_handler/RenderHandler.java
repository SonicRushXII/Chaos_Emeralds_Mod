package net.sonicrushxii.chaos_emerald.event_handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.entities.aqua.ChaosBubbleModel;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;

@Mod.EventBusSubscriber(modid = ChaosEmerald.MOD_ID, value= Dist.CLIENT)
public class RenderHandler {
    @SubscribeEvent
    public static void onPreRenderLiving(RenderLivingEvent.Pre<?, ?> event)
    {
        PoseStack poseStack = event.getPoseStack();
        LivingEntity entity = event.getEntity();

        //Grey Emerald
        if(entity instanceof Player player)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if(chaosEmeraldCap.greyEmeraldUse > 1)
                {
                    float yaw = player.getYRot();
                    float pitch = player.getXRot();

                    //Rotate Player
                    poseStack.pushPose();
                    // Translate the model to the player's position
                    poseStack.translate(0.0D, 1.0D, 0.0D); // Adjust as needed to center rotation at the player's position
                    // First, rotate the model horizontally based on the yaw
                    poseStack.mulPose(Axis.YP.rotationDegrees(-yaw+45F*(chaosEmeraldCap.greyEmeraldUse%8)));
                    // Then, rotate the model so it lies horizontally in the direction of the pitch
                    poseStack.mulPose(Axis.XP.rotationDegrees(pitch + 90F));
                    // Translate back to original position
                    poseStack.translate(0.0D, -1.0D, 0.0D); // Undo the initial translation
                }
            });
        }

    }

    @SubscribeEvent
    public static void onPostRenderLiving(RenderLivingEvent.Post<?, ?> event)
    {
        LivingEntity targetEntity = event.getEntity();
        PoseStack poseStack = event.getPoseStack();

        //Aqua Emerald
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

        //Grey Emerald
        if(targetEntity instanceof Player player)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                //Rotate Player
                if(chaosEmeraldCap.greyEmeraldUse > 1) {
                    poseStack.popPose();
                }
            });
        }

    }
}
