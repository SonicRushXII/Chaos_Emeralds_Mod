package net.sonicrushxii.chaos_emerald.modded;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.entities.aqua.SuperAquaBubbleEntity;
import net.sonicrushxii.chaos_emerald.entities.blue.IceHorizontalSpike;
import net.sonicrushxii.chaos_emerald.entities.blue.IceVerticalSpike;
import net.sonicrushxii.chaos_emerald.entities.false_super.ChaosSpaz;
import net.sonicrushxii.chaos_emerald.entities.form_hyper.SuperEmeraldEntity;
import net.sonicrushxii.chaos_emerald.entities.form_super.ChaosEmeraldEntity;
import net.sonicrushxii.chaos_emerald.entities.form_super.PortalRingEntity;
import net.sonicrushxii.chaos_emerald.entities.form_super.ChaosSpearEX;
import net.sonicrushxii.chaos_emerald.entities.green.ChaosDiveRipple;
import net.sonicrushxii.chaos_emerald.entities.master_emerald.EmeraldTransformer;
import net.sonicrushxii.chaos_emerald.entities.purple.SuperChaosSlicer;
import net.sonicrushxii.chaos_emerald.entities.yellow.ChaosSpear;

@Mod.EventBusSubscriber(modid = ChaosEmerald.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChaosEmerald.MOD_ID);

    //Chaos Emeralds
    public static final RegistryObject<EntityType<ChaosEmeraldEntity>> CHAOS_EMERALD_ENTITY = ENTITY_TYPES.register("chaos_emerald_entity",
            () -> EntityType.Builder.<ChaosEmeraldEntity>of(ChaosEmeraldEntity::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("chaos_emerald_entity"));
    public static final RegistryObject<EntityType<SuperEmeraldEntity>> SUPER_EMERALD_ENTITY = ENTITY_TYPES.register("super_emerald_entity",
            () -> EntityType.Builder.<SuperEmeraldEntity>of(SuperEmeraldEntity::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("super_emerald_entity"));

    public static final RegistryObject<EntityType<IceHorizontalSpike>> ICE_CHAOS_SPIKE = ENTITY_TYPES.register("ice_chaos_spike",
            () -> EntityType.Builder.<IceHorizontalSpike>of(IceHorizontalSpike::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("ice_chaos_spike"));

    public static final RegistryObject<EntityType<ChaosSpear>> CHAOS_SPEAR = ENTITY_TYPES.register("chaos_spear",
            () -> EntityType.Builder.<ChaosSpear>of(ChaosSpear::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("chaos_spear"));

    //False Super
    public static final RegistryObject<EntityType<ChaosSpaz>> FALSE_SUPER_CHAOS_SPAZ = ENTITY_TYPES.register("false_super_chaos_spaz",
            () -> EntityType.Builder.<ChaosSpaz>of(ChaosSpaz::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("false_super_chaos_spaz"));


    //Super Form
    public static final RegistryObject<EntityType<ChaosSpearEX>> CHAOS_SPEAR_EX = ENTITY_TYPES.register("chaos_spear_ex",
            () -> EntityType.Builder.<ChaosSpearEX>of(ChaosSpearEX::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("chaos_spear_ex"));
    public static final RegistryObject<EntityType<PortalRingEntity>> PORTAL_RING = ENTITY_TYPES.register("portal_ring",
            () -> EntityType.Builder.<PortalRingEntity>of(PortalRingEntity::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("portal_ring"));

    //Super Emeralds
    public static final RegistryObject<EntityType<EmeraldTransformer>> EMERALD_TRANSFORMER = ENTITY_TYPES.register("emerald_transformer",
            () -> EntityType.Builder.<EmeraldTransformer>of(EmeraldTransformer::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("emerald_transformer"));

    public static final RegistryObject<EntityType<IceVerticalSpike>> ICE_SUPER_SPIKE = ENTITY_TYPES.register("ice_super_spike",
            () -> EntityType.Builder.<IceVerticalSpike>of(IceVerticalSpike::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .build("ice_super_spike"));

    public static final RegistryObject<EntityType<SuperAquaBubbleEntity>> AQUA_BOOST_BUBBLE = ENTITY_TYPES.register("aqua_super_bubble",
            () -> EntityType.Builder.<SuperAquaBubbleEntity>of(SuperAquaBubbleEntity::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .setTrackingRange(32)
                    .build("aqua_super_bubble"));

    public static final RegistryObject<EntityType<ChaosDiveRipple>> CHAOS_DIVE_RIPPLE = ENTITY_TYPES.register("dive_super_ripple",
            () -> EntityType.Builder.<ChaosDiveRipple>of(ChaosDiveRipple::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .setTrackingRange(32)
                    .build("dive_super_ripple"));

    public static final RegistryObject<EntityType<SuperChaosSlicer>> CHAOS_SLICER = ENTITY_TYPES.register("super_chaos_slicer",
            () -> EntityType.Builder.<SuperChaosSlicer>of(SuperChaosSlicer::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)  // Define the size of the entity
                    .setTrackingRange(32)
                    .build("super_chaos_slicer"));





    public static void register(IEventBus eventBus){ ENTITY_TYPES.register(eventBus);}
}

