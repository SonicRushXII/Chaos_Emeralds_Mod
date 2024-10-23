package net.sonicrushxii.chaos_emerald.network;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import net.minecraftforge.network.ChannelBuilder;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.network.common.BreakBlock;
import net.sonicrushxii.chaos_emerald.network.common.UpdateMainhandItem;

public class PacketHandler {
    private static final int PROTOCOL_VERSION = 1;
    private static final SimpleChannel INSTANCE = ChannelBuilder
            .named(new ResourceLocation(ChaosEmerald.MOD_ID, "main"))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(BreakBlock.class, NetworkDirection.PLAY_TO_SERVER).encoder(BreakBlock::encode).decoder(BreakBlock::new).consumerMainThread(BreakBlock::handle).add();
        INSTANCE.messageBuilder(UpdateMainhandItem.class, NetworkDirection.PLAY_TO_SERVER).encoder(UpdateMainhandItem::encode).decoder(UpdateMainhandItem::new).consumerMainThread(UpdateMainhandItem::handle).add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static void sendToPlayer(ServerPlayer player, Object msg) {
        INSTANCE.send(msg,PacketDistributor.PLAYER.with(player));
    }

    public static void sendToChunkPlayers(LevelChunk levelChunk, Object msg) {
        INSTANCE.send(msg,PacketDistributor.TRACKING_CHUNK.with(levelChunk));
    }

    public static void sendToALLPlayers(Object msg) {
        INSTANCE.send(msg,PacketDistributor.ALL.noArg());
    }

}
