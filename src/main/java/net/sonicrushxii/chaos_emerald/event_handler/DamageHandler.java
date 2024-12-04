package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.aqua.BindEffectSyncPacketS2C;

public class DamageHandler {
    @SubscribeEvent
    public void onPlayerDamaged(LivingAttackEvent event)
    {
        /** Entity: Attack*/
        LivingEntity enemy = event.getEntity();
        if(enemy.hasEffect(ModEffects.CHAOS_BIND.get()) && enemy.getEffect(ModEffects.CHAOS_BIND.get()).getDuration() > 0)
        {
            event.setCanceled(true);
            enemy.removeEffect(ModEffects.CHAOS_BIND.get());
            PacketHandler.sendToALLPlayers(new BindEffectSyncPacketS2C(enemy.getId(),0));
        }

        /** Player Attacked*/
        if(event.getEntity() instanceof ServerPlayer player)
        {
            //Heavily Reduce Damage if using Chaos Blast
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                //Green Emerald
                if(player.hasEffect(ModEffects.CHAOS_DASH_ATTACK.get()) && player.getEffect(ModEffects.CHAOS_DASH_ATTACK.get()).getDuration() > 0
                        && !(event.getSource().getEntity() instanceof Player) && !event.getSource().isIndirect())
                    event.setCanceled(true);

                //Purple Emerald
                if(chaosEmeraldCap.purpleChaosUse > 1 && !(event.getSource().getEntity() instanceof LivingEntity))
                    event.setCanceled(true);

                //False Super Form - (Turn Invulnerable while transforming)
                if(chaosEmeraldCap.falseChaosSpaz < 0)
                    event.setCanceled(true);

            });
        }

        /** Player: Attacker*/
        try{
            if(event.getSource().getEntity() instanceof ServerPlayer player)
            {

            }
        }catch(NullPointerException ignored){}
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event)
    {
        //Reset all form Timers
        if(event.getEntity() instanceof ServerPlayer player)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                chaosEmeraldCap.falseSuperTimer = 0;
                chaosEmeraldCap.superFormTimer = 0;
                chaosEmeraldCap.hyperFormTimer = 0;
            });
        }
    }
}
