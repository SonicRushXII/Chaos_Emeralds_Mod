package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.transformations.false_super.ChaosSpaz;

public class InteractionHandler {
    @SubscribeEvent
    public void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        Player player = event.getEntity();
        Level world = event.getLevel();

        //If Main Hand
        if(event.getHand() == InteractionHand.MAIN_HAND) PacketHandler.sendToServer(new ChaosSpaz());
    }
}
