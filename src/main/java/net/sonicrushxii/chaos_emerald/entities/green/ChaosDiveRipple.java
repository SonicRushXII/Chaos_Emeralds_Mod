package net.sonicrushxii.chaos_emerald.entities.green;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonicrushxii.chaos_emerald.Utilities;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.SyncEntityMotionS2C;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ChaosDiveRipple extends Entity{
    public static final EntityDataAccessor<Boolean> GROUND_LEVEL = SynchedEntityData.defineId(ChaosDiveRipple.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(ChaosDiveRipple.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MAX_DURATION = SynchedEntityData.defineId(ChaosDiveRipple.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(ChaosDiveRipple.class, EntityDataSerializers.OPTIONAL_UUID);

    private float currentRadius;
    private float incrementSpeed;

    public ChaosDiveRipple(EntityType<? extends Entity> type, Level world) {
        super(type, world);
        this.currentRadius = 0.0f;
        this.incrementSpeed = 0.40f;
    }

    public int getDuration() {
        return this.entityData.get(DURATION);
    }
    public int getMAXDuration() {
        return this.entityData.get(MAX_DURATION);
    }

    public void setDuration(int duration) {
        this.entityData.set(MAX_DURATION,duration);
        this.entityData.set(DURATION, duration);
    }

    public void decrementDuration()
    {
        this.entityData.set(DURATION, this.getDuration()-1);
    }

    // Sets the owner by UUID
    public void setOwner(UUID ownerUuid) {
        this.entityData.set(OWNER, Optional.of(ownerUuid));
    }

    // Retrieves the owner's UUID, if it exists
    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER).orElse(null);
    }

    // Gets the actual owner Entity, if they are loaded in the world
    @Nullable
    public LivingEntity getOwner()
    {
        UUID ownerUuid = getOwnerUUID();
        if (ownerUuid != null) {
            return this.level().getPlayerByUUID(ownerUuid);
        }
        return null;
    }

    public boolean isOwnedBy(Player player) {
        return player.getUUID().equals(this.getOwnerUUID());
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(GROUND_LEVEL, false);
        this.entityData.define(DURATION, 40);
        this.entityData.define(MAX_DURATION, 40);
        this.entityData.define(OWNER,Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag)
    {
        // Load the owner's UUID
        if (tag.hasUUID("OwnerUUID")) this.setOwner(tag.getUUID("OwnerUUID"));

        //Load Other details
        if(tag.contains("Duration")){
            setDuration(tag.getInt("Duration"));
        }
        // Destroy Blocks
        if(tag.contains("LevelGround")) setLevelGround(tag.getBoolean("DropBlocks"));

        if(tag.contains("CurrentRadius")) this.currentRadius = tag.getFloat("CurrentRadius");
        if(tag.contains("IncrementSpeed")) this.incrementSpeed = tag.getFloat("IncrementSpeed");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        // Save the owner's UUID
        UUID ownerUuid = getOwnerUUID();
        if (ownerUuid != null) {
            tag.putUUID("OwnerUUID", ownerUuid);
        }

        tag.putBoolean("LevelGround", canLevelGround());
        tag.putInt("Duration", getDuration());
        tag.putFloat("CurrentRadius",this.currentRadius);
        tag.putFloat("IncrementSpeed",this.incrementSpeed);
    }

    public boolean canLevelGround() {
        return this.entityData.get(GROUND_LEVEL);
    }

    public void setLevelGround(boolean levelGround) {
        this.entityData.set(GROUND_LEVEL, levelGround);
    }

    @Override
    public void tick()
    {
        super.tick();

        int ticksElapsed = this.getMAXDuration()-this.getDuration();
        double radius = this.incrementSpeed*ticksElapsed;

        for(int i=0; i<10*ticksElapsed; ++i)
        {
            double theta = i*(Math.PI/(5*ticksElapsed));
            if(this.level().isClientSide)
            {
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,(i%5==0),
                        this.getX()+radius*Math.sin(theta), this.getY(), this.getZ()+radius*Math.cos(theta),
                        0.0,0.0,0.0);
                this.level().addParticle(new DustParticleOptions(new Vector3f(0f,1f,0f),1f),(i%3==0),
                        this.getX()+radius*Math.sin(theta), this.getY(), this.getZ()+radius*Math.cos(theta),
                        0.0,0.0,0.0);
            }
            else
            {
                //Deal Damage
                Vec3 destPos = new Vec3(this.getX()+radius*Math.sin(theta),
                                        this.getY(),
                                    this.getZ()+radius*Math.cos(theta));

                for(LivingEntity enemy : this.level().getEntitiesOfClass(LivingEntity.class, new AABB(
                                destPos.x+1.0, destPos.y+1.0, destPos.z+1.0,
                                destPos.x-1.0, destPos.y-1.0, destPos.z-1.0
                        ), (enemy)->(!enemy.getUUID().equals(this.getOwnerUUID()))
                ))
                {
                    //Damage
                    enemy.hurt(Objects.requireNonNull(this.getOwner()).damageSources().playerAttack((Player) this.getOwner()), 2.0f);

                    //Updated Motion
                    Vec3 motionDirection = (new Vec3(enemy.getX(),enemy.getY(),enemy.getZ())).subtract(new Vec3(this.getX(),enemy.getY(),this.getZ())).normalize();
                    enemy.setDeltaMovement(motionDirection);
                    PacketHandler.sendToALLPlayers(new SyncEntityMotionS2C(enemy.getId(),motionDirection));
                }

                //Level the Ground
                if(this.canLevelGround()) {
                    BlockPos currBlockPos = Utilities.convertToBlockPos(destPos);
                    BlockPos start = currBlockPos.offset(0, 0, 0);
                    BlockPos end = currBlockPos.offset(0, 3, 0);

                    for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
                        BlockState blockState = this.level().getBlockState(pos);
                        if (!Utilities.unbreakableBlocks.contains(ForgeRegistries.BLOCKS.getKey(blockState.getBlock()) + ""))
                            this.level().destroyBlock(pos, false);
                    }
                }
            }
        }

        //Decrease Duration
        this.decrementDuration();

        //Kill Once Entity Expires
        if(this.getDuration() < 0)
            this.kill();
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
