package net.sonicrushxii.chaos_emerald.network.all;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.sonicrushxii.chaos_emerald.event_handler.client_specific.ClientPacketHandler;

public class SyncEntityMotionS2C {
    int entityId;
    Vec3 entityMotion;

    public SyncEntityMotionS2C(int entityId, Vec3 entityMotion) {
        this.entityId = entityId;
        this.entityMotion = entityMotion;
    }

    public SyncEntityMotionS2C(FriendlyByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.entityMotion = new Vec3(buffer.readDouble(),buffer.readDouble(),buffer.readDouble());
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.entityId);
        buffer.writeDouble(this.entityMotion.x());
        buffer.writeDouble(this.entityMotion.y());
        buffer.writeDouble(this.entityMotion.z());
    }

    public void handle(CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientPacketHandler.clientMotionSync(entityId,entityMotion);
        }));
        ctx.setPacketHandled(true);
    }
}
