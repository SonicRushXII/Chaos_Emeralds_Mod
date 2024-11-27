package net.sonicrushxii.chaos_emerald.modded;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.item.chaos_locator.ChaosLocatorItem;
import net.sonicrushxii.chaos_emerald.item.ManuscriptItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, ChaosEmerald.MOD_ID);

    public static final RegistryObject<Item> MANUSCRIPT_SCROLL = ITEMS.register("manuscript_scroll",
            ()-> new ManuscriptItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CHAOS_LOCATOR = ITEMS.register("chaos_emerald_locator",
            ()-> new ChaosLocatorItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
