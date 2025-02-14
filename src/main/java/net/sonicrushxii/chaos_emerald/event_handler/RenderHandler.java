package net.sonicrushxii.chaos_emerald.event_handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.models.FlatPlayerModel;
import net.sonicrushxii.chaos_emerald.modded.ModModelRenderer;

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
                //Time stop
                if (chaosEmeraldCap.playerFrozenDetails.isFrozen())
                {
                    poseStack.pushPose();

                    // Scale
                    poseStack.scale(1.0f, 1.0f, 1.0f);

                    //Apply Rotation & Translation
                    poseStack.mulPose(Axis.YP.rotationDegrees(-chaosEmeraldCap.playerFrozenDetails.frozenRotY));
                    poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                    poseStack.translate(0D, -1.5D, 0D);

                    //Render The Custom Model
                    ModModelRenderer.renderPlayerModel(FlatPlayerModel.class, event, poseStack, null);

                    poseStack.popPose();
                    event.setCanceled(true);
                }
            });
        }

    }

    @SubscribeEvent
    public static void onPostRenderLiving(RenderLivingEvent.Post<?, ?> event)
    {}
}
