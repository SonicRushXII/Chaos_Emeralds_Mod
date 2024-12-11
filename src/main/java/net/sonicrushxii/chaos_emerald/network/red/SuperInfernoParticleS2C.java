package net.sonicrushxii.chaos_emerald.network.red;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.sonicrushxii.chaos_emerald.Utilities;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class SuperInfernoParticleS2C
{
    private final double absX, absY, absZ;
    private final short offset;

    public SuperInfernoParticleS2C(double absX, double absY, double absZ, short offset) {
        this.absX = absX;
        this.absY = absY;
        this.absZ = absZ;
        this.offset = offset;
    }

    public SuperInfernoParticleS2C(FriendlyByteBuf buf) {
        this.absX = buf.readDouble();
        this.absY = buf.readDouble();
        this.absZ = buf.readDouble();
        this.offset = buf.readShort();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(this.absX);
        buf.writeDouble(this.absY);
        buf.writeDouble(this.absZ);
        buf.writeShort(this.offset);
    }

    public static Vector3f colorSelect(int t)
    {
        return switch (t % 3) {
            case 0 -> new Vector3f(1.0f, 0.0f, 0.0f);
            case 1 -> new Vector3f(1.0f, 1.0f, 0.0f);
            default -> new Vector3f(1.0f, 0.647f, 0.0f);
        };
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // This code is run on the client side
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
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
            });
        });

        ctx.get().setPacketHandled(true);
    }
}
