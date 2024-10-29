package net.sonicrushxii.chaos_emerald.block;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.entities.blue.IceSpike;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;

import javax.swing.text.AttributeSet;

public class ChaosBlockItem extends BlockItem {

    public ChaosBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level world = pContext.getLevel();
        Player player = pContext.getPlayer();

        assert player != null;
        if(!player.isShiftKeyDown()){
            switch(player.getMainHandItem().getItem().toString())
            {
                case "aqua_emerald": System.out.println("Used Aqua Emerald");
                    break;
                case "blue_emerald":System.out.println("Used Blue Emerald");
                    blueEmeraldUse(world,player);
                    break;
                case "green_emerald": System.out.println("Used Green Emerald");
                    break;
                case "grey_emerald": System.out.println("Used Grey Emerald");
                    break;
                case "purple_emerald": System.out.println("Used Purple Emerald");
                    break;
                case "red_emerald": System.out.println("Used Red Emerald");
                    break;
                case "yellow_emerald": System.out.println("Used Yellow Emerald");
                    break;
            }
            System.out.println("Client Side:"+world.isClientSide+", Use Item:"+player.getMainHandItem().getDisplayName().getString());
            return InteractionResult.FAIL;
        }

        return super.useOn(pContext);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        switch(pPlayer.getMainHandItem().getItem().toString())
        {
            case "aqua_emerald": System.out.println("Used Aqua Emerald");
                                break;
            case "blue_emerald":System.out.println("Used Blue Emerald");
                                blueEmeraldUse(pLevel,pPlayer);
                                break;
            case "green_emerald": System.out.println("Used Green Emerald");
                break;
            case "grey_emerald": System.out.println("Used Grey Emerald");
                break;
            case "purple_emerald": System.out.println("Used Purple Emerald");
                break;
            case "red_emerald": System.out.println("Used Red Emerald");
                break;
            case "yellow_emerald": System.out.println("Used Yellow Emerald");
                break;
        }
        System.out.println("Client Side:"+pLevel.isClientSide+", Use Item:"+pPlayer.getMainHandItem().getDisplayName().getString());
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public static void blueEmeraldUse(Level pLevel, Player pPlayer)
    {
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_COOLDOWN).ifPresent(chaosEmeraldCooldown -> {
            if(chaosEmeraldCooldown.cooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] > 0) {
                pPlayer.displayClientMessage(Component.translatable("That Ability is not Ready Yet").withStyle(Style.EMPTY.withColor(0x0000FF)),true);
                return;
            }

            IceSpike iceSpike = new IceSpike(ModEntityTypes.ICE_SPIKE.get(), pLevel);
            iceSpike.setPos(pPlayer.getX()+pPlayer.getLookAngle().x,
                    pPlayer.getY()+pPlayer.getLookAngle().y+1.0,
                    pPlayer.getZ()+pPlayer.getLookAngle().z);
            iceSpike.setMovementDirection(pPlayer.getLookAngle());
            iceSpike.setOwner(pPlayer.getUUID());

            // Add the entity to the world
            pLevel.addFreshEntity(iceSpike);

            //Set Cooldown(in Seconds)
            chaosEmeraldCooldown.cooldownKey[EmeraldType.BLUE_EMERALD.ordinal()] = 25;
        });
    }
}

