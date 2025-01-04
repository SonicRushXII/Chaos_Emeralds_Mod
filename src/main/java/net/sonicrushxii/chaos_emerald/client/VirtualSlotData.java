package net.sonicrushxii.chaos_emerald.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.KeyBindings;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormAbility;
import net.sonicrushxii.chaos_emerald.capabilities.hyperform.HyperFormProperties;
import net.sonicrushxii.chaos_emerald.capabilities.superform.SuperFormAbility;
import net.sonicrushxii.chaos_emerald.capabilities.superform.SuperFormProperties;
import net.sonicrushxii.chaos_emerald.event_handler.custom.SuperFormHandler;

import java.util.Arrays;
import java.util.List;

public class VirtualSlotData {

    private static final ResourceLocation EMPTY_SLOT = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/empty_slot.png");

    private static final ResourceLocation SUPER_FORM_ICON_SLOT = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/superform/icon_slot.png");
    private static final ResourceLocation CHAOS_SPEAR_EX_SLOT = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/superform/chaos_spear_ex.png");
    private static final ResourceLocation CHAOS_CONTROL_EX_SLOT  = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/superform/chaos_control_ex.png");
    private static final ResourceLocation CHAOS_PORTAL_SLOT  = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/superform/chaos_portal_slot.png");

    private static final ResourceLocation HYPER_FORM_ICON_SLOT = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/hyperform/icon_slot.png");
    private static final ResourceLocation SUPER_SPEAR_EX_SLOT = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/hyperform/super_spear_ex.png");
    private static final ResourceLocation SUPER_CONTROL_EX_SLOT  = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/hyperform/super_control_ex.png");
    private static final ResourceLocation SUPER_BLAST_EX_SLOT  = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/hyperform/super_blast_slot.png");
    private static final ResourceLocation SUPER_PORTAL_SLOT  = new ResourceLocation(ChaosEmerald.MOD_ID,
            "textures/custom_gui/hyperform/super_portal_slot.png");

    private static final int ULT_BAR_WIDTH = 90;
    private static final int ULT_BAR_HEIGHT = 5;

    private record Ability(ResourceLocation texture, byte cooldown) {}

    private static String shortenName(String currName)
    {
        return switch (currName) {
            case "Right Button" -> "RMB";
            case "Backspace" -> "BkSpc";
            case "Caps Lock" -> "CAPS";
            case "Left Shift" -> "LShft";
            case "Right Shift" -> "RShft";
            case "Enter" -> "ENT";
            case "Left Alt" -> "LAlt";
            case "Right ALt" -> "RAlt";
            default -> currName;
        };
    }

    private static void renderSlot(Ability SLOT_ABILITY, GuiGraphics guiComponent, int x, int y, int[] textureDimensions)
    {
        ResourceLocation SLOT_TEXTURE = SLOT_ABILITY.texture();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        //Checks if On Cooldown or Not
        if(SLOT_ABILITY.cooldown() == (byte)0) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
        else{
            RenderSystem.setShaderColor(0.1F, 0.1F, 0.1F, 0.2F);
            guiComponent.drawString(Minecraft.getInstance().font,
                    (SLOT_ABILITY.cooldown<10)?"0"+SLOT_ABILITY.cooldown:""+SLOT_ABILITY.cooldown,
                    x+3*textureDimensions[0]/10,y+2*textureDimensions[1]/5,0xFFFFFF);
        }
        RenderSystem.setShaderTexture(0, SLOT_TEXTURE);

        //Draw the Actual Texture
        guiComponent.blit(SLOT_TEXTURE,x,y,
                0,0,textureDimensions[0],textureDimensions[1],textureDimensions[0],textureDimensions[1]);
        //Return Render Color back to same
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    //Register the Main Overlay
    public static final IGuiOverlay ABILITY_HUD = ((ForgeGui gui, GuiGraphics guiComponent, float partialTick, int screenWidth, int screenHeight)-> {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if(chaosEmeraldCap.superFormTimer > 0) renderSuperFormAbilities(player,gui,guiComponent,partialTick,screenWidth,screenHeight);
            if(chaosEmeraldCap.hyperFormTimer > 0) renderHyperFormAbilities(player,gui,guiComponent,partialTick,screenWidth,screenHeight);
        });
    });

    public static void renderSuperFormAbilities(LocalPlayer player, ForgeGui gui, GuiGraphics guiComponent, float partialTick, int screenWidth, int screenHeight)
    {
        final int[] textureDimensions = {22,22};
        int x = textureDimensions[0]; //screenWidth  - (int)(textureDimensions[0]*1.5);
        int y = 0;

        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            byte[] cooldownArray = ((SuperFormProperties)chaosEmeraldCap.formProperties).getAllCooldowns();

            //Render Slot Ability
            {
                String slotName = "Super";
                List<Ability> iconTextures = (Arrays.asList(
                        new Ability(CHAOS_SPEAR_EX_SLOT, cooldownArray[SuperFormAbility.CHAOS_CONTROL_EX.ordinal()]),
                        new Ability(CHAOS_CONTROL_EX_SLOT, cooldownArray[SuperFormAbility.CHAOS_SPEAR_EX.ordinal()]),
                        new Ability(CHAOS_PORTAL_SLOT, cooldownArray[SuperFormAbility.CHAOS_PORTAL.ordinal()]))
                );

                List<InputConstants.Key> keyBindings = Arrays.asList(
                        KeyBindings.INSTANCE.useAbility1.getKey(),
                        KeyBindings.INSTANCE.useAbility2.getKey(),
                        KeyBindings.INSTANCE.useAbility3.getKey(),
                        KeyBindings.INSTANCE.useAbility4.getKey(),
                        KeyBindings.INSTANCE.useAbility5.getKey(),
                        KeyBindings.INSTANCE.useAbility6.getKey()
                );

                //Render Slot Name
                guiComponent.drawCenteredString(Minecraft.getInstance().font, slotName,
                        x + textureDimensions[0] / 3,
                        y + textureDimensions[1] / 3,
                        0xFFFFFF);

                for (int i = 1; i <= iconTextures.size(); ++i) {
                    //Slot Icon
                    renderSlot(iconTextures.get(i - 1), guiComponent,
                            x, y + i * textureDimensions[1], textureDimensions);

                    //Keybinding for Ability
                    guiComponent.drawCenteredString(Minecraft.getInstance().font, shortenName(keyBindings.get(i - 1).getDisplayName().getString()),
                            x - textureDimensions[0] / 2,
                            y + textureDimensions[1] / 2 + i * textureDimensions[1],
                            0xFFFFFF);
                }
            }

            //Render Ultimate Bar
            {
                final int imageWidth = 16;
                final int imageHeight = 16;

                final int barX = (screenWidth - ULT_BAR_WIDTH + imageWidth + shortenName(KeyBindings.INSTANCE.transformButton.getKey().getDisplayName().getString()).length()*5 - 4) / 2; // Center horizontally
                int noOfHealthBars = (int)Math.ceil(((int)Math.ceil(player.getAbsorptionAmount())+(int)Math.ceil(player.getMaxHealth()))/20.0);
                final int barY = screenHeight - Math.min(2*screenHeight/5,((5+noOfHealthBars)*screenHeight/24));

                int barWidth = (int) ( (1.0/(SuperFormHandler.SUPERFORM_DURATION*20)) * (SuperFormHandler.SUPERFORM_DURATION*20-chaosEmeraldCap.superFormTimer) * ULT_BAR_WIDTH);
                guiComponent.fill(barX+1,barY+1, barX+ULT_BAR_WIDTH+1, barY-ULT_BAR_HEIGHT,0xFF000000);
                guiComponent.fill(barX,barY, barX+ULT_BAR_WIDTH, barY-ULT_BAR_HEIGHT,0xFF444444);
                guiComponent.fill(barX,barY, barX+barWidth, barY-ULT_BAR_HEIGHT,0xFFDDDD00);

                // Draw the image
                int imageX = barX - imageWidth - 5; // Image is to the left of the bar
                int imageY = barY - (ULT_BAR_HEIGHT + imageHeight) / 2; // Center the image vertically with the bar
                //Draw the Actual Texture
                guiComponent.blit(SUPER_FORM_ICON_SLOT,imageX,imageY,
                        0,0,imageWidth,imageHeight,imageWidth,imageHeight);

                int keyX = imageX - shortenName(KeyBindings.INSTANCE.transformButton.getKey().getDisplayName().getString()).length()*4 - 4;
                int keyY = imageY + imageHeight/3;

                //Keybinding for Ability
                guiComponent.drawCenteredString(Minecraft.getInstance().font,
                        shortenName(KeyBindings.INSTANCE.transformButton.getKey().getDisplayName().getString()),
                        keyX,
                        keyY,
                        0xFFFFFF);
            }
        });

    }

    public static void renderHyperFormAbilities(LocalPlayer player, ForgeGui gui, GuiGraphics guiComponent, float partialTick, int screenWidth, int screenHeight)
    {
        final int[] textureDimensions = {22,22};
        int x = textureDimensions[0]; //screenWidth  - (int)(textureDimensions[0]*1.5);
        int y = 0;

        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            byte[] cooldownArray = ((HyperFormProperties)chaosEmeraldCap.formProperties).getAllCooldowns();

            //Render Slot Ability
            {
                String slotName = "Hyper";
                List<Ability> iconTextures = (Arrays.asList(
                        new Ability(SUPER_SPEAR_EX_SLOT, cooldownArray[HyperFormAbility.SUPER_CHAOS_CONTROL_EX.ordinal()]),
                        new Ability(SUPER_CONTROL_EX_SLOT, cooldownArray[HyperFormAbility.SUPER_CHAOS_SPEAR_EX.ordinal()]),
                        new Ability(SUPER_BLAST_EX_SLOT, cooldownArray[HyperFormAbility.SUPER_CHAOS_PORTAL.ordinal()]),
                        new Ability(SUPER_PORTAL_SLOT, cooldownArray[HyperFormAbility.SUPER_CHAOS_PORTAL.ordinal()]))
                );

                List<InputConstants.Key> keyBindings = Arrays.asList(
                        KeyBindings.INSTANCE.useAbility1.getKey(),
                        KeyBindings.INSTANCE.useAbility2.getKey(),
                        KeyBindings.INSTANCE.useAbility3.getKey(),
                        KeyBindings.INSTANCE.useAbility4.getKey(),
                        KeyBindings.INSTANCE.useAbility5.getKey(),
                        KeyBindings.INSTANCE.useAbility6.getKey()
                );

                //Render Slot Name
                guiComponent.drawCenteredString(Minecraft.getInstance().font, slotName,
                        x + textureDimensions[0] / 3,
                        y + textureDimensions[1] / 3,
                        0xFFFFFF);

                for (int i = 1; i <= iconTextures.size(); ++i) {
                    //Slot Icon
                    renderSlot(iconTextures.get(i - 1), guiComponent,
                            x, y + i * textureDimensions[1], textureDimensions);

                    //Keybinding for Ability
                    guiComponent.drawCenteredString(Minecraft.getInstance().font, shortenName(keyBindings.get(i - 1).getDisplayName().getString()),
                            x - textureDimensions[0] / 2,
                            y + textureDimensions[1] / 2 + i * textureDimensions[1],
                            0xFFFFFF);
                }
            }

            //Render Ultimate Bar
            {
                final int imageWidth = 16;
                final int imageHeight = 16;

                final int barX = (screenWidth - ULT_BAR_WIDTH + imageWidth + shortenName(KeyBindings.INSTANCE.transformButton.getKey().getDisplayName().getString()).length()*5 - 4) / 2; // Center horizontally
                int noOfHealthBars = (int)Math.ceil(((int)Math.ceil(player.getAbsorptionAmount())+(int)Math.ceil(player.getMaxHealth()))/20.0);
                final int barY = screenHeight - Math.min(2*screenHeight/5,((5+noOfHealthBars)*screenHeight/24));

                int barWidth = (int) ( (1.0/(SuperFormHandler.SUPERFORM_DURATION*20)) * (SuperFormHandler.SUPERFORM_DURATION*20-chaosEmeraldCap.superFormTimer) * ULT_BAR_WIDTH);
                guiComponent.fill(barX+1,barY+1, barX+ULT_BAR_WIDTH+1, barY-ULT_BAR_HEIGHT,0xFF000000);
                guiComponent.fill(barX,barY, barX+ULT_BAR_WIDTH, barY-ULT_BAR_HEIGHT,0xFF444444);
                guiComponent.fill(barX,barY, barX+barWidth, barY-ULT_BAR_HEIGHT,0xFFDDDD00);

                // Draw the image
                int imageX = barX - imageWidth - 5; // Image is to the left of the bar
                int imageY = barY - (ULT_BAR_HEIGHT + imageHeight) / 2; // Center the image vertically with the bar
                //Draw the Actual Texture
                guiComponent.blit(HYPER_FORM_ICON_SLOT,imageX,imageY,
                        0,0,imageWidth,imageHeight,imageWidth,imageHeight);

                int keyX = imageX - shortenName(KeyBindings.INSTANCE.transformButton.getKey().getDisplayName().getString()).length()*4 - 4;
                int keyY = imageY + imageHeight/3;

                //Keybinding for Ability
                guiComponent.drawCenteredString(Minecraft.getInstance().font,
                        shortenName(KeyBindings.INSTANCE.transformButton.getKey().getDisplayName().getString()),
                        keyX,
                        keyY,
                        0xFFFFFF);
            }
        });

    }
}