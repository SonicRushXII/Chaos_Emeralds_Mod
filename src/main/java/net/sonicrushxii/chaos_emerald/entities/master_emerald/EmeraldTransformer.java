package net.sonicrushxii.chaos_emerald.entities.master_emerald;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.entities.all.PointEntity;
import net.sonicrushxii.chaos_emerald.modded.ModBlocks;

public class EmeraldTransformer extends PointEntity {
    public static final EntityDataAccessor<Byte> TRANSFORM_TYPE = SynchedEntityData.defineId(EmeraldTransformer.class, EntityDataSerializers.BYTE);

    public static int[] getFireworkColors(EmeraldType emeraldType)
    {
        //First two is Main Colors, Next to is Faded Colors
        return switch (emeraldType) {
            case AQUA_EMERALD   -> new int[]{0x00FFFF, 0xFFFFFF, 0x7FFFFF, 0xFFFFFF}; // Aqua, White, Light Aqua, White
            case BLUE_EMERALD   -> new int[]{0x0000FF, 0xFFFFFF, 0x7F7FFF, 0xFFFFFF}; // Blue, White, Light Blue, White
            case GREEN_EMERALD  -> new int[]{0x00FF00, 0xFFFFFF, 0x7FFF7F, 0xFFFFFF}; // Green, White, Light Green, White
            case GREY_EMERALD   -> new int[]{0xAAAAAA, 0xFFFFFF, 0xDDDDDD, 0xFFFFFF}; // Grey, White, Light Grey, White
            case PURPLE_EMERALD -> new int[]{0x800080, 0xFFFFFF, 0xC080C0, 0xFFFFFF}; // Purple, White, Lavender, White
            case RED_EMERALD    -> new int[]{0xFF0000, 0xFFFFFF, 0xFF7F7F, 0xFFFFFF}; // Red, White, Light Red, White
            case YELLOW_EMERALD -> new int[]{0xFFFF00, 0xFFFFFF, 0xFFFF7F, 0xFFFFFF}; // Yellow, White, Light Yellow, White
        };
    }

    public static Block getSuperEmeraldType(EmeraldType emeraldType)
    {
        //First two is Main Colors, Next to is Faded Colors
        return switch (emeraldType) {
            case AQUA_EMERALD   -> ModBlocks.AQUA_SUPER_EMERALD.get();
            case BLUE_EMERALD   -> ModBlocks.BLUE_SUPER_EMERALD.get();
            case GREEN_EMERALD  -> ModBlocks.GREEN_SUPER_EMERALD.get();
            case GREY_EMERALD   -> ModBlocks.GREY_SUPER_EMERALD.get();
            case PURPLE_EMERALD -> ModBlocks.PURPLE_SUPER_EMERALD.get();
            case RED_EMERALD    -> ModBlocks.RED_SUPER_EMERALD.get();
            case YELLOW_EMERALD -> ModBlocks.YELLOW_SUPER_EMERALD.get();
        };
    }

    public EmeraldTransformer(EntityType<? extends EmeraldTransformer> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TRANSFORM_TYPE, (byte) 0); // Default value for transformType is 0
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("TransformType"))
            this.entityData.set(TRANSFORM_TYPE, tag.getByte("TransformType"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte("TransformType", this.entityData.get(TRANSFORM_TYPE));
    }

    public byte getTransformType() {
        return this.entityData.get(TRANSFORM_TYPE);
    }

    public void setTransformType(byte transformType) {
        this.entityData.set(TRANSFORM_TYPE, transformType);
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide)
        {
            double offset = (this.getDuration()%360)*(Math.PI/180);

            for (double theta = 0; theta < 2*Math.PI; theta += Math.PI/12)
            {
                final double v0 = this.getDuration()*Math.sin(offset+theta)/25;
                final double v2 = this.getDuration()*Math.cos(offset+theta)/25;

                double particleX = this.getX() + v0;
                double particleY = this.getY();
                double particleZ = this.getZ() + v2;

                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                        false,
                        particleX, particleY, particleZ,
                        0, 0, 0);
            }
        }

        if(this.getDuration() == 1 && this.level() instanceof ServerLevel world)
        {
            //Spawn Firework
            {
                CommandSourceStack sourceStack = new CommandSourceStack(
                        CommandSource.NULL, // No real sender
                        new Vec3(this.getX(),this.getY(),this.getZ()), // Default position (world spawn or any desired location)
                        Vec2.ZERO, // Default rotation
                        world, // The level to execute the command in
                        4, // Permission level (4 = server/operator level)
                        "EmeraldTransform", // Display name
                        Component.literal("EmeraldTransform"), // Chat component for feedback
                        world.getServer(), // Reference to the Minecraft server
                        null // No specific entity tied to this source
                );

                int[] fireworkColors = getFireworkColors(
                        EmeraldType.values()[getTransformType()%7]
                );

                // Execute the command using the server's command dispatcher
                world.getServer()
                        .getCommands()
                        .performPrefixedCommand
                                (sourceStack,
                                        String.format("summon firework_rocket ~ ~ ~ {Life:0,LifeTime:0,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:0,Flicker:1b,Colors:[I;%d,%d],FadeColors:[I;%d,%d]}]}}}}",
                                                fireworkColors[0],fireworkColors[1],fireworkColors[2],fireworkColors[3]));

                //Transform Block
                BlockPos blockPos     =     new BlockPos((int)(this.getX()-0.5),(int)(this.getY()-0.5),(int)(this.getZ()-0.5));
                BlockState blockState =     world.getBlockState(blockPos);
                String blockString    =     ForgeRegistries.BLOCKS.getKey(blockState.getBlock())+"";

                if(blockString.startsWith("chaos_emerald:chaos_emerald"))
                {
                    world.setBlock(blockPos,getSuperEmeraldType(
                                    EmeraldType.values()[getTransformType()%7]
                            ).defaultBlockState(),
                            3);
                }
            }

        }
    }
}
