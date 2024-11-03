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
                if(chaosEmeraldCap.greyEmeraldUse != 0)
                    event.setDistance(0.0f);

                if(chaosEmeraldCap.purpleEmeraldUse != 0)
                    event.setDistance(0.0f);

                if(player.hasEffect(ModEffects.CHAOS_DASH_ATTACK.get()) && player.getEffect(ModEffects.CHAOS_DASH_ATTACK.get()).getDuration() > 0)
                    event.setDistance(0.0f);

                if(player.hasEffect(ModEffects.CHAOS_FLAME_JUMP.get()) && player.getEffect(ModEffects.CHAOS_FLAME_JUMP.get()).getDuration() > 0)
                    event.setDistance(0.0f);
            });
        }
    }
}
