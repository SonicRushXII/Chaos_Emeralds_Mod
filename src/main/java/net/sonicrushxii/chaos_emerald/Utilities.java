package net.sonicrushxii.chaos_emerald;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Consumer;

public class Utilities {

    public static Random random = new Random();

    public static final HashSet<String> passableBlocks =
            new HashSet<>(Arrays.asList(
                    "minecraft:air",
                    "minecraft:void_air",
                    "minecraft:cave_air",
                    "minecraft:oak_sapling",
                    "minecraft:spruce_sapling",
                    "minecraft:birch_sapling",
                    "minecraft:jungle_sapling",
                    "minecraft:acacia_sapling",
                    "minecraft:dark_oak_sapling",
                    "minecraft:beetroots",
                    "minecraft:water",
                    "minecraft:lava",
                    "minecraft:light",
                    "minecraft:powered_rail",
                    "minecraft:detector_rail",
                    "minecraft:seagrass",
                    "minecraft:tall_seagrass",
                    "minecraft:grass",
                    "minecraft:fern",
                    "minecraft:dead_bush",
                    "minecraft:dandelion",
                    "minecraft:poppy",
                    "minecraft:blue_orchid",
                    "minecraft:allium",
                    "minecraft:azure_bluet",
                    "minecraft:red_tulip" ,
                    "minecraft:orange_tulip" ,
                    "minecraft:white_tulip" ,
                    "minecraft:pink_tulip" ,
                    "minecraft:oxeye_daisy" ,
                    "minecraft:cornflower" ,
                    "minecraft:wither_rose",
                    "minecraft:lily_of_the_valley",
                    "minecraft:brown_mushroom",
                    "minecraft:red_mushroom",
                    "minecraft:torch",
                    "minecraft:wall_torch",
                    "minecraft:fire",
                    "minecraft:soul_fire",
                    "minecraft:wheat",
                    "minecraft:wall_torch",
                    "minecraft:oak_sign",
                    "minecraft:spruce_sign",
                    "minecraft:birch_sign",
                    "minecraft:jungle_sign",
                    "minecraft:acacia_sign",
                    "minecraft:dark_oak_sign",
                    "minecraft:oak_pressure_plate",
                    "minecraft:spruce_pressure_plate",
                    "minecraft:birch_pressure_plate",
                    "minecraft:jungle_pressure_plate",
                    "minecraft:acacia_pressure_plate",
                    "minecraft:dark_oak_pressure_plate",
                    "minecraft:redstone",
                    "minecraft:restone_torch",
                    "minecraft:redstone_wall_torch",
                    "minecraft:repeater",
                    "minecraft:stone_button",
                    "minecraft:oak_button",
                    "minecraft:spruce_button",
                    "minecraft:birch_button",
                    "minecraft:jungle_button",
                    "minecraft:acacia_button",
                    "minecraft:dark_oak_button",
                    "minecraft:candle",
                    "minecraft:white_candle",
                    "minecraft:orange_candle",
                    "minecraft:magenta_candle",
                    "minecraft:light_blue_candle",
                    "minecraft:yellow_candle",
                    "minecraft:lime_candle",
                    "minecraft:pink_candle",
                    "minecraft:gray_candle",
                    "minecraft:light_gray_candle",
                    "minecraft:cyan_candle",
                    "minecraft:purple_candle",
                    "minecraft:blue_candle",
                    "minecraft:brown_candle",
                    "minecraft:green_candle",
                    "minecraft:red_candle",
                    "minecraft:black_candle",
                    "minecraft:structure_void",
                    "minecraft:kelp",
                    "minecraft:nether_wart",
                    "minecraft:ladder",
                    "minecraft:vines",
                    "minecraft:lever",
                    "minecraft:white_carpet",
                    "minecraft:orange_carpet",
                    "minecraft:magenta_carpet",
                    "minecraft:light_blue_carpet",
                    "minecraft:yellow_carpet",
                    "minecraft:lime_carpet",
                    "minecraft:pink_carpet",
                    "minecraft:gray_carpet",
                    "minecraft:light_gray_carpet",
                    "minecraft:cyan_carpet",
                    "minecraft:purple_carpet",
                    "minecraft:blue_carpet",
                    "minecraft:brown_carpet",
                    "minecraft:green_carpet",
                    "minecraft:red_carpet",
                    "minecraft:black_carpet",
                    "minecraft:white_banner",
                    "minecraft:orange_banner",
                    "minecraft:magenta_banner",
                    "minecraft:light_blue_banner",
                    "minecraft:yellow_banner",
                    "minecraft:lime_banner",
                    "minecraft:pink_banner",
                    "minecraft:gray_banner",
                    "minecraft:light_gray_banner",
                    "minecraft:cyan_banner",
                    "minecraft:purple_banner",
                    "minecraft:blue_banner",
                    "minecraft:brown_banner",
                    "minecraft:green_banner",
                    "minecraft:red_banner",
                    "minecraft:black_banner",
                    "minecraft:white_wall_banner",
                    "minecraft:orange_wall_banner",
                    "minecraft:magenta_wall_banner",
                    "minecraft:light_blue_wall_banner",
                    "minecraft:yellow_wall_banner",
                    "minecraft:lime_wall_banner",
                    "minecraft:pink_wall_banner",
                    "minecraft:gray_wall_banner",
                    "minecraft:light_gray_wall_banner",
                    "minecraft:cyan_wall_banner",
                    "minecraft:purple_wall_banner",
                    "minecraft:blue_wall_banner",
                    "minecraft:brown_wall_banner",
                    "minecraft:green_wall_banner",
                    "minecraft:red_wall_banner",
                    "minecraft:black_wall_banner",
                    "minecraft:sunflower",
                    "minecraft:lilac",
                    "minecraft:rose_bush",
                    "minecraft:peony",
                    "minecraft:tall_grass",
                    "minecraft:large_fern")
            );

    public static final HashSet<String> unbreakableBlocks =
            new HashSet<>(Arrays.asList(
                    "minecraft:bedrock",
                    "minecraft:obsidian",
                    "minecraft:end_portal_frame",
                    "chaos_emerald:master_emerald",
                    "chaos_emerald:chaos_emerald/aqua_emerald",
                    "chaos_emerald:chaos_emerald/blue_emerald",
                    "chaos_emerald:chaos_emerald/green_emerald",
                    "chaos_emerald:chaos_emerald/grey_emerald",
                    "chaos_emerald:chaos_emerald/purple_emerald",
                    "chaos_emerald:chaos_emerald/red_emerald",
                    "chaos_emerald:chaos_emerald/yellow_emerald",
                    "chaos_emerald:super_emerald/aqua_emerald",
                    "chaos_emerald:super_emerald/blue_emerald",
                    "chaos_emerald:super_emerald/green_emerald",
                    "chaos_emerald:super_emerald/grey_emerald",
                    "chaos_emerald:super_emerald/purple_emerald",
                    "chaos_emerald:super_emerald/red_emerald",
                    "chaos_emerald:super_emerald/yellow_emerald"
            )
            );
    //Server Side Find Block
    public static boolean isPlayerLookingAtBlockType(ServerPlayer player, Level world, String targetBlock, double maxDistance) {
        // Get player's eye position
        Vec3 eyePosition = player.getEyePosition(1.0F); // 1.0F for current tick

        // Get the player's view vector
        Vec3 lookVector = player.getLookAngle();

        // Calculate the end position of the ray
        Vec3 rayEnd = eyePosition.add(lookVector.scale(maxDistance));

        // Perform the ray trace
        HitResult hitResult = world.clip(new ClipContext(
                eyePosition,       // Start of the ray
                rayEnd,            // End of the ray
                ClipContext.Block.OUTLINE, // Target only blocks
                ClipContext.Fluid.NONE,    // Ignore fluids
                player             // Entity performing the ray trace
        ));

        // Check if the ray trace hit a block
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;

            // Get the BlockState at the hit position
            BlockState hitBlockState = world.getBlockState(blockHitResult.getBlockPos());

            // Check if the block matches the target block type
            return targetBlock.equals(
                    ""+ForgeRegistries.BLOCKS.getKey(hitBlockState.getBlock())
            );
        }

        return false; // No block was hit
    }

    /** Method to summon an entity in a given world at specified coordinates
     * Mob specifies the mob to be summon, Eg: EntityType.ZOMBIE
     * ServerLevel determines the world in which the entity is summoned
     * Vec3 tells us where to spawn the entity
     * entityConsumer allows the user to perform some actions on the entity
     * **/
    public static <T extends Entity> void summonEntity(EntityType<T> entityType, ServerLevel world, Vec3 pos, @Nullable Consumer<T> entityConsumer)
    {
        // Create an instance of the entity
        T entity = entityType.create(world);

        if (entity != null) {
            // Set the entity's position
            entity.setPos(pos.x(), pos.y(), pos.z());

            //Perform the given operations on the Entity
            if(entityConsumer!=null) entityConsumer.accept(entity);

            // Add the entity to the world
            world.addFreshEntity(entity);
        }
    }


    //User Defined Functions for things that should be available
    public static Vec3 calculateViewVector(float pXRot, float pYRot)
    {
        float f = pXRot * ((float)Math.PI / 180F);
        float f1 = -pYRot * ((float)Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((f3 * f4), (-f5), (f2 * f4));
    }

    public static float getYawFromVec(Vec3 vec) {
        double x = vec.x();
        double z = vec.z();

        return (float) (Math.atan2(z, x) * (180 / Math.PI));
    }

    public static float[] getYawPitchFromVec(Vec3 vec) {
        vec = vec.normalize();
        double x = vec.x();
        double y = vec.y();
        double z = vec.z();

        // Calculate yaw
        float yaw = (float) (Math.acos(z) * (180 / Math.PI));
        yaw = (x>0.0)?-yaw:yaw;

        // Calculate pitch
        float pitch = (float) (-Math.asin(y) * (180 / Math.PI));

        return new float[]{yaw, pitch};
    }

    public static float[] getYawPitchFromVecinRadian(Vec3 vec) {
        vec = vec.normalize();
        double x = vec.x();
        double y = vec.y();
        double z = vec.z();

        // Calculate yaw
        float yaw = (float) (Math.acos(z));
        yaw = (x>0.0)?-yaw:yaw;

        // Calculate pitch
        float pitch = (float) (-Math.asin(y));

        return new float[]{yaw, pitch};
    }

    public static float[] calculateFacing(Vec3 playerPos, Vec3 targetPos) {
        return getYawPitchFromVec(targetPos.subtract(playerPos));
    }

    public static BlockPos convertToBlockPos(Vec3 vec3)
    {
        // Convert the Vec3 coordinates to integer coordinates for the BlockPos
        int x = (int)Math.floor(vec3.x);
        int y = (int)Math.floor(vec3.y);
        int z = (int)Math.floor(vec3.z);

        // Create and return the new BlockPos object with these coordinates
        return new BlockPos(x, y, z);
    }

    public static void displayParticle(Level world , ParticleOptions particleType,
                                       double absX, double absY, double absZ,
                                       float radiusX, float radiusY, float radiusZ,
                                       double speedX, double speedY, double speedZ,
                                       int count, boolean force) {

        for (int i = 0; i < count; i++)
        {
            double x = random.nextGaussian();
            double y = random.nextGaussian();
            double z = random.nextGaussian();

            double nf = Math.sqrt(x*x + y*y + z*z);

            x = (x/nf)*radiusX;
            y = (y/nf)*radiusY;
            z = (z/nf)*radiusZ;

            // Spawn the particle effect
            world.addParticle(particleType, force,
                    x + (absX),
                    y + (absY),
                    z + (absZ),
                    speedX, speedY, speedZ);
        }
    }

    public static void displayParticle(Level world , ParticleOptions particleType,
                                       double absX, double absY, double absZ,
                                       float radiusX, float radiusY, float radiusZ,
                                       double speed,
                                       int count, boolean force) {
        for (int i = 0; i < count; i++) {

            double x = random.nextGaussian();
            double y = random.nextGaussian();
            double z = random.nextGaussian();

            double nf = Math.sqrt(x*x + y*y + z*z);

            x = (x/nf)*radiusX;
            y = (y/nf)*radiusY;
            z = (z/nf)*radiusZ;

            // Calculate the particle's initial position
            double particleX = x + (absX);
            double particleY = y + (absY);
            double particleZ = z + (absZ);

            // Calculate the direction vector from the origin to the particle
            double dirX = particleX - x;
            double dirY = particleY - y;
            double dirZ = particleZ - z;

            // Normalize the direction vector
            double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
            if (length != 0) {
                dirX /= length;
                dirY /= length;
                dirZ /= length;
            }

            // Scale the direction vector by the desired speed
            double speedX = dirX * speed;
            double speedY = dirY * speed;
            double speedZ = dirZ * speed;

            // Spawn the particle effect
            world.addParticle(particleType, force,
                    particleX, particleY, particleZ,
                    speedX, speedY, speedZ);
        }
    }

    public static void particleRaycast(Level world, ParticleOptions particleType,
                                        Vec3 pos1, Vec3 pos2)
    {
        // Calculate the vector from pos1 to pos2
        Vec3 direction = pos2.subtract(pos1);

        double distance = direction.length();
        Vec3 directionNormalized = direction.normalize();

        for (int i = 0; i <= (int) distance*3; i++) {
            Vec3 point = pos1.add(directionNormalized.scale((i+1)/3.0));
            world.addParticle(particleType,
                    point.x, point.y, point.z,
                    0, 0, 0);
        }
    }
}
