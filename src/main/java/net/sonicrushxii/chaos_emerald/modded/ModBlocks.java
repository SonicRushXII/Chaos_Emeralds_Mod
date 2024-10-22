package net.sonicrushxii.chaos_emerald.modded;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.block.ChaosEmeraldBlock;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosEmerald.MOD_ID);

    public static final RegistryObject<Block> AQUA_EMERALD = registerBlock("aqua_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> BLUE_EMERALD = registerBlock("blue_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> GREEN_EMERALD = registerBlock("green_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> GREY_EMERALD = registerBlock("grey_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> PURPLE_EMERALD = registerBlock("purple_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> RED_EMERALD = registerBlock("red_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> YELLOW_EMERALD = registerBlock("yellow_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),new Item.Properties().stacksTo(1)));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);

    }

}
