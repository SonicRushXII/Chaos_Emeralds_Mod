package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.sonicrushxii.chaos_emerald.event_handler.client_specific.ClientLoginHandler;
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
        if(FMLEnvironment.dist == Dist.CLIENT && player.level().isClientSide()) ClientLoginHandler.onClientLogin(player);
        else onServerLogin((ServerPlayer) player);
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


}
