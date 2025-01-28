package net.sonicrushxii.chaos_emerald.event_handler.client_specific;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldCap;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.modded.ModEffects;
import org.joml.Vector3f;

public class ClientPacketHandler
{
    public static void clientMotionSync(int entityId, Vec3 entityMotion)
    {
        try {
            Level world = Minecraft.getInstance().level;
            Player player = (Player) world.getEntity(entityId);

            player.setDeltaMovement(entityMotion);
        }catch (NullPointerException|ClassCastException ignored) {}
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

    public static void clientParticleAura(String particle_Type, double absX, double absY, double absZ, double speed, float radiusX, float radiusY, float radiusZ, short count, boolean force, float red, float green, float blue, float scale) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        LocalPlayer player = mc.player;

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
        LocalPlayer player = mc.player;

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

    public static void clientStopSound(ResourceLocation soundLocation)
    {
        // This code is run on the client side
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        LocalPlayer player = mc.player;

        if(player != null && world != null) {
            for(SoundSource soundSource : SoundSource.values())
                mc.getSoundManager().stop(soundLocation, soundSource);
        }
    }

    public static void clientDataSync(int player_Id, ChaosEmeraldCap chaos_EmeraldCap)
    {
        // This code is run on the client side
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        // Get the player entity by ID
        Player player = (Player) mc.level.getEntity(player_Id);
        if (player == null) return;

        // Update the player's capability data on the client side
        player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            chaosEmeraldCap.copyFrom(chaos_EmeraldCap);
        });
    }

    public static void bindEffectSync(int effectDur, int entityId)
    {
        try {
            LivingEntity livingEntity = (LivingEntity) Minecraft.getInstance().level.getEntity(entityId);

            if (effectDur > 0)
            {
                if(livingEntity.hasEffect(ModEffects.CHAOS_BIND.get()))
                    livingEntity.getEffect(ModEffects.CHAOS_BIND.get()).
                            update(new MobEffectInstance(ModEffects.CHAOS_BIND.get(), effectDur,
                                    0, false, false,false));
                else
                    livingEntity.addEffect(new MobEffectInstance(ModEffects.CHAOS_BIND.get(), effectDur,
                            0, false, false,false));
            }
            else if(livingEntity.hasEffect(ModEffects.CHAOS_BIND.get()))
            {
                livingEntity.removeEffect(ModEffects.CHAOS_BIND.get());
            }
        }catch (NullPointerException|ClassCastException ignored) {}
    }

    public static void digSyncEffect(int entityId, byte digTime, Vec3 entityMotion)
    {
        try {
            Level world = Minecraft.getInstance().level;
            Player player = (Player) world.getEntity(entityId);

            player.setDeltaMovement(entityMotion);
            player.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
                chaosEmeraldCap.greyChaosUse = digTime;
            });

        }catch (NullPointerException|ClassCastException ignored) {}
    }

    public static void fireSyncEffect(BlockPos blockPos)
    {
        try {
            Level world = Minecraft.getInstance().level;
            world.setBlock(blockPos, Blocks.FIRE.defaultBlockState(), 3);
        }catch (NullPointerException|ClassCastException ignored) {}
    }

    public static void superInfernoParticle(double absX, double absY, double absZ, short offset)
    {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        LocalPlayer player = mc.player;

        if (player != null && world != null)
        {
            double RADIUS = Utilities.random.nextDouble(2.0,3.0);

            for(int i=1;i<=5;++i)
            {
                for (float p = -1.5F; p <= 1.5F; p += 0.85F) {
                    double theta = i * Math.PI / 2.5 + offset * Math.PI / 30;
                    double pX = absX + RADIUS * Math.sin(theta);
                    double pZ = absZ + RADIUS * Math.cos(theta);
                    world.addParticle(ParticleTypes.FLAME, i%2==0,
                            pX, absY+p, pZ,
                            0, 0.05, 0);
                    world.addParticle(new DustParticleOptions(new Vector3f(1f,0f,0f),1.5f), i%2==0,
                            pX, absY+p, pZ,
                            0, 0.05, 0);
                }
            }
        }
    }



}
