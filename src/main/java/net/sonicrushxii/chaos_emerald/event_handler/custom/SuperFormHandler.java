package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.sonicrushxii.chaos_emerald.KeyBindings;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.modded.ModBlocks;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import net.sonicrushxii.chaos_emerald.network.transformations.form_super.ActivateSuperForm;
import net.sonicrushxii.chaos_emerald.network.transformations.form_super.DeactivateSuperForm;
import net.sonicrushxii.chaos_emerald.potion_effects.AttributeMultipliers;
import org.joml.Vector3f;

public class SuperFormHandler
{
    public static final int SUPERFORM_DURATION = 300; //SECONDS
    public static final int SUPERFORM_COOLDOWN = 10; //SECONDS

    public static void serverTick(ServerPlayer player, int tick)
    {
        //Server Tick
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            //Super Form Handler
            if (chaosEmeraldCap.superFormTimer != 0)
            {
                //Increase Timer
                chaosEmeraldCap.superFormTimer += 1;

                //Transformation Animation
                if(chaosEmeraldCap.superFormTimer < 0)
                {
                    //Lock in Place
                    player.setDeltaMovement(0,0,0);

                    //Display Particle
                    PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                            ParticleTypes.ELECTRIC_SPARK,
                            player.getX(),player.getY()+player.getEyeHeight()/2,player.getZ(),
                            0.001,0.5F,player.getEyeHeight()/2,0.5F,1,false));

                    //Activation Point
                    if(chaosEmeraldCap.superFormTimer == -4)
                    {
                        //Play the Sound
                        player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.MASTER, 0.75f, 1.0f);

                        //Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.FLASH,
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.01F, 0.01F, 0.01F, 1, true));

                        //Give Potion Effects
                        ActivateSuperForm.giveEffects(player);
                    }

                    //Flight Effects
                    {
                        //Flight
                        player.getAbilities().mayfly = true;
                        player.getAbilities().flying = !player.onGround();
                        player.onUpdateAbilities();
                    }

                    //Return Gravity
                    player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08);
                }

                //Activate Super Form
                if(chaosEmeraldCap.superFormTimer == 0)
                {
                    chaosEmeraldCap.superFormTimer = 1;


                }

                //Super Form Duration
                if(chaosEmeraldCap.superFormTimer > 0)
                {
                    //Display Particle Every Tick
                    PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                            new DustParticleOptions(new Vector3f(1.0F, 1.0F, 0.0F),1.0F),
                            player.getX(),player.getY()+player.getEyeHeight()/2,player.getZ(),
                            0.001,0.5F,player.getEyeHeight()/2,0.5F,2,true));
                    //Handle Flight
                    if(player.getAbilities().flying && player.isSprinting())
                    {
                        //Move in Direction you are looking
                        Vec3 lookAngle = player.getLookAngle().scale(1.75);
                        player.setDeltaMovement(lookAngle);
                        PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(),lookAngle));
                    }
                    //Re-enable Flight if it isn't enabled
                    if(tick == 0 && !player.getAbilities().mayfly)
                    {
                        player.getAbilities().mayfly = true;
                        player.onUpdateAbilities();
                    }

                    //Give the Player the Effects every Second
                    if(tick == 0)
                    {
                        //Display The Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.END_ROD,
                                player.getX(),player.getY()+player.getEyeHeight()/2,player.getZ(),
                                0.001,0.5F,player.getEyeHeight()/2,0.5F,3,false));

                        ActivateSuperForm.giveEffects(player);
                    }

                    //Sprint Boost
                    if(player.isSprinting()) {
                        if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.SUPER_SPEED))
                            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(AttributeMultipliers.SUPER_SPEED);
                    }
                    else {
                        if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(AttributeMultipliers.SUPER_SPEED))
                            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(AttributeMultipliers.SUPER_SPEED);
                    }

                }

                //Super Form End
                if(chaosEmeraldCap.superFormTimer > 20*SUPERFORM_DURATION)
                    DeactivateSuperForm.performDeactivateSuper(player);
            }

            //Decrement Super Cooldown
            if(tick == 0)
            {
                //Give Back Chaos Emeralds
                if(chaosEmeraldCap.superFormCooldown == 1)  DeactivateSuperForm.giveBackChaosEmeralds(player);
                chaosEmeraldCap.superFormCooldown = Math.max(chaosEmeraldCap.superFormCooldown - 1, 0);
            }

            //Sync Data to Client
            PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(
                    player.getId(),chaosEmeraldCap
            ));
        });
    }

    public static void clientTick(LocalPlayer player, int clientTick)
    {
        //Sends a Packet To Activate Super form if you have all Seven Emeralds.
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            //Transform
            if(KeyBindings.INSTANCE.transformButton.isDown() && chaosEmeraldCap.superFormTimer == 0 && chaosEmeraldCap.superFormCooldown == 0)
            {
                if(ActivateSuperForm.hasAllChaosEmeralds(player) && ActivateSuperForm.isPlayerNotWearingArmor(player)) {
                    PacketHandler.sendToServer(new ActivateSuperForm());
                    player.setDeltaMovement(0,0,0);
                }
            }

            //DeTransform
            if(KeyBindings.INSTANCE.transformButton.isDown() && (chaosEmeraldCap.superFormTimer > 0 && chaosEmeraldCap.superFormTimer < SUPERFORM_DURATION*20) && chaosEmeraldCap.superFormCooldown == 0)
            {
                PacketHandler.sendToServer(new DeactivateSuperForm());
            }

            //Lock Position
            if(chaosEmeraldCap.superFormTimer < 0)  player.setDeltaMovement(0,0,0);
        });
    }
}
