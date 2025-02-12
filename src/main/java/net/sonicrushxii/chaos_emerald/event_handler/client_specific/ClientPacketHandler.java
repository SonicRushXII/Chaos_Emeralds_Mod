package net.sonicrushxii.chaos_emerald.event_handler.client_specific;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;

import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import org.joml.Vector3f;

import java.util.Objects;

public class ClientPacketHandler
{
    public static void clientParticleAura(String particle_Type, double absX, double absY, double absZ, double speed, float radiusX, float radiusY, float radiusZ, short count, boolean force, float red, float green, float blue, float scale) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        AbstractClientPlayer player = mc.player;

        if (player != null && world != null) {
            ParticleType<?> particleType = ForgeRegistries.PARTICLE_TYPES.getValue(new ResourceLocation(particle_Type));
            ParticleOptions particleOptions;

            if (particleType == ParticleTypes.DUST) {
                particleOptions = new DustParticleOptions(new Vector3f(red, green, blue), scale);
            } else {
                particleOptions = (ParticleOptions) particleType;
            }

            assert particleOptions != null;
            Utilities.displayParticle(player.level(), particleOptions, absX, absY, absZ,
                    radiusX, radiusY, radiusZ,
                    speed, count, force);
        }
    }

    public static void clientParticleDir(String particle_Type, double absX, double absY, double absZ, double speedX,double speedY,double speedZ, float radiusX, float radiusY, float radiusZ, short count, boolean force, float red, float green, float blue, float scale)
    {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        AbstractClientPlayer player = mc.player;

        if (player != null && world != null) {
            ParticleType<?> particleType = ForgeRegistries.PARTICLE_TYPES.getValue(new ResourceLocation(particle_Type));
            ParticleOptions particleOptions;

            if (particleType == ParticleTypes.DUST) {
                particleOptions = new DustParticleOptions(new Vector3f(red, green, blue), scale);
            } else {
                particleOptions = (ParticleOptions) particleType;
            }

            assert particleOptions != null;
            Utilities.displayParticle(player.level(), particleOptions, absX,absY,absZ,
                    radiusX,radiusY,radiusZ,
                    speedX, speedY,speedZ,
                    count, force);
        }
    }

    public static void clientParticleRaycast(Vector3f pos1, Vector3f pos2, String particle_Type, float red, float green, float blue, float scale)
    {
        ClientLevel world = Minecraft.getInstance().level;
        if (world != null)
        {
            ParticleType<?> particleType = ForgeRegistries.PARTICLE_TYPES.getValue(new ResourceLocation(particle_Type));
            ParticleOptions particleOptions;

            if (particleType == ParticleTypes.DUST) {
                particleOptions = new DustParticleOptions(new Vector3f(red, green, blue), scale);
            } else {
                particleOptions = (ParticleOptions) particleType;
            }

            Utilities.particleRaycast(
                    world, particleOptions,
                    new Vec3(pos1.x(), pos1.y(), pos1.z()),
                    new Vec3(pos2.x(), pos2.y(), pos2.z())
            );
        }
    }

    public static void clientPlaysound(ResourceLocation soundLocation, BlockPos emitterPosition, float volume, float pitch)
    {
        // This code is run on the client side
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        AbstractClientPlayer player = mc.player;

        if(player != null && world != null) {
            if(player.blockPosition().distSqr(emitterPosition) < 576)
                world.playLocalSound(emitterPosition.getX(),emitterPosition.getY(),emitterPosition.getZ(),
                        Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(soundLocation)),
                        SoundSource.MASTER, volume, pitch, true);
        }
    }

    public static void clientStopSound(ResourceLocation soundLocation)
    {
        // This code is run on the client side
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        AbstractClientPlayer player = mc.player;

        if(player != null && world != null) {
            for(SoundSource soundSource : SoundSource.values())
                mc.getSoundManager().stop(soundLocation, soundSource);
        }
    }

}
