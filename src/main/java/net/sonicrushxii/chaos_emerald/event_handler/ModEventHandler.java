package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;

@Mod.EventBusSubscriber(modid = ChaosEmerald.MOD_ID)
public class ModEventHandler {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof Player){
            //Add Other Capabilities from here
            if(!event.getObject().getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).isPresent()){
                event.addCapability(new ResourceLocation(ChaosEmerald.MOD_ID, "properties"), new ChaosEmeraldProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if(event.isWasDeath()){
            //Add Other Capabilities from here
            event.getOriginal().getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(oldStore->{
                event.getOriginal().getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(newStore->{
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }
}
