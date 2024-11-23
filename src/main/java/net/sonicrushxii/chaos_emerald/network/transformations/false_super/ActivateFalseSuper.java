package net.sonicrushxii.chaos_emerald.network.transformations.false_super;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.modded.ModSounds;
import net.sonicrushxii.chaos_emerald.potion_effects.AttributeMultipliers;

import java.util.function.Supplier;

public class ActivateFalseSuper
{
    public ActivateFalseSuper() {}

    public ActivateFalseSuper(FriendlyByteBuf buffer){}

    public void encode(FriendlyByteBuf buffer){}

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(
                ()->{
                    ServerPlayer player = ctx.get().getSender();
                    //Check if Standing on the Master Emerald
                    if(player != null && "chaos_emerald:master_emerald"
                            .equals(ForgeRegistries.BLOCKS.getKey(
                                    player.level()
                                            .getBlockState(player.blockPosition()
                                                    .offset(0,-1,0))
                                            .getBlock())+""
                            ))
                    {
                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            //Give False Super Time
                            chaosEmeraldCap.falseSuperTimer = -80;

                            //Remove Gravity
                            player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.0);

                            //Play the Sound
                            player.level().playSound(null,player.getX(),player.getY(),player.getZ(), ModSounds.ACTIVATE_FALSE_SUPER.get(), SoundSource.MASTER, 1.0f, 1.0f);
                        });
                    }
                });
        ctx.get().setPacketHandled(true);
    }
}
