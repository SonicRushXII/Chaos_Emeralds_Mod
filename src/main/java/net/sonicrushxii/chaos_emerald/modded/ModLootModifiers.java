package net.sonicrushxii.chaos_emerald.modded;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.loot.EndCityLootModifier;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ChaosEmerald.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> END_CITY_LOOT =
            LOOT_MODIFIER_SERIALIZERS.register("end_city_loot", EndCityLootModifier.CODEC);


    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}