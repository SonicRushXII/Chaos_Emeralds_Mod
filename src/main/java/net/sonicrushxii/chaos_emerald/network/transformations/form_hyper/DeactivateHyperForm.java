package net.sonicrushxii.chaos_emerald.network.transformations.form_hyper;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.all.FormProperties;
import net.sonicrushxii.chaos_emerald.event_handler.custom.HyperFormHandler;
import net.sonicrushxii.chaos_emerald.event_handler.custom.SuperFormHandler;
import net.sonicrushxii.chaos_emerald.modded.ModBlocks;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import net.sonicrushxii.chaos_emerald.potion_effects.AttributeMultipliers;

import java.util.function.Supplier;

public class DeactivateHyperForm
{
    public DeactivateHyperForm() {}

    public DeactivateHyperForm(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public static void performDeactivateHyper(ServerPlayer player)
    {
        //Flash Effect
        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                ParticleTypes.FLASH,
                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                0.001, 0.01F, 0.01F, 0.01F, 1, true));

        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            chaosEmeraldCap.hyperFormTimer = 0;

            //Return Flight Capability back to normal
            GameType currentGameMode = player.gameMode.getGameModeForPlayer();
            switch (currentGameMode) {
                case SPECTATOR:
                case CREATIVE:
                    player.getAbilities().mayfly = true;
                    break;
                case SURVIVAL:
                case ADVENTURE:
                default:
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
            }
            player.onUpdateAbilities();

            //Remove Effects
            {
                //Return Step Height back to normal
                if (player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).hasModifier(AttributeMultipliers.HYPER_STEP_ADDITION))
                    player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).removeModifier(AttributeMultipliers.HYPER_STEP_ADDITION);

                //Return Armor
                if (player.getAttribute(Attributes.ARMOR).hasModifier(AttributeMultipliers.HYPER_ARMOR))
                    player.getAttribute(Attributes.ARMOR).removeModifier(AttributeMultipliers.HYPER_ARMOR);

                //Return Knockback Resistance
                if (player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).hasModifier(AttributeMultipliers.HYPER_KB_RESIST))
                    player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(AttributeMultipliers.HYPER_KB_RESIST);

                //Return Speed
                if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.HYPER_BOOST_SPEED))
                    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(AttributeMultipliers.HYPER_BOOST_SPEED);
                if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.HYPER_WALK_SPEED))
                    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(AttributeMultipliers.HYPER_WALK_SPEED);

                //Remove Effects
                {
                    player.removeEffect(MobEffects.MOVEMENT_SPEED);      //Speed
                    player.removeEffect(MobEffects.JUMP);                //Jump
                    player.removeEffect(MobEffects.DIG_SPEED);           //Haste
                    player.removeEffect(MobEffects.DAMAGE_BOOST);        //Strength
                    player.removeEffect(MobEffects.DAMAGE_RESISTANCE);   //Resistance
                    player.removeEffect(MobEffects.FIRE_RESISTANCE);     //Fire Resistance
                    player.removeEffect(MobEffects.HERO_OF_THE_VILLAGE); //Hero Of The Village
                }

                //Pad Effect
                if (!player.hasEffect(ModEffects.HYPER_FALLDMG_EFFECT.get()))
                    player.addEffect(new MobEffectInstance(ModEffects.HYPER_FALLDMG_EFFECT.get(), 300, 0, false, false));
                else
                    player.getEffect(ModEffects.HYPER_FALLDMG_EFFECT.get()).update(new MobEffectInstance(ModEffects.HYPER_FALLDMG_EFFECT.get(), 300, 0, false, false));
            }

            //Cooldown
            chaosEmeraldCap.hyperFormCooldown = HyperFormHandler.HYPERFORM_COOLDOWN;

            //Reset Data
            chaosEmeraldCap.formProperties = new FormProperties();
        });
    }

    public static void giveBackSuperEmeralds(Player player)
    {
        if (!player.getInventory().add(new ItemStack(ModBlocks.AQUA_SUPER_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.AQUA_SUPER_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.BLUE_SUPER_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.BLUE_SUPER_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.GREEN_SUPER_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.GREEN_SUPER_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.GREY_SUPER_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.GREY_SUPER_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.PURPLE_SUPER_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.PURPLE_SUPER_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.RED_SUPER_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.RED_SUPER_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.YELLOW_SUPER_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.YELLOW_SUPER_EMERALD.get().asItem()), false);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();
                    //Check if Standing on the Master Emerald
                    if(player != null)
                    {
                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            //Don't Play if any other Transformations are going on
                            if(chaosEmeraldCap.falseSuperTimer < 0 || chaosEmeraldCap.superFormTimer != 0) return;

                            performDeactivateHyper(player);

                            //Emerald Data
                            PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(
                                    player.getId(),chaosEmeraldCap
                            ));
                        });
                    }
                });
        ctx.get().setPacketHandled(true);
    }
}
