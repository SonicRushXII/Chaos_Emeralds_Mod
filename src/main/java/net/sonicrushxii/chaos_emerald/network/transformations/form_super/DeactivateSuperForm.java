package net.sonicrushxii.chaos_emerald.network.transformations.form_super;

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
import net.sonicrushxii.chaos_emerald.event_handler.custom.SuperFormHandler;
import net.sonicrushxii.chaos_emerald.modded.ModBlocks;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import net.sonicrushxii.chaos_emerald.potion_effects.AttributeMultipliers;

import java.util.function.Supplier;

public class DeactivateSuperForm
{
    public DeactivateSuperForm() {}

    public DeactivateSuperForm(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public static void performDeactivateSuper(ServerPlayer player)
    {
        //Flash Effect
        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                ParticleTypes.FLASH,
                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                0.001, 0.01F, 0.01F, 0.01F, 1, true));

        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            chaosEmeraldCap.superFormTimer = 0;

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
                if (player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).hasModifier(AttributeMultipliers.SUPER_STEP_ADDITION))
                    player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).removeModifier(AttributeMultipliers.SUPER_STEP_ADDITION);

                //Return Armor
                if (player.getAttribute(Attributes.ARMOR).hasModifier(AttributeMultipliers.SUPER_ARMOR))
                    player.getAttribute(Attributes.ARMOR).removeModifier(AttributeMultipliers.SUPER_ARMOR);

                //Return Knockback Resistance
                if (player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).hasModifier(AttributeMultipliers.SUPER_KB_RESIST))
                    player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(AttributeMultipliers.SUPER_KB_RESIST);

                //Return Speed
                if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.SUPER_BOOST_SPEED))
                    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(AttributeMultipliers.SUPER_BOOST_SPEED);
                if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.SUPER_WALK_SPEED))
                    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(AttributeMultipliers.SUPER_WALK_SPEED);

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
                if (!player.hasEffect(ModEffects.SUPER_FALLDMG_EFFECT.get()))
                    player.addEffect(new MobEffectInstance(ModEffects.SUPER_FALLDMG_EFFECT.get(), 300, 0, false, false));
                else
                    player.getEffect(ModEffects.SUPER_FALLDMG_EFFECT.get()).update(new MobEffectInstance(ModEffects.SUPER_FALLDMG_EFFECT.get(), 300, 0, false, false));
            }

            //Cooldown
            chaosEmeraldCap.superFormCooldown = SuperFormHandler.SUPERFORM_COOLDOWN;

            //Reset Data
            chaosEmeraldCap.formProperties = new FormProperties();
        });
    }

    public static void giveBackChaosEmeralds(Player player)
    {
        if (!player.getInventory().add(new ItemStack(ModBlocks.AQUA_CHAOS_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.AQUA_CHAOS_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.BLUE_CHAOS_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.BLUE_CHAOS_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.GREEN_CHAOS_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.GREEN_CHAOS_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.GREY_CHAOS_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.GREY_CHAOS_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.PURPLE_CHAOS_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.PURPLE_CHAOS_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.RED_CHAOS_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.RED_CHAOS_EMERALD.get().asItem()), false);
        if (!player.getInventory().add(new ItemStack(ModBlocks.YELLOW_CHAOS_EMERALD.get().asItem())))
            player.drop(new ItemStack(ModBlocks.YELLOW_CHAOS_EMERALD.get().asItem()), false);
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
                            if(chaosEmeraldCap.falseSuperTimer < 0 || chaosEmeraldCap.hyperFormTimer != 0) return;

                            performDeactivateSuper(player);

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
