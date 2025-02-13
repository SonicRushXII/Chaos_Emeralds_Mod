package net.sonicrushxii.chaos_emerald.network.all;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.sonicrushxii.chaos_emerald.event_handler.client_specific.ClientPacketHandler;

public class UpdatePositionPacketS2C {
    private final Vec3 pos;
    private final float rotX;
    private final float rotY;
    private final int entityId;

    public UpdatePositionPacketS2C(Vec3 position, float yaw, float pitch, int entityId) {
        this.pos = position;
        this.rotY = yaw;
        this.rotX = pitch;
        this.entityId = entityId;
    }

    public UpdatePositionPacketS2C(Vec3 position, float yaw, int entityId) {
        this.pos = position;
        this.rotY = yaw;
        this.rotX = -999.0f;
        this.entityId = entityId;
    }

    public UpdatePositionPacketS2C(Vec3 position, int entityId) {
        this.pos = position;
        this.rotY = -999.0f;
        this.rotX = -999.0f;
        this.entityId = entityId;
    }

    public UpdatePositionPacketS2C(FriendlyByteBuf buf) {
        this.pos = buf.readVec3();
        this.rotY = buf.readFloat();
        this.rotX = buf.readFloat();
        this.entityId = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVec3(this.pos);
        buf.writeFloat(this.rotY);
        buf.writeFloat(this.rotX);
        buf.writeInt(this.entityId);
    }

    public void handle(CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientPacketHandler.updateClientPosition(pos,rotY,rotX,entityId);
        }));
        ctx.setPacketHandled(true);
    }
}