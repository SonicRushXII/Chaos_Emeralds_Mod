package net.sonicrushxii.chaos_emerald.event_handler.custom;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.sonicrushxii.chaos_emerald.KeyBindings;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.transformations.false_super.ActivateFalseSuper;

public class FalseSuperHandler
{
    private final static int FALSE_SUPER_DURATION = 20;

    public static void serverTick(ServerPlayer player, int serverTick)
    {
        //Server Tick
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            if(chaosEmeraldCap.falseSuperTimer > 0)
            {
                //Increase Dur
                chaosEmeraldCap.falseSuperTimer += 1;

                //Display Particle Every Tick


                //Give the Player the Effects every Second
                if(serverTick == 0)
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

                //End The Move
                if(chaosEmeraldCap.falseSuperTimer > FALSE_SUPER_DURATION*20)
                {
                    //Set Timer to 0
                    chaosEmeraldCap.falseSuperTimer = 0;

                    //Remove Effects
                    {
                        player.removeEffect(MobEffects.MOVEMENT_SPEED);     //Speed
                        player.removeEffect(MobEffects.JUMP);               //Jump
                        player.removeEffect(MobEffects.DIG_SPEED);          //Haste
                        player.removeEffect(MobEffects.DAMAGE_BOOST);       //Strength
                        player.removeEffect(MobEffects.DAMAGE_RESISTANCE);  //Resistance
                    }
                }
            }
        });
    }

    public static void clientTick(LocalPlayer player, int clientTick)
    {
        //Master Emerald - Key Press Use
        if(KeyBindings.INSTANCE.transformButton.consumeClick())
        {
            PacketHandler.sendToServer(new ActivateFalseSuper());
            while(KeyBindings.INSTANCE.transformButton.consumeClick());
        }
    }
}
