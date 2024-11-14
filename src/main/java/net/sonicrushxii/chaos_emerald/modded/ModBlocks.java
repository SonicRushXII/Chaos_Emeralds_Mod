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
import net.sonicrushxii.chaos_emerald.block.SuperBlockItem;
import net.sonicrushxii.chaos_emerald.block.SuperEmeraldBlock;

import java.util.StringTokenizer;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosEmerald.MOD_ID);
    public static final RegistryObject<Block> AQUA_CHAOS_EMERALD = registerBlock("chaos_emerald/aqua_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> BLUE_CHAOS_EMERALD = registerBlock("chaos_emerald/blue_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> GREEN_CHAOS_EMERALD = registerBlock("chaos_emerald/green_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> GREY_CHAOS_EMERALD = registerBlock("chaos_emerald/grey_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> PURPLE_CHAOS_EMERALD = registerBlock("chaos_emerald/purple_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> RED_CHAOS_EMERALD = registerBlock("chaos_emerald/red_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> YELLOW_CHAOS_EMERALD = registerBlock("chaos_emerald/yellow_emerald",
            ()->new ChaosEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> AQUA_SUPER_EMERALD = registerBlock("super_emerald/aqua_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> BLUE_SUPER_EMERALD = registerBlock("super_emerald/blue_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> GREEN_SUPER_EMERALD = registerBlock("super_emerald/green_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> GREY_SUPER_EMERALD = registerBlock("super_emerald/grey_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> PURPLE_SUPER_EMERALD = registerBlock("super_emerald/purple_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> RED_SUPER_EMERALD = registerBlock("super_emerald/red_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));
    public static final RegistryObject<Block> YELLOW_SUPER_EMERALD = registerBlock("super_emerald/yellow_emerald",
            ()->new SuperEmeraldBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
                    .lightLevel((pProperites) -> {
                        return 13;
                    })));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        StringTokenizer sg = new StringTokenizer(name,"/");

        String token = sg.nextToken();
        System.err.println(token);
        switch(token)
        {
            case "chaos_emerald": registerChaosBlockItem(name,toReturn); break;
            case "super_emerald": registerSuperBlockItem(name,toReturn); break;
            default:
        }

        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerChaosBlockItem(String name, RegistryObject<T> block)
    {return ModItems.ITEMS.register(name, () -> new ChaosBlockItem(block.get(),new Item.Properties().stacksTo(1).fireResistant()));}

    private static <T extends Block>RegistryObject<Item> registerSuperBlockItem(String name, RegistryObject<T> block)
    {return ModItems.ITEMS.register(name, () -> new SuperBlockItem(block.get(),new Item.Properties().stacksTo(1).fireResistant()));}

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);

    }

}
