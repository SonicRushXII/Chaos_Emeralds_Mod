package net.sonicrushxii.chaos_emerald.network.all;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BreakBlock {
    BlockPos blockPos;

    public BreakBlock(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public BreakBlock(FriendlyByteBuf buffer) {
        this.blockPos = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeBlockPos(this.blockPos);
    }

    public static void performBreakBlock(Level pLevel,BlockPos blockPos)
    {
        pLevel.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
        pLevel.updateNeighborsAt(blockPos, Blocks.AIR);
        pLevel.updateNeighborsAt(blockPos.below(), Blocks.AIR);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();
                    if(player != null)
                        performBreakBlock(player.level(),blockPos);
                });
        ctx.get().setPacketHandled(true);
    }
}
