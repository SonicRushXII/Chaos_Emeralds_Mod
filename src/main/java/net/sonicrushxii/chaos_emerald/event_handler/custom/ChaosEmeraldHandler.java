package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldAbility;
import net.sonicrushxii.chaos_emerald.capabilities.all.ChaosAbilityDetails;
import net.sonicrushxii.chaos_emerald.modded.ModSounds;
import net.sonicrushxii.chaos_emerald.modded.ModTeleporter;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.ParticleAuraPacketS2C;
import net.sonicrushxii.chaos_emerald.network.common.ChaosTeleport;
import net.sonicrushxii.chaos_emerald.network.common.TimeStop;

import java.util.Arrays;
import java.util.Collections;

public class ChaosEmeraldHandler
{
    //Time Stop
    public static final byte TIME_STOP_BUILDUP = 20; //In Ticks
    public static final byte TIME_STOP_DURATION = 15; // In Seconds
    public static final byte TIME_STOP_COOLDOWN = 2; // In Seconds

    //Teleport
    public static final byte TELEPORT_BUILDUP = 20; //In Ticks
    public static final byte TELEPORT_DURATION = 10; // In Seconds
    public static final byte TELEPORT_COOLDOWN = 1; // In Seconds

    //Dimension Teleport
    public static final byte DIMENSION_TP_BUILDUP = 20; //In Ticks
    public static final byte DIMENSION_TP_COOLDOWN = 5; // In Seconds

    public static void serverTick(ServerPlayer player, int tick)
    {
        Level world = player.level();

        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap ->
        {
            //Fetch Ability Properties
            ChaosAbilityDetails chaosAbilities = chaosEmeraldCap.chaosAbilityDetails;

            //Time Stop
            {
                //Buildup
                if(chaosAbilities.timeStop < 0)
                {
                    chaosAbilities.timeStop += 1;

                    //Particle
                    {
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.ELECTRIC_SPARK,
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.55F, player.getEyeHeight() / 2, 0.55F,
                                5, false));
                        //Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                new DustParticleOptions(Utilities.hexToVector3f(chaosAbilities.useColor), 1.5f),
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.55F, player.getEyeHeight() / 2, 0.55F,
                                1, true)
                        );
                    }

                    //On Use
                    if(chaosAbilities.timeStop == 1- TIME_STOP_BUILDUP)
                    {
                        //Play Sound
                        world.playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.MASTER, 1.0f, 1.0f);

                        //Slow Down Player
                        MobEffectInstance slowEffect = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, TIME_STOP_BUILDUP-1, 2, false, false, false);
                        if(player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN))
                            player.getEffect(MobEffects.MOVEMENT_SLOWDOWN).update(slowEffect);
                        else
                            player.addEffect(slowEffect);

                        //Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.FLASH,
                                player.getX(),player.getY()+player.getEyeHeight()/2,player.getZ(),
                                0.001,0.01F,0.01F,0.01F,
                                1,true));
                    }

                    //Stopping Time
                    if(chaosAbilities.timeStop == 0)
                    {
                        //Blind
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 0, false, false));

                        //Play Sound
                        world.playSound(null,player.getX(),player.getY(),player.getZ(), ModSounds.CHAOS_CONTROL_TIME_STOP.get(), SoundSource.MASTER, 1.0f, 1.0f);

                        //Set Data
                        chaosAbilities.timeStop = 1;

                        //Actual Time Stop
                        TimeStop.startTimeStop(player);
                    }

                }

                //Increment Timer
                if (chaosAbilities.timeStop > 0)
                {
                    //Per Tick

                    //Per Second
                    if (tick == 0)
                        chaosAbilities.timeStop += 1;

                    //End Ability
                    if (chaosAbilities.timeStop > TIME_STOP_DURATION)
                        TimeStop.endTimeStop(player);
                }
            }

            //Teleport
            {
                //Buildup
                if(chaosAbilities.teleport < 0)
                {
                    chaosAbilities.teleport += 1;

                    //Particle
                    {
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.ELECTRIC_SPARK,
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.55F, player.getEyeHeight() / 2, 0.55F,
                                5, false));
                        //Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                new DustParticleOptions(Utilities.hexToVector3f(chaosAbilities.useColor), 1.5f),
                                player.getX(), player.getY() + player.getEyeHeight() / 2, player.getZ(),
                                0.001, 0.55F, player.getEyeHeight() / 2, 0.55F,
                                1, true)
                        );
                    }

                    //On Use
                    if(chaosAbilities.teleport == 1-TELEPORT_BUILDUP)
                    {
                        //Play Sound
                        world.playSound(null,player.getX(),player.getY(),player.getZ(), ModSounds.CHAOS_CONTROL_TELEPORT_START.get(), SoundSource.MASTER, 1.0f, 1.0f);

                        //Slow Down Player
                        MobEffectInstance slowEffect = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, TELEPORT_BUILDUP-1, 2, false, false, false);
                        if(player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN))
                            player.getEffect(MobEffects.MOVEMENT_SLOWDOWN).update(slowEffect);
                        else
                            player.addEffect(slowEffect);

                        //Particle
                        PacketHandler.sendToALLPlayers(new ParticleAuraPacketS2C(
                                ParticleTypes.FLASH,
                                player.getX(),player.getY()+player.getEyeHeight()/2,player.getZ(),
                                0.001,0.01F,0.01F,0.01F,
                                1,true));
                    }

                    //Teleporting
                    if(chaosAbilities.teleport == 0)
                    {
                        //Blind
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 0, false, false));

                        //Set Data
                        chaosAbilities.teleport = 1;

                        //Actual Time Stop
                        ChaosTeleport.startTeleport(player);
                    }

                }

                //Increment Timer
                if (chaosAbilities.teleport > 0)
                {
                    //Per Tick

                    //Per Second
                    if (tick == 0)
                        chaosAbilities.teleport += 1;

                    //End Ability
                    if (chaosAbilities.teleport > TELEPORT_DURATION)
                        ChaosTeleport.endTeleport(player);
                }
            }

            //Dimension Teleport
            {
                //Increment Counter
                if(chaosAbilities.dimTeleport > 0)
                    chaosAbilities.dimTeleport += 1;

                //Once Built Up Teleport to the Nether
                if(chaosAbilities.dimTeleport > DIMENSION_TP_BUILDUP)
                {
                    //Set Data
                    chaosAbilities.dimTeleport = 0;
                    chaosEmeraldCap.chaosCooldownKey[EmeraldAbility.CHAOS_CONTROL.ordinal()] = DIMENSION_TP_COOLDOWN;

                    //Actual Teleportation
                    {
                        //Set Target Dimension and Coordinates
                        ServerLevel destinationWorld = player.getServer().getLevel(ResourceKey.create(Registries.DIMENSION,
                                new ResourceLocation(chaosAbilities.targetDimension)));

                        int[] currentDimPositions = chaosAbilities.previousDimensionPos.clone();

                        chaosAbilities.targetDimension = String.valueOf(player.level().dimension().location());
                        chaosAbilities.previousDimensionPos[0] = player.getBlockX();
                        chaosAbilities.previousDimensionPos[1] = player.getBlockY();
                        chaosAbilities.previousDimensionPos[2] = player.getBlockZ();
                        chaosAbilities.previousDimensionPos[3] = (int) player.getYRot();
                        chaosAbilities.previousDimensionPos[4] = (int) player.getXRot();

                        //Change Dimensions
                        assert destinationWorld != null;
                        player.changeDimension(destinationWorld, new ModTeleporter(new BlockPos(0,0,0), false));
                        player.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);

                        //Get Slowfalling
                        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20, 0, false, false, false), player);

                        //Teleport to the Position(if one was specified)
                        if(!Arrays.equals(currentDimPositions,new int[]{0,0,0,0,0}))
                        {
                            player.teleportTo(
                                    destinationWorld,
                                    currentDimPositions[0],
                                    currentDimPositions[1],
                                    currentDimPositions[2],
                                    Collections.emptySet(),
                                    currentDimPositions[3],
                                    currentDimPositions[4]
                            );
                            player.connection.send(new ClientboundTeleportEntityPacket(player));
                        }
                        else
                        {
                            String command = "spreadplayers 0 0 10 50 under 120 false " + player.getScoreboardName();
                            player.server.getCommands().performPrefixedCommand(player.createCommandSourceStack().withSuppressedOutput(), command);
                        }
                    }

                }
            }

            PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(player.getId(),chaosEmeraldCap));
        });
    }
}
