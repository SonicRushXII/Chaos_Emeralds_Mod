package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.TickRateManager;
import net.sonicrushxii.chaos_emerald.KeyBindings;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;

public class ChaosEmeraldHandler
{
    private static final byte TIME_STOP_DURATION = 5;

    public static void serverTick(ServerPlayer player, int tick)
    {
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if (chaosEmeraldCap.timeStop > 0)
            {
                //Per Tick

                //Per Second
                if(tick == 0){
                    chaosEmeraldCap.timeStop += 1;
                    System.out.println(chaosEmeraldCap.timeStop+" Seconds have passed!");
                }

                //End Ability
                if(chaosEmeraldCap.timeStop > TIME_STOP_DURATION)
                {
                    chaosEmeraldCap.timeStop = 0;
                    player.serverLevel().tickRateManager().setFrozen(false);
                }
            }
            if (chaosEmeraldCap.teleport > 0)
            {
                System.out.println("Chaos Teleport!");
                chaosEmeraldCap.teleport = 0;
            }
        });
    }
}
