package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.aqua.BindEffectSyncPacketS2C;

import java.util.*;

public class LoginHandler
{
    //Sync Chaos Bind Effect
    public static Map<Integer,Integer> bindEntityDur = new HashMap<>();

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        Player player = event.getEntity();
        if(player == null) return;

        //Clear the Hashmap on Login
        bindEntityDur.clear();

        //Perform Login functions
        if(!player.level().isClientSide()) onServerLogin((ServerPlayer) player);
        else onClientLogin((LocalPlayer) player);
    }

    private void onServerLogin(ServerPlayer player)
    {
        //Update the Effects on Server Side
        ServerLevel world = player.serverLevel();
        for(Entity entity : world.getAllEntities())
        {
            try{
                LivingEntity livingEntity = (LivingEntity) entity;
                if(livingEntity.hasEffect(ModEffects.CHAOS_BIND.get()) && livingEntity.getEffect(ModEffects.CHAOS_BIND.get()).getDuration() > 0)
                {
                    PacketHandler.sendToPlayer(player,new BindEffectSyncPacketS2C(livingEntity.getId(),livingEntity.getEffect(ModEffects.CHAOS_BIND.get()).getDuration()));
                }
            }catch(ClassCastException|NullPointerException ignored){}
        }
    }

    private void onClientLogin(LocalPlayer player)
    {
    }
}
