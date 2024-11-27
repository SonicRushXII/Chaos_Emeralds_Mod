package net.sonicrushxii.chaos_emerald.item.chaos_locator;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec3;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.ParticleRaycastPacketS2C;

public class ChaosLocatorItem extends Item {

    public static TagKey<Structure> CHAOS_EMERALD_VAULTS = TagKey.create(Registries.STRUCTURE,
            new ResourceLocation(ChaosEmerald.MOD_ID,"emerald_vaults"));

    public ChaosLocatorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) pLevel;

            //Query the Structure
            Pair<BlockPos, Holder<Structure>> result = serverLevel.getChunkSource().getGenerator().findNearestMapStructure
                    (serverLevel,
                    serverLevel.registryAccess()
                            .registryOrThrow(Registries.STRUCTURE)
                            .getOrCreateTag(CHAOS_EMERALD_VAULTS),
                    pPlayer.blockPosition(), 100, false);


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
                                playerPos.add(lookDirection.scale(0.3)),playerPos.add(lookDirection.scale(7))));
                        PacketHandler.sendToALLPlayers(new ParticleRaycastPacketS2C(ParticleTypes.END_ROD,
                                playerPos.add(lookDirection.scale(0.3)),playerPos.add(lookDirection.scale(7))));
                        pPlayer.displayClientMessage(Component.translatable(String.format("Found at ~%d ~ ~%d, Distance: %.2f",relStructure2DPos[0],relStructure2DPos[1],distance)).withStyle(Style.EMPTY.withColor(emeraldStructure.getColorCode())),true);
                        break;
                    }
                }
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
