package net.sonicrushxii.chaos_emerald.modded;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.entities.blue.IceSpike;
import net.sonicrushxii.chaos_emerald.entities.yellow.ChaosSpear;

@Mod.EventBusSubscriber(modid = ChaosEmerald.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChaosEmerald.MOD_ID);

    public static final RegistryObject<EntityType<IceSpike>> ICE_SPIKE = ENTITY_TYPES.register("ice_spike",
            () -> EntityType.Builder.<IceSpike>of(IceSpike::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("ice_spike"));
    public static final RegistryObject<EntityType<ChaosSpear>> CHAOS_SPEAR = ENTITY_TYPES.register("chaos_spear",
            () -> EntityType.Builder.<ChaosSpear>of(ChaosSpear::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("chaos_spear"));

    public static void register(IEventBus eventBus){ ENTITY_TYPES.register(eventBus);}
}

