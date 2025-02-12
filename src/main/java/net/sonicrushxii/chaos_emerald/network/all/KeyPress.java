package net.sonicrushxii.chaos_emerald.network.all;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.sonicrushxii.chaos_emerald.KeyBindings;
import net.sonicrushxii.chaos_emerald.block.ChaosBlockItem;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.event_handler.custom.ChaosEmeraldHandler;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;

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
                            {
                                //Get Tick Rate Manager
                                chaosEmeraldCap.timeStop = 1;
                                TickRateManager tickRateManager = player.serverLevel().tickRateManager();
                                tickRateManager.setFrozen(true);
                            }
                            else if (this.keyMapping == KeyBindings.INSTANCE.chaosTeleport.getKey().getValue())
                            {
                                chaosEmeraldCap.teleport = 1;
                            }

                        });

                    }
                });
        ctx.setPacketHandled(true);
    }
}
