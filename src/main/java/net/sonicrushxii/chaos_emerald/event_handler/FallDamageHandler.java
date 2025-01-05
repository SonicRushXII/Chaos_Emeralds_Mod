package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;

public class FallDamageHandler {

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if(event.getEntity() instanceof ServerPlayer player)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap->{
                if(chaosEmeraldCap.greyChaosUse != 0)
                    event.setDistance(0.0f);

                if(chaosEmeraldCap.purpleChaosUse != 0)
                    event.setDistance(0.0f);

                if(player.hasEffect(ModEffects.CHAOS_DASH_ATTACK.get()) && player.getEffect(ModEffects.CHAOS_DASH_ATTACK.get()).getDuration() > 0)
                    event.setDistance(0.0f);

                if(player.hasEffect(ModEffects.CHAOS_FLAME_JUMP.get()) && player.getEffect(ModEffects.CHAOS_FLAME_JUMP.get()).getDuration() > 0)
                    event.setDistance(0.0f);

                if(chaosEmeraldCap.falseSuperTimer > 0)
                    event.setDistance(0.0f);

                if(player.hasEffect(ModEffects.SUPER_ICE_LAUNCH.get()) && player.getEffect(ModEffects.SUPER_ICE_LAUNCH.get()).getDuration() > 0)
                    event.setDistance(0.0f);

                if(player.hasEffect(ModEffects.SUPER_CHAOS_DIVE.get()) && player.getEffect(ModEffects.SUPER_CHAOS_DIVE.get()).getDuration() > 0)
                    event.setDistance(0.0f);

                if(player.hasEffect(ModEffects.SUPER_FALLDMG_EFFECT.get()) && player.getEffect(ModEffects.SUPER_FALLDMG_EFFECT.get()).getDuration() > 0)
                    event.setDistance(0.0f);

                if(player.hasEffect(ModEffects.HYPER_FALLDMG_EFFECT.get()) && player.getEffect(ModEffects.HYPER_FALLDMG_EFFECT.get()).getDuration() > 0)
                    event.setDistance(0.0f);
            });
        }
    }
}
