package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.common.ForgeMod;
import net.sonicrushxii.chaos_emerald.KeyBindings;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import net.sonicrushxii.chaos_emerald.network.transformations.form_super.ActivateSuperForm;
import org.joml.Vector3f;

public class SuperFormHandler
{
    public static final int SUPERFORM_DURATION = 3; //SECONDS

    public static void serverTick(ServerPlayer player, int tick)
    {
        //Server Tick
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            //False Super Handler
            if (chaosEmeraldCap.superFormTimer != 0) {
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

                    //Flash Effects
                    if(chaosEmeraldCap.superFormTimer == -4)
                    {
                        //Play the Sound
                        player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.MASTER, 0.75f, 1.0f);

                        //Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.FLASH,
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.01F, 0.01F, 0.01F, 1, true));
                    }
                }

                //Activate Super Form
                if(chaosEmeraldCap.superFormTimer == 0)
                {
                    chaosEmeraldCap.superFormTimer = 1;

                    //Return Gravity
                    player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08);
                }

                //Super Form Duration
                if(chaosEmeraldCap.superFormTimer > 0)
                {
                    //Display Particle Every Tick
                    PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                            new DustParticleOptions(new Vector3f(1.0F, 1.0F, 0.0F),1.0F),
                            player.getX(),player.getY()+player.getEyeHeight()/2,player.getZ(),
                            0.001,0.5F,player.getEyeHeight()/2,0.5F,2,true));
                }

                //Super Form End
                if(chaosEmeraldCap.superFormTimer > 20*SUPERFORM_DURATION)
                {
                    chaosEmeraldCap.superFormTimer = 0;

                }
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
            if(KeyBindings.INSTANCE.transformButton.isDown() && chaosEmeraldCap.superFormTimer == 0)
            {
                if(ActivateSuperForm.hasAllChaosEmeralds(player)) {
                    PacketHandler.sendToServer(new ActivateSuperForm());
                    player.setDeltaMovement(0,0,0);
                }
            }

            //Lock Position
            if(chaosEmeraldCap.superFormTimer < 0)  player.setDeltaMovement(0,0,0);
        });
    }
}
