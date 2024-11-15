package net.sonicrushxii.chaos_emerald.modded;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;

public class ModCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChaosEmerald.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CHAOS_EMERALD_TAB = CREATIVE_MODE_TABS.register("chaos_emerald_tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ModBlocks.GREEN_CHAOS_EMERALD.get()))
                    .title(Component.translatable("creativetab.chaos_emerald"))
                    .displayItems((pParameters,pOutput)->{
                        pOutput.accept(ModBlocks.AQUA_CHAOS_EMERALD.get());
                        pOutput.accept(ModBlocks.BLUE_CHAOS_EMERALD.get());
                        pOutput.accept(ModBlocks.GREEN_CHAOS_EMERALD.get());
                        pOutput.accept(ModBlocks.GREY_CHAOS_EMERALD.get());
                        pOutput.accept(ModBlocks.PURPLE_CHAOS_EMERALD.get());
                        pOutput.accept(ModBlocks.RED_CHAOS_EMERALD.get());
                        pOutput.accept(ModBlocks.YELLOW_CHAOS_EMERALD.get());

                        pOutput.accept(ModBlocks.AQUA_SUPER_EMERALD.get());
                        pOutput.accept(ModBlocks.BLUE_SUPER_EMERALD.get());
                        pOutput.accept(ModBlocks.GREEN_SUPER_EMERALD.get());
                        pOutput.accept(ModBlocks.GREY_SUPER_EMERALD.get());
                        pOutput.accept(ModBlocks.PURPLE_SUPER_EMERALD.get());
                        pOutput.accept(ModBlocks.RED_SUPER_EMERALD.get());
                        pOutput.accept(ModBlocks.YELLOW_SUPER_EMERALD.get());

                        pOutput.accept(ModBlocks.MASTER_EMERALD.get());
                    })
                    .build());

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
