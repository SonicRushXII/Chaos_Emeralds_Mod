package net.sonicrushxii.chaos_emerald.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.all.ChaosUseDetails;
import net.sonicrushxii.chaos_emerald.event_handler.custom.ChaosEmeraldHandler;
import org.joml.Vector3f;

public class VirtualOverlay {

    private static final float OVERLAY_DECAY_RATE = 0.8F;

    private static final ResourceLocation EMPTY_SLOT = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/empty_slot.png");

    private static final ResourceLocation CHAOS_OVERLAY_INNER = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/chaos_overlay_inner.png");
    private static final ResourceLocation CHAOS_OVERLAY_OUTER = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/chaos_overlay_outer.png");

    //Register the Main Overlay
    public static final IGuiOverlay CHAOS_ABILITY_HUD = ((ForgeGui gui, GuiGraphics guiComponent, float partialTick, int screenWidth, int screenHeight)-> {
        AbstractClientPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            ChaosUseDetails chaosAbility = chaosEmeraldCap.chaosUseDetails;
            if(chaosAbility.useColor > -1 && (chaosAbility.timeStop > 0 || chaosAbility.teleport > 0))
                renderChaosOverlay(player,gui,guiComponent,partialTick,screenWidth,screenHeight);
        });
    });

    public static void renderChaosOverlay(AbstractClientPlayer player, ForgeGui gui, GuiGraphics guiComponent, float partialTick, int screenWidth, int screenHeight)
    {
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            ChaosUseDetails chaosAbility = chaosEmeraldCap.chaosUseDetails;

            //Calculate gradient based on the Time spent in Timestop
            float overlayGradient = (
                    (chaosAbility.teleport > 0)
                    ?OVERLAY_DECAY_RATE - ((float) (chaosAbility.teleport) / (ChaosEmeraldHandler.TELEPORT_DURATION)) * OVERLAY_DECAY_RATE
                    :OVERLAY_DECAY_RATE - ((float) (chaosAbility.timeStop) / (ChaosEmeraldHandler.TIME_STOP_DURATION)) * OVERLAY_DECAY_RATE
            );

            //Calculate Dimensions
            final int[] textureDimensions = {screenWidth,screenHeight};
            int x = 0;
            int y = 0;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            //Modify the color based on the Current Chaos Color
            Vector3f colorComponent = Utilities.hexToVector3f(chaosAbility.useColor);
            RenderSystem.setShaderColor(colorComponent.x, colorComponent.y, colorComponent.z, (1F-OVERLAY_DECAY_RATE)+overlayGradient);

            //Draw Outer Overlay
            RenderSystem.setShaderTexture(0, CHAOS_OVERLAY_OUTER);
            guiComponent.blit(
                    CHAOS_OVERLAY_OUTER,
                    x,y,0,0,
                    textureDimensions[0],textureDimensions[1],textureDimensions[0],textureDimensions[1]
            );

            //Return Render Color back to same
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            //Draw Inner Overlay
            RenderSystem.setShaderTexture(0, CHAOS_OVERLAY_INNER);
            guiComponent.blit(
                    CHAOS_OVERLAY_INNER,
                    x,y,0,0,
                    textureDimensions[0],textureDimensions[1],textureDimensions[0],textureDimensions[1]
            );
        });
    }

}