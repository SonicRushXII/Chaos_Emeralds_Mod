package net.sonicrushxii.chaos_emerald.block;

import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
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
import net.minecraftforge.jarjar.selection.util.Constants;
import net.sonicrushxii.chaos_emerald.modded.ModBlocks;

public class ChaosEmeraldBlock extends Block {

    public static final VoxelShape SHAPE = Block.box(3,4,3,13,11,13);

    public ChaosEmeraldBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.getMainHandItem() == ItemStack.EMPTY && !pLevel.isClientSide) {
            pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
            pLevel.playSound(pPlayer,pPos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.5f,1.1f);
            pLevel.updateNeighborsAt(pPos, Blocks.AIR);
            pLevel.updateNeighborsAt(pPos.below(), Blocks.AIR);

            pPlayer.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(pState.getBlock().asItem()));
            return InteractionResult.SUCCESS;
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
