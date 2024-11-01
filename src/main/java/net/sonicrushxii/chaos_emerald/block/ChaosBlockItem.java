package net.sonicrushxii.chaos_emerald.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.entities.blue.IceSpike;
import net.sonicrushxii.chaos_emerald.event_handler.custom.ChaosEmeraldHandler;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import net.sonicrushxii.chaos_emerald.modded.ModEntityTypes;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;

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
                                    ChaosEmeraldHandler.aquaEmeraldUse(world,player);
                                    break;
                case "blue_emerald":System.out.println("Used Blue Emerald");
                                    ChaosEmeraldHandler.blueEmeraldUse(world,player);
                                    break;
                case "green_emerald": System.out.println("Used Green Emerald");
                                    ChaosEmeraldHandler.greenEmeraldUse(world,player);
                                    break;
                case "grey_emerald": System.out.println("Used Grey Emerald");
                                    ChaosEmeraldHandler.greyEmeraldUse(world,player);
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
                break;
            case "red_emerald": System.out.println("Used Red Emerald");
                break;
            case "yellow_emerald": System.out.println("Used Yellow Emerald");
                break;
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}

