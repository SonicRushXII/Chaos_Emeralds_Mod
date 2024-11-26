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

    public static final RegistryObject<SoundEvent> ACTIVATE_FALSE_SUPER =
            registerSoundEvents("activate_false_super");
    public static final RegistryObject<SoundEvent> FALSE_CHAOS_SPAZ =
            registerSoundEvents("false_chaos_spaz");
    public static final RegistryObject<SoundEvent> SUPER_EMERALD_TRANSFORM =
            registerSoundEvents("super_emerald_transform");

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
