package net.vg.fishingfrenzy.registry;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.vg.fishingfrenzy.Constants;
import net.vg.fishingfrenzy.entity.client.AlbacoreModel;
import net.vg.fishingfrenzy.entity.client.AnchovyModel;
import net.vg.fishingfrenzy.entity.client.BlueDiscusModel;
import net.vg.fishingfrenzy.entity.client.BonefishModel;
import net.vg.fishingfrenzy.entity.client.BreamModel;
import net.vg.fishingfrenzy.entity.client.BullheadModel;
import net.vg.fishingfrenzy.entity.client.CatfishModel;
import net.vg.fishingfrenzy.entity.client.WoodskipModel;
import net.vg.fishingfrenzy.entity.client.CarpModel;
import net.vg.fishingfrenzy.entity.client.CobblestoneFishModel;
import net.vg.fishingfrenzy.entity.client.DoradoModel;
import net.vg.fishingfrenzy.entity.client.EelModel;
import net.vg.fishingfrenzy.entity.client.TunaModel;
import net.vg.fishingfrenzy.entity.client.VoidSalmonModel;
import net.vg.fishingfrenzy.entity.client.WalleyeModel;
import net.vg.fishingfrenzy.entity.mob.CustomBreedableSchoolingFishEntity;
import net.vg.fishingfrenzy.item.fish.FishManager;
import net.vg.fishingfrenzy.item.fish.FishRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DynamicFishSystem {
    private static final Map<String, Class<? extends EntityModel<LivingEntityRenderState>>> FISH_MODELS = Map.ofEntries(
            Map.entry("anchovy", AnchovyModel.class),
            Map.entry("albacore", AlbacoreModel.class),
            Map.entry("blue_discus", BlueDiscusModel.class),
            Map.entry("bream", BreamModel.class),
            Map.entry("bullhead", BullheadModel.class),
            Map.entry("catfish", CatfishModel.class),
            Map.entry("woodskip", WoodskipModel.class),
            Map.entry("bonefish", BonefishModel.class),
            Map.entry("carp", CarpModel.class),
            Map.entry("cobblestone_fish", CobblestoneFishModel.class),
            Map.entry("dorado", DoradoModel.class),
            Map.entry("eel", EelModel.class),
            Map.entry("tuna", TunaModel.class),
            Map.entry("void_salmon", VoidSalmonModel.class),
            Map.entry("walleye", WalleyeModel.class)
    );

    public static void registerAllFish() {
        Constants.LOGGER.info("Registering dynamic fish");
        getFishRenderDefinitions().forEach(definition -> {
            EntityRendererRegistry.register(definition.fishRegistry.getFishEntityType(), definition::createRenderer);
            EntityModelLayerRegistry.register(definition.modelLayer, definition::createLayerDefinition);
        });
        Constants.LOGGER.info("Registered dynamic fish");
    }

    public static List<FishRenderDefinition> getFishRenderDefinitions() {
        List<FishRenderDefinition> definitions = new ArrayList<>();
        FishManager.FISH_REGISTRIES.forEach(fishRegistry -> {
            Class<? extends EntityModel<LivingEntityRenderState>> modelClass = FISH_MODELS.get(fishRegistry.getFishName());
            if (modelClass != null) {
                definitions.add(new FishRenderDefinition(fishRegistry, modelClass));
            }
        });
        return definitions;
    }

    public static final class FishRenderDefinition {
        private final FishRegistry fishRegistry;
        private final Class<? extends EntityModel<LivingEntityRenderState>> modelClass;
        private final ModelLayerLocation modelLayer;

        private FishRenderDefinition(FishRegistry fishRegistry, Class<? extends EntityModel<LivingEntityRenderState>> modelClass) {
            this.fishRegistry = fishRegistry;
            this.modelClass = modelClass;
            this.modelLayer = new ModelLayerLocation(
                    Identifier.fromNamespaceAndPath(Constants.MOD_ID, fishRegistry.getFishName()), "main"
            );
        }

        public EntityType<CustomBreedableSchoolingFishEntity> getEntityType() {
            return fishRegistry.getFishEntityType().get();
        }

        public ModelLayerLocation getModelLayer() {
            return modelLayer;
        }

        public LayerDefinition createLayerDefinition() {
            try {
                Method method = modelClass.getMethod("createBodyLayer");
                return (LayerDefinition) method.invoke(null);
            } catch (Exception e) {
                throw new RuntimeException("Failed to get model data for " + fishRegistry.getFishName(), e);
            }
        }

        public EntityRenderer<CustomBreedableSchoolingFishEntity, ?> createRenderer(EntityRendererProvider.Context context) {
            return new FishRenderer(context, fishRegistry, modelClass, modelLayer);
        }
    }

    private static class FishRenderer extends MobRenderer<CustomBreedableSchoolingFishEntity, LivingEntityRenderState, EntityModel<LivingEntityRenderState>> {
        private final Identifier texture;

        public FishRenderer(EntityRendererProvider.Context context, FishRegistry fishRegistry,
                            Class<? extends EntityModel<LivingEntityRenderState>> modelClass, ModelLayerLocation modelLayer) {
            super(context, createModel(context, fishRegistry, modelClass, modelLayer), fishRegistry.getShadowRadius());
            this.texture = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/" + fishRegistry.getFishName() + ".png");
        }

        private static EntityModel<LivingEntityRenderState> createModel(EntityRendererProvider.Context context, FishRegistry fishRegistry,
                                                                         Class<? extends EntityModel<LivingEntityRenderState>> modelClass,
                                                                         ModelLayerLocation modelLayer) {
            try {
                Constructor<? extends EntityModel<LivingEntityRenderState>> constructor =
                        modelClass.getConstructor(ModelPart.class);
                return constructor.newInstance(context.bakeLayer(modelLayer));
            } catch (Exception e) {
                throw new RuntimeException("Failed to create model for " + fishRegistry.getFishName(), e);
            }
        }

        @Override
        public LivingEntityRenderState createRenderState() {
            return new LivingEntityRenderState();
        }

        @Override
        public void extractRenderState(CustomBreedableSchoolingFishEntity entity, LivingEntityRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            state.isInWater = entity.isInWater();
            state.isBaby = entity.isBaby();
        }

        @Override
        public Identifier getTextureLocation(LivingEntityRenderState state) {
            return texture;
        }

        @Override
        protected void setupRotations(LivingEntityRenderState state, PoseStack poseStack, float ageInTicks, float rotationYaw) {
            super.setupRotations(state, poseStack, ageInTicks, rotationYaw);
            if (state.isBaby) {
                poseStack.scale(0.5f, 0.5f, 0.5f);
            }
            float wiggle = 4.3F * Mth.sin(0.6F * ageInTicks);
            poseStack.mulPose(Axis.YP.rotationDegrees(wiggle));
            if (!state.isInWater) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            }
        }
    }
}
