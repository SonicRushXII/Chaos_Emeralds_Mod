package net.sonicrushxii.chaos_emerald.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChaosEmeraldProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ChaosEmeraldCooldown> CHAOS_EMERALD_COOLDOWN = CapabilityManager.get(new CapabilityToken<ChaosEmeraldCooldown>() {});

    private ChaosEmeraldCooldown chaosEmeraldCooldown = null;
    private final LazyOptional<ChaosEmeraldCooldown> optional = LazyOptional.of(this::createChaosEmeraldCooldown);

    private ChaosEmeraldCooldown createChaosEmeraldCooldown() {
        if(this.chaosEmeraldCooldown == null)
            this.chaosEmeraldCooldown = new ChaosEmeraldCooldown();

        return this.chaosEmeraldCooldown;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability == CHAOS_EMERALD_COOLDOWN)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createChaosEmeraldCooldown().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createChaosEmeraldCooldown().loadNBTData(nbt);
    }
}