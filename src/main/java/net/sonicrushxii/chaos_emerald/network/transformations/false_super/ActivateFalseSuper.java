package net.sonicrushxii.chaos_emerald.network.transformations.false_super;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;

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
                    //Check if Looking at the Master Emerald
                    if(player != null && Utilities.isPlayerLookingAtBlockType(player,player.level(),"chaos_emerald:master_emerald",7.5D))
                    {
                        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                            //Give False Super Time
                            chaosEmeraldCap.falseSuperTimer = 1;

                            //player.level().playSound(null,player.getX(),player.getY(),player.getZ(), ModSounds.DOUBLE_JUMP.get(), SoundSource.MASTER, 1.0f, 1.0f);

                            //Give Effects
                            {
                                //Speed
                                if(!player.hasEffect(MobEffects.MOVEMENT_SPEED)) player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 1, false, false));
                                else player.getEffect(MobEffects.MOVEMENT_SPEED).update(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 1, false, false));

                                //Jump
                                if(!player.hasEffect(MobEffects.JUMP)) player.addEffect(new MobEffectInstance(MobEffects.JUMP, -1, 1, false, false));
                                else player.getEffect(MobEffects.JUMP).update(new MobEffectInstance(MobEffects.JUMP, -1, 1, false, false));

                                //Haste
                                if(!player.hasEffect(MobEffects.DIG_SPEED)) player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, -1, 1, false, false));
                                else player.getEffect(MobEffects.DIG_SPEED).update(new MobEffectInstance(MobEffects.DIG_SPEED, -1, 1, false, false));

                                //Strength
                                if(!player.hasEffect(MobEffects.DAMAGE_BOOST)) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 1, false, false));
                                else player.getEffect(MobEffects.DAMAGE_BOOST).update(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 1, false, false));

                                //Resistance
                                if(!player.hasEffect(MobEffects.DAMAGE_RESISTANCE)) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 1, false, false));
                                else player.getEffect(MobEffects.DAMAGE_RESISTANCE).update(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 1, false, false));
                            }
                        });
                    }
                });
        ctx.get().setPacketHandled(true);
    }
}
