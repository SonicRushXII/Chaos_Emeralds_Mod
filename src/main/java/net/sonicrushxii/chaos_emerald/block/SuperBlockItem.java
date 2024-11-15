package net.sonicrushxii.chaos_emerald.block;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.sonicrushxii.chaos_emerald.event_handler.custom.SuperEmeraldHandler;

public class SuperBlockItem extends BlockItem {
    public SuperBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    private static boolean useEmerald(String itemString, Level pLevel, Player pPlayer)
    {
        switch(itemString)
        {
            case "aqua_emerald": System.out.println("Used Aqua Emerald");
                SuperEmeraldHandler.aquaEmeraldUse(pLevel,pPlayer);
                break;
            case "blue_emerald":System.out.println("Used Blue Emerald");
                SuperEmeraldHandler.blueEmeraldUse(pLevel,pPlayer);
                break;
            case "green_emerald": System.out.println("Used Green Emerald");
                SuperEmeraldHandler.greenEmeraldUse(pLevel,pPlayer);
                break;
            case "grey_emerald": System.out.println("Used Grey Emerald");
                SuperEmeraldHandler.greyEmeraldUse(pLevel,pPlayer);
                break;
            case "purple_emerald": System.out.println("Used Purple Emerald");
                SuperEmeraldHandler.purpleEmeraldUse(pLevel,pPlayer);
                break;
            case "red_emerald": System.out.println("Used Red Emerald");
                SuperEmeraldHandler.redEmeraldUse(pLevel,pPlayer);
                break;
            case "yellow_emerald": System.out.println("Used Yellow Emerald");
                SuperEmeraldHandler.yellowEmeraldUse(pLevel,pPlayer);
                break;
            default: return false;
        }
        System.out.println("Client Side:"+pLevel.isClientSide+", Use Item:"+itemString);
        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level world = pContext.getLevel();
        Player player = pContext.getPlayer();

        assert player != null;
        if(!player.isShiftKeyDown()){
            //If MainHand Fails check Offhand
            if(!useEmerald(player.getMainHandItem().getItem().toString(), world, player))
                useEmerald(player.getOffhandItem().getItem().toString(), world, player);
            return InteractionResult.FAIL;
        }

        return super.useOn(pContext);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        //If MainHand Fails check Offhand
        if(!useEmerald(pPlayer.getMainHandItem().getItem().toString(), pLevel, pPlayer))
            useEmerald(pPlayer.getOffhandItem().getItem().toString(), pLevel, pPlayer);

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}

