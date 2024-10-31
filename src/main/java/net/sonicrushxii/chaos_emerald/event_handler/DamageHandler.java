package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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

        }

        /** Player: Attacker*/
        try{
            if(event.getSource().getEntity() instanceof ServerPlayer player)
            {

            }
        }catch(NullPointerException ignored){}
    }
}
