package net.sonicrushxii.chaos_emerald.event_handler;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.modded.ModDimensions;

@Mod.EventBusSubscriber(modid = ChaosEmerald.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldLoadHandler {
    public static BlockPos reprieveTargetPos = new BlockPos(0, 50, 0);

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        // Check if the level is a ServerLevel (i.e., it's a dimension on the server side)
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            // Check if the current dimension matches your custom dimension
            if (serverLevel.dimension().equals(ModDimensions.CHAOS_REPRIEVE_LEVEL_KEY))
            {
                //Place the structure at the given position
                Utilities.placeStructure(serverLevel,
                        reprieveTargetPos.getX()-24, reprieveTargetPos.getY()-18, reprieveTargetPos.getZ()-1,"ancient_hub");
            }
        }
    }
}
