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
            new DustParticleOptions(new Vector3f(0,0.149F,1F),2.5f),
            0x0026FF),
    GREEN_LOCATION("green_vault",
            new DustParticleOptions(new Vector3f(0.298F,1F,0),2.5f),
            0x4CFF00),
    GREY_LOCATION("grey_vault",
            new DustParticleOptions(new Vector3f(0.851F,0.851F,0.851F),2.5f),
            0xD9D9D9),
    PURPLE_LOCATION("purple_vault",
            new DustParticleOptions(new Vector3f(0.698F,0,1F),2.5f),
            0xB200FF),
    RED_LOCATION("red_vault",
            new DustParticleOptions(new Vector3f(1F,0,0),2.5f),
            0xCC00FF),
    YELLOW_LOCATION("yellow_vault",
            new DustParticleOptions(new Vector3f(1F,0.925F,0),2.5f),
            0xFFEC00),
    MASTER_LOCATION("master_emerald_island",
            new DustParticleOptions(new Vector3f(0.114F,0.949F,0.447F),2.5f),
            0x1DF272);

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