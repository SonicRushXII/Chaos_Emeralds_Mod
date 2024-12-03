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
import net.sonicrushxii.chaos_emerald.event_handler.custom.ChaosEmeraldHandler;

import java.util.StringTokenizer;

public class ChaosBlockItem extends BlockItem {
    public ChaosBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    private static boolean useEmerald(String itemString, Level pLevel, Player pPlayer)
    {
        //Make String Tokenizer
        StringTokenizer sg = new StringTokenizer(itemString,"/");
        //Ignore First Token
        sg.nextToken();

        //Get Last Token
        switch(sg.nextToken())
        {
            case "aqua_emerald": System.out.println("Used Aqua Emerald");
                ChaosEmeraldHandler.aquaEmeraldUse(pLevel,pPlayer);
                break;
            case "blue_emerald":System.out.println("Used Blue Emerald");
                ChaosEmeraldHandler.blueEmeraldUse(pLevel,pPlayer);
                break;
            case "green_emerald": System.out.println("Used Green Emerald");
                ChaosEmeraldHandler.greenEmeraldUse(pLevel,pPlayer);
                break;
            case "grey_emerald": System.out.println("Used Grey Emerald");
                ChaosEmeraldHandler.greyEmeraldUse(pLevel,pPlayer);
                break;
            case "purple_emerald": System.out.println("Used Purple Emerald");
                ChaosEmeraldHandler.purpleEmeraldUse(pLevel,pPlayer);
                break;
            case "red_emerald": System.out.println("Used Red Emerald");
                ChaosEmeraldHandler.redEmeraldUse(pLevel,pPlayer);
                break;
            case "yellow_emerald": System.out.println("Used Yellow Emerald");
                ChaosEmeraldHandler.yellowEmeraldUse(pLevel,pPlayer);
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
            useEmerald(pContext.getItemInHand().getItem().toString(), world, player);
            return InteractionResult.FAIL;
        }

        return super.useOn(pContext);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        //Use corresponding hand
        switch(pUsedHand)
        {
            case MAIN_HAND: useEmerald(pPlayer.getMainHandItem().getItem().toString(), pLevel, pPlayer); break;
            case OFF_HAND: useEmerald(pPlayer.getOffhandItem().getItem().toString(), pLevel, pPlayer); break;
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}

