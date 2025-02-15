package net.sonicrushxii.chaos_emerald.potion_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.all.PlayerFrozenDetails;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import net.sonicrushxii.chaos_emerald.network.all.UpdatePositionPacketS2C;

public class PlayerTimeFreeze extends MobEffect {

    public PlayerTimeFreeze(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory,color);
    }

    public static void removeEffect(LivingEntity pLivingEntity)
    {
        //Reset Capability Details
        if(pLivingEntity instanceof Player player && !player.level().isClientSide)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                //If Duration is ending then reset the thing to false
                chaosEmeraldCap.playerFrozenDetails = new PlayerFrozenDetails();
                PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(player.getId(), chaosEmeraldCap));
            });
        }

        //Remove The Effect
        if(pLivingEntity.hasEffect(ModEffects.PLAYER_TIME_FREEZE.get()))
            pLivingEntity.removeEffect(ModEffects.PLAYER_TIME_FREEZE.get());
        if(pLivingEntity.hasEffect(MobEffects.BLINDNESS))
            pLivingEntity.removeEffect(MobEffects.BLINDNESS);
    }


    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier)
    {
        super.applyEffectTick(pLivingEntity,pAmplifier);
        if(pLivingEntity instanceof Player player && !player.level().isClientSide)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                //Get Frozen Details
                PlayerFrozenDetails playerFrozenDetails = chaosEmeraldCap.playerFrozenDetails;

                //If player hasn't been frozen yet Return
                if(!playerFrozenDetails.isFrozen()) return;

                //If Frozen Set Positions
                player.setPos(playerFrozenDetails.frozenPosX,playerFrozenDetails.frozenPosY,playerFrozenDetails.frozenPosZ);
                player.setYRot(playerFrozenDetails.frozenRotY);
                player.setXRot(playerFrozenDetails.frozenRotX);

                //Update on Clientside
                PacketHandler.sendToALLPlayers(
                        new UpdatePositionPacketS2C(
                                new Vec3(playerFrozenDetails.frozenPosX,playerFrozenDetails.frozenPosY,playerFrozenDetails.frozenPosZ),
                                playerFrozenDetails.frozenRotY,
                                playerFrozenDetails.frozenRotX,
                                player.getId()
                        )
                );

                //If Duration is ending then reset the thing to false
                if(player.getEffect(ModEffects.PLAYER_TIME_FREEZE.get()).getDuration() == 1)
                {
                    chaosEmeraldCap.playerFrozenDetails = new PlayerFrozenDetails();
                    PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(player.getId(),chaosEmeraldCap));
                }
            });
        }
    }

    @Override
    public void onEffectStarted(LivingEntity pLivingEntity, int pAmplifier)
    {
        super.onEffectStarted(pLivingEntity, pAmplifier);
        if(pLivingEntity instanceof Player player && !player.level().isClientSide)
        {
            //Initialize the Data
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                //Get Frozen Details
                PlayerFrozenDetails playerFrozenDetails = chaosEmeraldCap.playerFrozenDetails;

                playerFrozenDetails.frozenPosX = player.getX();
                playerFrozenDetails.frozenPosY = player.getY();
                playerFrozenDetails.frozenPosZ = player.getZ();
                playerFrozenDetails.frozenRotX = player.getXRot();
                playerFrozenDetails.frozenRotY = player.getYRot();

                PacketHandler.sendToALLPlayers(new EmeraldDataSyncS2C(player.getId(),chaosEmeraldCap));
            });

            //Set Motion to Zero
            player.setDeltaMovement(0,0,0);
            PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(player.getId(),new Vec3(0,0,0)));
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
