package net.vg.fishingfrenzy.entity.mob;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.animal.fish.AbstractSchoolingFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.Level;
import net.vg.fishingfrenzy.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public abstract class BreedableSchoolingFishEntity extends AbstractSchoolingFish {
    private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(BreedableSchoolingFishEntity.class, EntityDataSerializers.BOOLEAN);
    private static final int BABY_START_AGE = -24000;
    private UUID lovingPlayer;


    private int loveTicks;
    public void setLoveTicks(int loveTicks) {
        this.loveTicks = loveTicks;
    }
    public int getLoveTicks() {
        return this.loveTicks;
    }



    private int breedingAge;
    public int getBreedingAge() {
        return this.breedingAge;
    }
    public void setBreedingAge(int age) {
        Constants.LOGGER.debug("Setting breeding age: " + age);
        this.breedingAge = age;
        this.entityData.set(DATA_BABY_ID, age < 0);
    }
    private int forcedAge;
    private int happyTicksRemaining;

    public BreedableSchoolingFishEntity(EntityType<? extends AbstractSchoolingFish> entityType, Level level) {
        super(entityType, level);
    }

    public abstract boolean isBreedingItem(ItemStack stack);
    public void setBaby(boolean baby) {
        Constants.LOGGER.debug("Setting baby: " + baby);
        this.setBreedingAge(baby ? BABY_START_AGE : 0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder arg) {
        super.defineSynchedData(arg);
        arg.define(DATA_BABY_ID, false);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.getBreedingAge() != 0) {
            this.loveTicks = 0;
        }

        if (this.loveTicks > 0) {
            this.loveTicks--;
            if (this.loveTicks % 10 == 0) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;

                this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, e, f);
            }
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (this.isBreedingItem(itemStack)) {
            if (!this.level().isClientSide() && this.getBreedingAge() == 0 && this.canEat()) {
                this.eat(player, interactionHand, itemStack);
                this.lovePlayer(player);
                return InteractionResult.SUCCESS;
            }
            if (this.isBaby()) {
                this.eat(player, interactionHand, itemStack);
                this.growUp(toGrowUpAge(-this.getBreedingAge()), true);
                return InteractionResult.SUCCESS;
            }
            if (this.level().isClientSide()) {
                return InteractionResult.CONSUME;
            }
        }
        return super.mobInteract(player, interactionHand);
    }

    protected void eat(Player player, InteractionHand hand, ItemStack stack) {
        stack.consume(1, player);
    }
    public boolean isBaby() {
        return this.entityData.get(DATA_BABY_ID);
    }
    public boolean canEat() {
        return this.loveTicks <= 0;
    }
    public void lovePlayer(Player player) {
        this.loveTicks = 600;
        if (player != null) {
            this.lovingPlayer = player.getUUID();
        }

        this.level().broadcastEntityEvent(this, (byte)18);
    }
    public static int toGrowUpAge(int breedingAge) {
        return (int)((float)(breedingAge / 20) * 0.1F);
    }
    public void growUp(int age, boolean overGrow) {
        int i = this.getBreedingAge();
        i += age * 20;
        if (i > 0) {
            i = 0;
        }

        int k = i - i;
        this.setBreedingAge(i);
        if (overGrow) {
            this.forcedAge += k;
            if (this.happyTicksRemaining == 0) {
                this.happyTicksRemaining = 40;
            }
        }

        if (this.getBreedingAge() == 0) {
            this.setBreedingAge(this.forcedAge);
        }
    }
    public void growUp(int age) {
        this.growUp(age, false);
    }


    public ServerPlayer getLovingPlayer() {
        if (this.lovingPlayer == null) {
            return null;
        } else {
            Player player = this.level().getPlayerByUUID(this.lovingPlayer);
            return player instanceof ServerPlayer ? (ServerPlayer) player : null;
        }
    }

    public boolean isInLove() {
        return this.loveTicks > 0;
    }
    public void resetLoveTicks() {
        this.loveTicks = 0;
    }
    public boolean canBreedWith(BreedableSchoolingFishEntity other) {
        if (other == this) {
            return  false;
        } else {
            return other.getClass() == this.getClass() && this.isInLove() && other.isInLove();
        }
    }

    public abstract BreedableSchoolingFishEntity createChild(ServerLevel level, BreedableSchoolingFishEntity entity);

    public void breed(ServerLevel level, BreedableSchoolingFishEntity other) {
        BreedableSchoolingFishEntity baby = this.createChild(level, other);
        if (baby != null) {
            Constants.LOGGER.debug("Baby Created");
            baby.setBaby(true);
            Constants.LOGGER.debug("Baby set to baby");
            baby.snapTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            this.breed(level, other, baby);
            level.addFreshEntityWithPassengers(baby);
        } else {
            Constants.LOGGER.debug("Baby was null, not spawning");
        }
    }

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
                level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
            }
        }
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Age", this.getBreedingAge());
        output.putInt("ForcedAge", this.forcedAge);
        output.putInt("InLove", this.loveTicks);
        output.storeNullable("LoveCause", UUIDUtil.STRING_CODEC, this.lovingPlayer);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setBreedingAge(input.getIntOr("Age", 0));
        this.forcedAge = input.getIntOr("ForcedAge", 0);
        this.loveTicks = input.getIntOr("InLove", 0);
        this.lovingPlayer = input.read("LoveCause", UUIDUtil.STRING_CODEC).orElse(null);
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == (byte)18) {
            for (int i = 0; i < 7; i++) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;

                this.level().addParticle(ParticleTypes.HEART, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, e, f);
            }
        } else {
            super.handleEntityEvent(b);
        }
    }
}
