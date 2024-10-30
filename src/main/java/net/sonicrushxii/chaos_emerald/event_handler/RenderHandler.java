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
import net.sonicrushxii.chaos_emerald.entities.aqua.ChaosBubbleModel;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;

@Mod.EventBusSubscriber(modid = ChaosEmerald.MOD_ID, value= Dist.CLIENT)
public class RenderHandler {
    @SubscribeEvent
    public static void onPreRenderLiving(RenderLivingEvent.Pre<?, ?> event)
    {

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
    }
}
