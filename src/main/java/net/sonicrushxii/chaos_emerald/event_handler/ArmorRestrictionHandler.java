package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.modded.ModBlocks;

public class ArmorRestrictionHandler {

    @SubscribeEvent
    public void onArmorChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player player)
        {
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap ->
            {
                if(chaosEmeraldCap.superFormTimer == 0) return;

                // Check if the slot is an armor slot
                EquipmentSlot slot = event.getSlot();
                if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST ||
                        slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET) {

                    ItemStack itemStack = player.getItemBySlot(slot);

                    // Remove the armor from the slot (clear it)
                    player.setItemSlot(slot, ItemStack.EMPTY);

                    //Give back the item
                    if (!player.getInventory().add(itemStack))
                        player.drop((itemStack), false);
                }
            });
        }
    }
}