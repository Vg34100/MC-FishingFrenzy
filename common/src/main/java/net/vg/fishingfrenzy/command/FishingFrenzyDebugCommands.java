package net.vg.fishingfrenzy.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnPlacements;
import net.vg.fishingfrenzy.entity.mob.CustomBreedableSchoolingFishEntity;
import net.vg.fishingfrenzy.item.fish.FishManager;
import net.vg.fishingfrenzy.item.fish.FishRegistry;
import net.vg.fishingfrenzy.world.ModEntitySpawns;

/**
 * Operator-only diagnostics for checking the exact spawn path used by natural fish spawning.
 * This deliberately does not use /summon: /summon bypasses placement and spawn predicates.
 */
public final class FishingFrenzyDebugCommands {
    private FishingFrenzyDebugCommands() {
    }

    public static void register() {
        CommandRegistrationEvent.EVENT.register((dispatcher, context, selection) -> {
            LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("fishingfrenzy")
                    .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS));
            root.then(Commands.literal("debug")
                    .then(Commands.literal("test_spawn")
                            .then(Commands.argument("fish", StringArgumentType.word())
                                    .then(Commands.argument("position", BlockPosArgument.blockPos())
                                            .executes(FishingFrenzyDebugCommands::testSpawn)))));
            dispatcher.register(root);
        });
    }

    private static int testSpawn(CommandContext<CommandSourceStack> context) {
        String fishName = StringArgumentType.getString(context, "fish");
        FishRegistry fish = FishManager.FISH_REGISTRY_MAP.get(fishName);
        if (fish == null) {
            context.getSource().sendFailure(Component.literal("Unknown Fishing Frenzy fish: " + fishName));
            return 0;
        }

        BlockPos pos = BlockPosArgument.getBlockPos(context, "position");
        ServerLevel level = context.getSource().getLevel();
        boolean placementOk = SpawnPlacements.isSpawnPositionOk(fish.getFishEntityTypeDirectly(), level, pos);
        int connectedWater = ModEntitySpawns.countConnectedWaterBlocks(level, pos);
        String failedRule = ModEntitySpawns.getSpawnRuleFailure(
                fish.getFishEntityTypeDirectly(), level, EntitySpawnReason.NATURAL, pos, RandomSource.create(), fish);
        boolean rulesOk = failedRule == null;

        String result = fishName + " natural-spawn test at " + pos.toShortString()
                + ": placement=" + placementOk + ", connected_water="
                + (connectedWater >= ModEntitySpawns.MINIMUM_CONNECTED_WATER_BLOCKS
                ? ModEntitySpawns.MINIMUM_CONNECTED_WATER_BLOCKS + "+"
                : connectedWater)
                + ", fish_rules=" + rulesOk
                + (failedRule == null ? "" : " (failed=" + failedRule + ")");
        if (!placementOk || !rulesOk) {
            context.getSource().sendSuccess(() -> Component.literal(result + "; no fish created."), false);
            return 0;
        }

        CustomBreedableSchoolingFishEntity entity = fish.getFishEntityTypeDirectly().create(level, EntitySpawnReason.NATURAL);
        if (entity == null) {
            context.getSource().sendFailure(Component.literal(result + "; entity creation unexpectedly failed."));
            return 0;
        }

        entity.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        level.addFreshEntity(entity);
        context.getSource().sendSuccess(() -> Component.literal(result + "; spawned one fish."), true);
        return 1;
    }
}
