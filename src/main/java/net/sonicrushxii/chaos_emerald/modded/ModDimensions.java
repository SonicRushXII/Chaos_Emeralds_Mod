package net.sonicrushxii.chaos_emerald.modded;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;

public class ModDimensions {
    public static final ResourceKey<Level> CHAOS_REPRIEVE_LEVEL_KEY = ResourceKey.create(
            Registries.DIMENSION,new ResourceLocation(ChaosEmerald.MOD_ID, "chaos_reprieve_dim")
    );
}
