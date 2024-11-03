package net.sonicrushxii.chaos_emerald.network.red;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FireSyncPacketS2C {
    BlockPos blockPos;


    public FireSyncPacketS2C(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public FireSyncPacketS2C(FriendlyByteBuf buffer) {
        this.blockPos = buffer.readBlockPos();

    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeBlockPos(this.blockPos);

    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                        try {
                            Level world = Minecraft.getInstance().level;
                            world.setBlock(blockPos, Blocks.FIRE.defaultBlockState(), 3);
                        }catch (NullPointerException|ClassCastException ignored) {}
                    });
                });
        ctx.get().setPacketHandled(true);
    }
}
