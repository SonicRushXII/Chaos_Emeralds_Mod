package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.event_handler.client_specific.ClientTickHandler;
import net.sonicrushxii.chaos_emerald.event_handler.custom.ChaosEmeraldHandler;

public class PlayerTickHandler {

    private static final int TICKS_PER_SEC = 20;
    private static int serverTick = 0;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent.Pre event)
    {
        if (FMLEnvironment.dist == Dist.CLIENT && event.player.level().isClientSide())
            ClientTickHandler.clientPlayerTick(event.player);
        else
            onServerPlayerTick((ServerPlayer)event.player);
    }

    public void onServerPlayerTick(ServerPlayer player)
    {
        //Handles Tick
        serverTick = (serverTick+1)%TICKS_PER_SEC;

        //Chaos Emerald Handling
        ChaosEmeraldHandler.serverTick(player,serverTick);
    }
}
