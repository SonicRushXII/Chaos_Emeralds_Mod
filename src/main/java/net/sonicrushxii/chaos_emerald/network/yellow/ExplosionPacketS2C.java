package net.sonicrushxii.chaos_emerald.network.yellow;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExplosionPacketS2C {
    private final BlockPos pos;
    private final float strength;

    public ExplosionPacketS2C(BlockPos pos, float strength) {
        this.pos = pos;
        this.strength = strength;
    }

    public ExplosionPacketS2C(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.strength = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeFloat(strength);
    }

    public boolean handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // Ensure this runs only on the client
            if (context.get().getDirection().getReceptionSide().isClient()) {
                // Trigger a client-side explosion
                Minecraft.getInstance().level.explode(
                        Minecraft.getInstance().player, // player causing it, or null if none
                        pos.getX(), pos.getY(), pos.getZ(),
                        strength,
                        Level.ExplosionInteraction.NONE
                );
            }
        });
        return true;
    }
}