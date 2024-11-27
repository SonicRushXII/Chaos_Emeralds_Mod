package net.sonicrushxii.chaos_emerald.item.chaos_locator;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import org.joml.Vector3f;

public enum EmeraldStructure
{
    AQUA_LOCATION("aqua_vault",
            new DustParticleOptions(new Vector3f(0,1F,1F),2.5f),
            0x00FFFF),
    BLUE_LOCATION("blue_vault",
            new DustParticleOptions(new Vector3f(0,0,1F),2.5f),
            0x0000FF),
    GREEN_LOCATION("green_vault",
            new DustParticleOptions(new Vector3f(0,1F,0),2.5f),
            0x00FF00),
    GREY_LOCATION("grey_vault",
            new DustParticleOptions(new Vector3f(0.8F,0.8F,0.8F),2.5f),
            0xEEEEEE),
    PURPLE_LOCATION("purple_vault",
            new DustParticleOptions(new Vector3f(1,0,1),2.5f),
            0xCC00FF),
    RED_LOCATION("red_vault",
            new DustParticleOptions(new Vector3f(1,0,0),2.5f),
            0xCC00FF),
    YELLOW_LOCATION("yellow_vault",
            new DustParticleOptions(new Vector3f(1,1,0),2.5f),
            0xFFFF00);

    private final ResourceLocation vaultLocation;
    private final ParticleOptions particleOptions;
    private final int color;

    EmeraldStructure(String vaultLocation, ParticleOptions particleOptions, int color)
    {
        this.vaultLocation = new ResourceLocation(ChaosEmerald.MOD_ID,vaultLocation);
        this.particleOptions = particleOptions;
        this.color = color;
    }
    public ResourceLocation getVaultLocation() { return vaultLocation; }
    public ParticleOptions getParticle() { return particleOptions; }
    public int getColorCode() {return this.color;}
}
