package net.sonicrushxii.chaos_emerald.potion_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.EmeraldDataSyncS2C;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import net.sonicrushxii.chaos_emerald.network.all.UpdatePositionPacketS2C;

public class PlayerTimeFreeze extends MobEffect {

    public PlayerTimeFreeze(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier)
    {
        super.applyEffectTick(pLivingEntity,pAmplifier);
        if(pLivingEntity instanceof Player player && !player.level().isClientSide)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                //If player hasn't been frozen yet Return
                if(!chaosEmeraldCap.playerIsFrozen) return;

                //If Frozen Set Positions
                player.setPos(chaosEmeraldCap.playerFrozenX,chaosEmeraldCap.playerFrozenY,chaosEmeraldCap.playerFrozenZ);
                player.setYRot(chaosEmeraldCap.atkRotPhaseY);
                player.setXRot(chaosEmeraldCap.atkRotPhaseX);

                //Update on Clientside
                PacketHandler.sendToALLPlayers(
                        new UpdatePositionPacketS2C(
                                new Vec3(chaosEmeraldCap.playerFrozenX,chaosEmeraldCap.playerFrozenY,chaosEmeraldCap.playerFrozenZ),
                                chaosEmeraldCap.atkRotPhaseY,
                                chaosEmeraldCap.atkRotPhaseX,
                                player.getId()
                        )
                );

                //If Duration is ending then reset the thing to false
                if(player.getEffect(ModEffects.PLAYER_TIME_FREEZE.get()).getDuration() == 1) {
                    chaosEmeraldCap.playerIsFrozen = false;
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
                chaosEmeraldCap.playerFrozenX = player.getX();
                chaosEmeraldCap.playerFrozenY = player.getY();
                chaosEmeraldCap.playerFrozenZ = player.getZ();
                chaosEmeraldCap.atkRotPhaseX = player.getXRot();
                chaosEmeraldCap.atkRotPhaseY = player.getYRot();
                chaosEmeraldCap.playerIsFrozen = true;

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
