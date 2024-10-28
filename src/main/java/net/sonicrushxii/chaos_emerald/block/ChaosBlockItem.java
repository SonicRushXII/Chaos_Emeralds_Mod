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
import net.sonicrushxii.chaos_emerald.entities.blue.IceSpike;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;

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
        IceSpike iceSpike = new IceSpike(ModEntityTypes.ICE_SPIKE.get(), pLevel);
        iceSpike.setPos(pPlayer.getX()+pPlayer.getLookAngle().x,
                        pPlayer.getY()+pPlayer.getLookAngle().y,
                        pPlayer.getZ()+pPlayer.getLookAngle().z);
        iceSpike.setMovementDirection(pPlayer.getLookAngle());

        // Add the entity to the world
        pLevel.addFreshEntity(iceSpike);
    }
}

