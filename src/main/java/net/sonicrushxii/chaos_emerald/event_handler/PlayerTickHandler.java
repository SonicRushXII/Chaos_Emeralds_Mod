package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerTickHandler {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent.Pre event)
    {
        if (event.player == null) return;
        if (event.player.level().isClientSide()) onLocalPlayerTick((LocalPlayer)event.player);
        else onServerPlayerTick((ServerPlayer)event.player);
    }

    public void onLocalPlayerTick(LocalPlayer player)
    {

    }

    public void onServerPlayerTick(ServerPlayer player)
    {

    }
}
