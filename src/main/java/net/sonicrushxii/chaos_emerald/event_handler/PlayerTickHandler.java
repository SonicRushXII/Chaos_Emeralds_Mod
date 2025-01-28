package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.event_handler.client_specific.ClientTickHandler;
import net.sonicrushxii.chaos_emerald.event_handler.custom.*;

public class PlayerTickHandler {
    private static final int TICKS_PER_SEC = 20;
    private static int serverTick = 0;


    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END || event.player == null) return;
        if (FMLEnvironment.dist == Dist.CLIENT && event.player.level().isClientSide()) ClientTickHandler.onLocalPlayerTick(event.player);
        else onServerPlayerTick((ServerPlayer)event.player);

        Player player = event.player;

        //Handle cooldowns for all players
        if(serverTick == 0)
            event.player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                for(byte i = 0; i< EmeraldType.values().length; ++i) {
                    chaosEmeraldCap.chaosCooldownKey[i] = (byte) Math.max(0, chaosEmeraldCap.chaosCooldownKey[i] - 1);
                    chaosEmeraldCap.superCooldownKey[i] = (byte) Math.max(0, chaosEmeraldCap.superCooldownKey[i] - 1);
                }
            });
    }

    public void onServerPlayerTick(ServerPlayer player)
    {
        //Handles Tick
        serverTick = (serverTick+1)%TICKS_PER_SEC;

        //Handle Chaos Emeralds
        ChaosEmeraldHandler.serverTick(player,serverTick);

        //False Super Form
        FalseSuperHandler.serverTick(player,serverTick);

        //Handle Super Emeralds
        SuperEmeraldHandler.serverTick(player,serverTick);

        //Super Form
        SuperFormHandler.serverTick(player,serverTick);

        //Hyper Form
        HyperFormHandler.serverTick(player,serverTick);

    }
}
