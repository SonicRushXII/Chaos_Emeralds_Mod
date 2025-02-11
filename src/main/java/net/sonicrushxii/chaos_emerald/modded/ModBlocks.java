package net.sonicrushxii.chaos_emerald.modded;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.block.ChaosBlockItem;
import net.sonicrushxii.chaos_emerald.block.ChaosEmeraldBlock;
import net.sonicrushxii.chaos_emerald.block.MasterEmeraldBlock;
import net.sonicrushxii.chaos_emerald.block.SuperEmeraldBlock;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosEmerald.MOD_ID);
    public static final RegistryObject<Block> AQUA_CHAOS_EMERALD = registerBlock("chaos_emerald/aqua_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> BLUE_CHAOS_EMERALD = registerBlock("chaos_emerald/blue_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> GREEN_CHAOS_EMERALD = registerBlock("chaos_emerald/green_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> GREY_CHAOS_EMERALD = registerBlock("chaos_emerald/grey_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> PURPLE_CHAOS_EMERALD = registerBlock("chaos_emerald/purple_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> RED_CHAOS_EMERALD = registerBlock("chaos_emerald/red_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> YELLOW_CHAOS_EMERALD = registerBlock("chaos_emerald/yellow_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));

    public static final RegistryObject<Block> AQUA_SUPER_EMERALD = registerBlock("super_emerald/aqua_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 14;
                    })));
    public static final RegistryObject<Block> BLUE_SUPER_EMERALD = registerBlock("super_emerald/blue_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 14;
                    })));
    public static final RegistryObject<Block> GREEN_SUPER_EMERALD = registerBlock("super_emerald/green_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 14;
                    })));
    public static final RegistryObject<Block> GREY_SUPER_EMERALD = registerBlock("super_emerald/grey_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 14;
                    })));
    public static final RegistryObject<Block> PURPLE_SUPER_EMERALD = registerBlock("super_emerald/purple_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 14;
                    })));
    public static final RegistryObject<Block> RED_SUPER_EMERALD = registerBlock("super_emerald/red_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 14;
                    })));
    public static final RegistryObject<Block> YELLOW_SUPER_EMERALD = registerBlock("super_emerald/yellow_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 14;
                    })));

    public static final RegistryObject<Block> MASTER_EMERALD = registerBlock("master_emerald",
            ()->new MasterEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 15;
                    })));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return ModItems.ITEMS.register(name, () -> new ChaosBlockItem(block.get(),new Item.Properties().stacksTo(1)));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);

    }

}
