package net.sonicrushxii.chaos_emerald.modded;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ChaosEmerald.MOD_ID);

    public static final RegistryObject<SoundEvent> CHAOS_CONTROL_TELEPORT_START =
            registerSoundEvents("chaos_control_teleport_start");
    public static final RegistryObject<SoundEvent> CHAOS_CONTROL_TELEPORT_END =
            registerSoundEvents("chaos_control_teleport_end");
    public static final RegistryObject<SoundEvent> CHAOS_CONTROL_TIME_STOP =
            registerSoundEvents("chaos_control_time_stop");
    public static final RegistryObject<SoundEvent> CHAOS_CONTROL_TIME_RESUME =
            registerSoundEvents("chaos_control_time_resume");

    private static RegistryObject<SoundEvent> registerSoundEvents(String soundName){
        return SOUND_EVENTS.register(soundName,
                ()->SoundEvent.createVariableRangeEvent(
                        new ResourceLocation(ChaosEmerald.MOD_ID,soundName)
                ));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}