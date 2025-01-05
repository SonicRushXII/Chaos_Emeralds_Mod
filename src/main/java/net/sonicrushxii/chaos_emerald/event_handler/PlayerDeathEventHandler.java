package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.modded.ModBlocks;
import net.sonicrushxii.chaos_emerald.network.transformations.form_hyper.DeactivateHyperForm;
import net.sonicrushxii.chaos_emerald.network.transformations.form_super.DeactivateSuperForm;

public class PlayerDeathEventHandler {

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        // Check if the entity that died is a Player
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                if(chaosEmeraldCap.superFormTimer > 0)
                {
                    player.spawnAtLocation(new ItemStack(ModBlocks.AQUA_CHAOS_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.BLUE_CHAOS_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.GREEN_CHAOS_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.GREY_CHAOS_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.PURPLE_CHAOS_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.RED_CHAOS_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.YELLOW_CHAOS_EMERALD.get().asItem()));

                    DeactivateSuperForm.performDeactivateSuper(player);
                }

                else if(chaosEmeraldCap.hyperFormCooldown > 0)
                {
                    player.spawnAtLocation(new ItemStack(ModBlocks.AQUA_SUPER_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.BLUE_SUPER_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.GREEN_SUPER_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.GREY_SUPER_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.PURPLE_SUPER_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.RED_SUPER_EMERALD.get().asItem()));
                    player.spawnAtLocation(new ItemStack(ModBlocks.YELLOW_SUPER_EMERALD.get().asItem()));

                    DeactivateHyperForm.performDeactivateHyper(player);
                }

            });
        }
    }
}