package net.sonicrushxii.chaos_emerald.event_handler.client_specific;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.sonicrushxii.chaos_emerald.KeyBindings;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.KeyPress;

public class ClientTickHandler {

    public static int clientTick = 0;
    private static final int TICKS_PER_SEC = 20;

    public static void clientPlayerTick(Player pPlayer)
    {
        //Local Player Tick
        if(pPlayer instanceof AbstractClientPlayer player)
        {
            clientTick = (clientTick+1)%TICKS_PER_SEC;

            //Timestop
            if(KeyBindings.INSTANCE.chaosTimeStop.consumeClick())
            {
                PacketHandler.sendToServer(new KeyPress(KeyBindings.INSTANCE.chaosTimeStop));
                while(KeyBindings.INSTANCE.chaosTimeStop.consumeClick());
            }

            //Teleport
            if(KeyBindings.INSTANCE.chaosTeleport.consumeClick())
            {
                PacketHandler.sendToServer(new KeyPress(KeyBindings.INSTANCE.chaosTeleport));
                while(KeyBindings.INSTANCE.chaosTeleport.consumeClick());
            }
        }
    }
}
