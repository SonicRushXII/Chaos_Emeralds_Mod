package net.sonicrushxii.chaos_emerald.network.all;

import net.minecraft.client.KeyMapping;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.sonicrushxii.chaos_emerald.KeyBindings;
import net.sonicrushxii.chaos_emerald.block.ChaosBlockItem;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.network.common.ChaosTeleport;
import net.sonicrushxii.chaos_emerald.network.common.TimeStop;

public class KeyPress {
    int keyMapping;

    public KeyPress(KeyMapping keyMapping)
    {
        this.keyMapping = keyMapping.getKey().getValue();
    }

    public KeyPress(FriendlyByteBuf buffer) {
        this.keyMapping = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.keyMapping);
    }

    public void handle(CustomPayloadEvent.Context ctx){
        ctx.enqueueWork(
                ()->{
                    ServerPlayer player = ctx.getSender();
                    if(player != null)
                    {
                        //Check if holding Chaos Emerald
                        if(!ChaosBlockItem.isHoldingChaosEmerald(player)) return;

                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            //Time Stop
                            if (this.keyMapping == KeyBindings.INSTANCE.chaosTimeStop.getKey().getValue())
                                TimeStop.keyPress(player);

                            //Teleport
                            else if (this.keyMapping == KeyBindings.INSTANCE.chaosTeleport.getKey().getValue())
                                ChaosTeleport.keyPress(player);

                        });

                    }
                });
        ctx.setPacketHandled(true);
    }
}
