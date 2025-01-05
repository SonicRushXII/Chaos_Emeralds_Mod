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
    public static Capability<ChaosEmeraldCap> CHAOS_EMERALD_CAP = CapabilityManager.get(new CapabilityToken<ChaosEmeraldCap>() {});

    private ChaosEmeraldCap chaosEmeraldCap = null;
    private final LazyOptional<ChaosEmeraldCap> optional = LazyOptional.of(this::createChaosEmeraldCap);

    private ChaosEmeraldCap createChaosEmeraldCap() {
        if(this.chaosEmeraldCap == null) {
            this.chaosEmeraldCap = new ChaosEmeraldCap();
            System.err.println("CHANGED CAPABILITY");
        }

        return this.chaosEmeraldCap;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability == CHAOS_EMERALD_CAP)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createChaosEmeraldCap().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createChaosEmeraldCap().loadNBTData(nbt);
    }
}