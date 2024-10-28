package net.sonicrushxii.chaos_emerald.entities.blue;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.*;

public class IceSpike extends Entity {
    private int duration;
    protected Vec3 movementDirection;

    public Vec3 getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(Vec3 movementDirection) {
        this.movementDirection = movementDirection;
    }

    private Deque<BlockPos> icePositions = new ArrayDeque<>(50);

    public IceSpike(EntityType<? extends Entity> type, Level world) {
        super(type, world);
        this.duration = 40;
        this.movementDirection = new Vec3(0,0,0);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Duration")) {
            this.duration = tag.getInt("Duration");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Duration", this.duration);
    }

    @Override
    public void tick()
    {
        super.tick();

        this.setDeltaMovement(movementDirection);
        this.move(MoverType.SELF, this.getDeltaMovement());

        //Stop Moving at Dur 0
        if(duration == 0) {
            this.setMovementDirection(new Vec3(0, 0, 0));
            this.setDeltaMovement(0,0,0);
        }

        //While Duration is Positive Spawn Ice
        if(duration > 0)
        {
            this.level().setBlock(this.blockPosition(), Blocks.PACKED_ICE.defaultBlockState(), 3);
            icePositions.addLast(this.blockPosition());
            // Check for wall collision
            // Check for vertical collision
            if (this.horizontalCollision)                                this.duration = 0;
            if (this.verticalCollision && this.getDeltaMovement().y > 0) this.duration = 0;
        }
        //While Duration is in Negative take Break and then destroy Ice
        else if(!this.icePositions.isEmpty() && duration < -40)
        {
            BlockPos icePosition = this.icePositions.removeFirst();
            if(this.level().getBlockState(icePosition).getBlock() == Blocks.PACKED_ICE)
                this.level().destroyBlock(icePosition,false);
        }

        // Custom tick logic - Here, it just counts down the duration and removes the entity
        this.duration--;
        if(this.icePositions.isEmpty() && duration < 0) {
            this.discard(); // Removes the entity from the world
        }

        //Particle
        if(this.level().isClientSide)
            this.level().addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY() + 0.5, this.getZ(), 0, 0, 0);

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
