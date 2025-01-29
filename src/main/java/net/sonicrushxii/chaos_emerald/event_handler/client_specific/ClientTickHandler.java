package net.sonicrushxii.chaos_emerald.event_handler.client_specific;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.sonicrushxii.chaos_emerald.event_handler.custom.*;

public class ClientTickHandler {

    public static int clientTick = 0;
    private static final int TICKS_PER_SEC = 20;

    public static void clientPlayerTick(Player pPlayer)
    {
        //Local Player Tick
        if(pPlayer instanceof AbstractClientPlayer player)
        {
            clientTick = (clientTick+1)%TICKS_PER_SEC;

            //Handles Chaos Emeralds
            ChaosEmeraldHandler.clientTick(player,clientTick);

            //False Super Form
            FalseSuperHandler.clientTick(player,clientTick);

            //Handle Super Emeralds
            SuperEmeraldHandler.clientTick(player,clientTick);

            //Super Form
            SuperFormHandler.clientTick(player,clientTick);

            //Hyper Form
            HyperFormHandler.clientTick(player,clientTick);
        }
    }
}
