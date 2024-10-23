package net.sonicrushxii.chaos_emerald.network.common;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;


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

    public static void performUpdateItem(ServerPlayer pPlayer,ItemStack itemStack)
    {
        pPlayer.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
    }

    public void handle(CustomPayloadEvent.Context ctx){
        ctx.enqueueWork(
                ()->{
                    ServerPlayer player = ctx.getSender();
                    if(player != null)
                        performUpdateItem(player,this.itemStack);
                });
        ctx.setPacketHandled(true);
    }
}
