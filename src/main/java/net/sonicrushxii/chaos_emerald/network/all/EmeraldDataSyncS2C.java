package net.sonicrushxii.chaos_emerald.network.all;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldCap;
import net.sonicrushxii.chaos_emerald.event_handler.client_specific.ClientPacketHandler;

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

    public void handle(CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientPacketHandler.clientDataSync(this.playerId,this.chaosEmeraldCap);
        }));
        ctx.setPacketHandled(true);
    }
}
