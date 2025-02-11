package net.sonicrushxii.chaos_emerald.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.common.BreakBlock;
import net.sonicrushxii.chaos_emerald.network.common.UpdateHandItem;
import org.jetbrains.annotations.Nullable;

public class ChaosEmeraldBlock extends Block {

    public static final VoxelShape SHAPE = Block.box(3,4,3,13,11,13);

    public ChaosEmeraldBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if(pPlacer instanceof ServerPlayer player){
            player.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.getMainHandItem() == ItemStack.EMPTY && !pLevel.isClientSide) {

            //Playsound
            pLevel.playSound(pPlayer,pPos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.5f,1.1f);

            //Remove Block
            pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
            pLevel.updateNeighborsAt(pPos, Blocks.AIR);
            pLevel.updateNeighborsAt(pPos.below(), Blocks.AIR);
            PacketHandler.sendToServer(new BreakBlock(pPos));

            //Update Player Item
            pPlayer.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(pState.getBlock().asItem()));
            PacketHandler.sendToServer(new UpdateHandItem(new ItemStack(pState.getBlock().asItem()),InteractionHand.MAIN_HAND));

            return InteractionResult.CONSUME;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
