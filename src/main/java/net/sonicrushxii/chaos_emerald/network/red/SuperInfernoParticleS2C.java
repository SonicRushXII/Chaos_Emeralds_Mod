package net.sonicrushxii.chaos_emerald.network.red;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class SuperInfernoParticleS2C
{
    private final double absX, absY, absZ;
    private final byte tickAmt;

    public SuperInfernoParticleS2C(double absX, double absY, double absZ, byte tickAmt) {
        this.absX = absX;
        this.absY = absY;
        this.absZ = absZ;
        this.tickAmt = tickAmt;
    }

    public SuperInfernoParticleS2C(FriendlyByteBuf buf) {
        this.absX = buf.readDouble();
        this.absY = buf.readDouble();
        this.absZ = buf.readDouble();
        this.tickAmt = buf.readByte();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(this.absX);
        buf.writeDouble(this.absY);
        buf.writeDouble(this.absZ);
        buf.writeByte(this.tickAmt);
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
                    int t=0;
                    for(float p = 0; p<= 10.0F ; p += 0.8F)
                    {
                        for(double offset = 0; offset <= 2*Math.PI; offset += Math.PI/2)
                        {
                            double particleX = 0.5*p*Math.sin(tickAmt/4.0F+offset+p/10);
                            double particleZ = 0.5*p*Math.cos(tickAmt/4.0F+offset+p/10);

                            Utilities.displayParticle(world,
                                    new DustParticleOptions(colorSelect(t++),1f),
                                    absX+particleX,absY+p/5.0,absZ+particleZ,
                                    0.25f-(0.025f)*p,0.25f-(0.025f)*p,0.25f-(0.025f)*p,
                                    0.001, 2, false
                            );
                            Utilities.displayParticle(world,
                                    ParticleTypes.FLAME,
                                    absX+particleX,absY+p/5.0,absZ+particleZ,
                                    0.25f-(0.025f)*p,0.25f-(0.025f)*p,0.25f-(0.025f)*p,
                                    0.001, 3, false
                            );
                        }
                    }
                }
            });
        });

        ctx.get().setPacketHandled(true);
    }
}
