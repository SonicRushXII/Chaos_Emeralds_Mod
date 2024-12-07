package net.sonicrushxii.chaos_emerald.network.all;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldCap;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;

import java.util.function.Supplier;

public class EmeraldDataSyncS2C {

    private final int playerId;
    private final ChaosEmeraldCap chaosEmeraldCap;

    public EmeraldDataSyncS2C(int playerId, ChaosEmeraldCap chaosEmeraldCap) {
        this.playerId = playerId;
        this.chaosEmeraldCap = chaosEmeraldCap;
    }

    public EmeraldDataSyncS2C(FriendlyByteBuf buffer) {
        this.playerId = buffer.readInt();
        this.chaosEmeraldCap = new ChaosEmeraldCap();
        CompoundTag nbtData = buffer.readNbt();
        if(nbtData != null)
            chaosEmeraldCap.loadNBTData(nbtData);
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.playerId);
        CompoundTag nbtData = new CompoundTag();
        this.chaosEmeraldCap.saveNBTData(nbtData);
        buffer.writeNbt(nbtData);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()-> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    // This code is run on the client side
                    Minecraft mc = Minecraft.getInstance();
                    if (mc.level == null) return;

                    // Get the player entity by ID
                    Player player = (Player) mc.level.getEntity(this.playerId);
                    if (player == null) return;

                    // Update the player's capability data on the client side
                    player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                        chaosEmeraldCap.copyFrom(this.chaosEmeraldCap);
                    });
                }));
        ctx.get().setPacketHandled(true);
    }
}
