package net.sonicrushxii.chaos_emerald.item.chaos_locator;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.modded.ModItems;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.ParticleRaycastPacketS2C;
import net.sonicrushxii.chaos_emerald.network.all.UpdateHandItem;

public class ChaosLocatorItem extends Item {

    public static TagKey<Structure> ALL_EMERALD_VAULTS = TagKey.create(Registries.STRUCTURE,new ResourceLocation(ChaosEmerald.MOD_ID,"emerald_vaults"));
    public static TagKey<Structure> AQUA_VAULTS = TagKey.create(Registries.STRUCTURE,new ResourceLocation(ChaosEmerald.MOD_ID,"aqua_vaults"));
    public static TagKey<Structure> BLUE_VAULTS = TagKey.create(Registries.STRUCTURE,new ResourceLocation(ChaosEmerald.MOD_ID,"blue_vaults"));
    public static TagKey<Structure> GREEN_VAULTS = TagKey.create(Registries.STRUCTURE,new ResourceLocation(ChaosEmerald.MOD_ID,"green_vaults"));
    public static TagKey<Structure> GREY_VAULTS = TagKey.create(Registries.STRUCTURE,new ResourceLocation(ChaosEmerald.MOD_ID,"grey_vaults"));
    public static TagKey<Structure> PURPLE_VAULTS = TagKey.create(Registries.STRUCTURE,new ResourceLocation(ChaosEmerald.MOD_ID,"purple_vaults"));
    public static TagKey<Structure> RED_VAULTS = TagKey.create(Registries.STRUCTURE,new ResourceLocation(ChaosEmerald.MOD_ID,"red_vaults"));
    public static TagKey<Structure> YELLOW_VAULTS = TagKey.create(Registries.STRUCTURE,new ResourceLocation(ChaosEmerald.MOD_ID,"yellow_vaults"));
    public static TagKey<Structure> MASTER_EMERALD_ISLANDS = TagKey.create(Registries.STRUCTURE,new ResourceLocation(ChaosEmerald.MOD_ID,"master_emerald_islands"));

    public ChaosLocatorItem(Properties pProperties) {
        super(pProperties);
    }

    public static int getCustomModelData(ItemStack itemStack) {
        if (itemStack.hasTag()) {
            CompoundTag tag = itemStack.getTag();
            if (tag != null && tag.contains("CustomModelData", 3)) { // 3 is the NBT type ID for integers
                return tag.getInt("CustomModelData");
            }
        }
        return -1; // Return a default value or handle the absence of CustomModelData as needed
    }

    public static boolean isUpgraded(ItemStack itemStack)
    {
        return (itemStack.is(ModItems.SUPER_RADAR.get()));
    }

    public static void scanForEmeralds(ServerLevel serverLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        ItemStack radarItem = (pUsedHand == InteractionHand.MAIN_HAND)?pPlayer.getMainHandItem():pPlayer.getOffhandItem();
        int customModelData = getCustomModelData(radarItem);

        Pair<BlockPos, Holder<Structure>> result;

        switch(customModelData)
        {
            case 732110 : result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel,
                    serverLevel.registryAccess()
                            .registryOrThrow(Registries.STRUCTURE)
                            .getOrCreateTag(AQUA_VAULTS),
                    pPlayer.blockPosition(), 100, false);
                break;
            case 732111 : result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel,
                    serverLevel.registryAccess()
                            .registryOrThrow(Registries.STRUCTURE)
                            .getOrCreateTag(BLUE_VAULTS),
                    pPlayer.blockPosition(), 100, false);
                break;
            case 732112 : result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel,
                    serverLevel.registryAccess()
                            .registryOrThrow(Registries.STRUCTURE)
                            .getOrCreateTag(GREEN_VAULTS),
                    pPlayer.blockPosition(), 100, false);
                break;
            case 732113 : result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel,
                    serverLevel.registryAccess()
                            .registryOrThrow(Registries.STRUCTURE)
                            .getOrCreateTag(GREY_VAULTS),
                    pPlayer.blockPosition(), 100, false);
                break;
            case 732114 : result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel,
                    serverLevel.registryAccess()
                            .registryOrThrow(Registries.STRUCTURE)
                            .getOrCreateTag(PURPLE_VAULTS),
                    pPlayer.blockPosition(), 100, false);
                break;
            case 732115 : result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel,
                    serverLevel.registryAccess()
                            .registryOrThrow(Registries.STRUCTURE)
                            .getOrCreateTag(RED_VAULTS),
                    pPlayer.blockPosition(), 100, false);
                break;
            case 732116 : result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel,
                    serverLevel.registryAccess()
                            .registryOrThrow(Registries.STRUCTURE)
                            .getOrCreateTag(YELLOW_VAULTS),
                    pPlayer.blockPosition(), 100, false);
                break;
            case 732117 : result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel,
                    serverLevel.registryAccess()
                            .registryOrThrow(Registries.STRUCTURE)
                            .getOrCreateTag(MASTER_EMERALD_ISLANDS),
                    pPlayer.blockPosition(), 100, false);
                break;
            default: result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel,
                    serverLevel.registryAccess()
                            .registryOrThrow(Registries.STRUCTURE)
                            .getOrCreateTag(ALL_EMERALD_VAULTS),
                    pPlayer.blockPosition(), 100, false);
        }

        //Get Result of the
        if(result != null)
        {
            BlockPos structureLocation = result.getFirst();
            Holder<Structure> foundStructure = result.getSecond();

            int[] player2DPos = {pPlayer.getBlockX(),pPlayer.getBlockZ()};
            int[] structure2DPos = {structureLocation.getX(),structureLocation.getZ()};

            int[] relStructure2DPos = {structure2DPos[0]-player2DPos[0],structure2DPos[1]-player2DPos[1]};
            double distance = Math.sqrt(Math.pow(relStructure2DPos[0],2) + Math.pow(relStructure2DPos[1],2));

            Vec3 playerPos = new Vec3(pPlayer.getX(),pPlayer.getY()+pPlayer.getEyeHeight()-0.1,pPlayer.getZ());
            Vec3 lookDirection = (new Vec3(structure2DPos[0],playerPos.y(),structure2DPos[1])).subtract(playerPos).normalize();

            for(EmeraldStructure emeraldStructure : EmeraldStructure.values())
            {
                if(foundStructure.is(emeraldStructure.getVaultLocation()))
                {
                    PacketHandler.sendToALLPlayers(new ParticleRaycastPacketS2C(emeraldStructure.getParticle(),
                            playerPos.add(lookDirection.scale(0.3)),playerPos.add(lookDirection.scale(10))));
                    PacketHandler.sendToALLPlayers(new ParticleRaycastPacketS2C(ParticleTypes.END_ROD,
                            playerPos.add(lookDirection.scale(0.3)),playerPos.add(lookDirection.scale(10))));
                    serverLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),
                            SoundEvents.BEACON_ACTIVATE, SoundSource.MASTER, 0.55f, 2F);

                    if(isUpgraded(radarItem)) pPlayer.displayClientMessage(Component.translatable(String.format("Found at ~%d ~ ~%d, Distance: %.2f",relStructure2DPos[0],relStructure2DPos[1],distance)).withStyle(Style.EMPTY.withColor(emeraldStructure.getColorCode())),true);
                }
            }
        }
    }

    public static void changeEmeraldMode(Player pPlayer, InteractionHand pUsedHand)
    {
        //Get Item
        ItemStack radarItem = (pUsedHand == InteractionHand.MAIN_HAND)?pPlayer.getMainHandItem():pPlayer.getOffhandItem();

        //Find Custom Model Data
        int customModelData = getCustomModelData(radarItem);

        customModelData = customModelData+1;
        if(customModelData > 732116 && !isUpgraded(radarItem) || customModelData > 732117 && isUpgraded(radarItem))
            customModelData = 732109;
        if(customModelData < 732109)
            customModelData = 732110;

        //Modify the Tag
        CompoundTag radarTag = radarItem.getOrCreateTag();
        radarTag.putInt("CustomModelData",customModelData);

        //Get Radar Item
        radarItem.setTag(radarTag);

        //Replace Item that is in the current Hand
        switch(pUsedHand)
        {
            case MAIN_HAND: pPlayer.setItemSlot(EquipmentSlot.MAINHAND,radarItem);
                            PacketHandler.sendToServer(new UpdateHandItem(radarItem,InteractionHand.MAIN_HAND));
                            break;
            case OFF_HAND:  pPlayer.setItemSlot(EquipmentSlot.OFFHAND,radarItem);
                            PacketHandler.sendToServer(new UpdateHandItem(radarItem,InteractionHand.OFF_HAND));
                            break;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) pLevel;

            if(pPlayer.isShiftKeyDown())    changeEmeraldMode(pPlayer, pUsedHand);
            else                            scanForEmeralds(serverLevel, pPlayer, pUsedHand);

        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
