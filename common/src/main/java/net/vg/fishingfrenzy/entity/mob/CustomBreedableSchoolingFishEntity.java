package net.vg.fishingfrenzy.entity.mob;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.fish.AbstractSchoolingFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.Level;
import net.vg.fishingfrenzy.entity.goal.BreedableSchoolFishMateGoal;
import net.vg.fishingfrenzy.item.fish.FishRegistry;

import java.util.Optional;

public class CustomBreedableSchoolingFishEntity extends BreedableSchoolingFishEntity {
    private FishRegistry fishRegistry;

    private final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public CustomBreedableSchoolingFishEntity(EntityType<? extends BreedableSchoolingFishEntity> entityType, Level level, FishRegistry fishRegistry) {
        super(entityType, level);
        this.fishRegistry = fishRegistry;
        this.registerCustomGoals();
    }

    private void registerCustomGoals() {
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.250, arg -> arg.is(fishRegistry.getBreedingItem()), false));
        this.goalSelector.addGoal(1, new BreedableSchoolFishMateGoal(this, 1.0));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 4f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));

        if (fishRegistry.shouldAttack()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
        }
    }

    public void setFishRegistry(FishRegistry fishRegistry) {
        this.fishRegistry = fishRegistry;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected void dropFromLootTable(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
        super.dropFromLootTable(serverLevel, damageSource, bl);
        this.spawnAtLocation(serverLevel, fishRegistry.getFishName().equals("cobblestone_fish")
                ? Items.COBBLESTONE
                : fishRegistry.getFishRegistry().get());

        for (Item item : fishRegistry.getAdditionalDrops()) {
            this.spawnAtLocation(serverLevel, item);
        }
    }


    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }

    @Override
    public ItemStack getBucketItemStack() {
        if (fishRegistry != null && fishRegistry.getBucketItemRegistry() != null) {
            return new ItemStack(fishRegistry.getBucketItemRegistry().get());
        }
        return new ItemStack(Items.WATER_BUCKET);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = RandomSource.create().nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float f) {
        float g = this.getPose() == Pose.SWIMMING ? Math.min(f * 6.0f, 1.0f) : 0.0f;
        this.walkAnimation.update(f, 0.2f, 1.0f);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.is(fishRegistry.getBreedingItem());
    }

    @Override
    public BreedableSchoolingFishEntity createChild(ServerLevel level, BreedableSchoolingFishEntity entity) {
        CustomBreedableSchoolingFishEntity baby = (CustomBreedableSchoolingFishEntity) this.getType().create(level, EntitySpawnReason.BREEDING);
        if (baby != null) {
            baby.setBaby(true);
        }
        return baby;
    }

    @Override
    public void breed(ServerLevel level, BreedableSchoolingFishEntity other) {
        BreedableSchoolingFishEntity baby = this.createChild(level, other);
        if (baby != null) {
            baby.setBaby(true);
            baby.snapTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            this.breed(level, other, baby);
            level.addFreshEntityWithPassengers(baby);
        }
    }

    @Override
    public void breed(ServerLevel level, BreedableSchoolingFishEntity other, BreedableSchoolingFishEntity baby) {
        if (baby != null) {
            Optional.ofNullable(this.getLovingPlayer()).or(() -> Optional.ofNullable(other.getLovingPlayer())).ifPresent(player -> {
                player.awardStat(Stats.ANIMALS_BRED);
            });
            this.setBreedingAge(6000);
            other.setBreedingAge(6000);
            this.resetLoveTicks();
            other.resetLoveTicks();
            this.level().broadcastEntityEvent(this, (byte)18);
            if (level.getGameRules().get(GameRules.MOB_DROPS)) {
                level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), RandomSource.create().nextInt(7) + 1));
            }
        }
    }
}
