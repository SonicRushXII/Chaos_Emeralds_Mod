package net.sonicrushxii.chaos_emerald.network.all;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateMainhandItem {

    ItemStack itemStack;

    public UpdateMainhandItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public UpdateMainhandItem(FriendlyByteBuf buffer) {
        this.itemStack = buffer.readItem();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeItem(this.itemStack);
    }

    public static void performUpdateItem(ServerPlayer pPlayer,ItemStack item)
    {
        pPlayer.setItemSlot(EquipmentSlot.MAINHAND, item);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();
                    if(player != null)
                        performUpdateItem(player,this.itemStack);
                });
        ctx.get().setPacketHandled(true);
    }
}
