package net.vg.fishingfrenzy.entity.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class BlueDiscusModel extends EntityModel<LivingEntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("fishingfrenzy", "blue_discus"), "main");

    private final ModelPart blueDiscus;
    private final ModelPart main;
    private final ModelPart head;
    private final ModelPart tail;

    public BlueDiscusModel(ModelPart root) {
        super(root.getChild("blue_discus"));
        this.blueDiscus = root.getChild("blue_discus");
        this.main = blueDiscus.getChild("main");
        this.head = main.getChild("head");
        this.tail = main.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition blueDiscus = partDefinition.addOrReplaceChild("blue_discus", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 1.0F));
        PartDefinition main = blueDiscus.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(14, 6).addBox(-1.0F, -5.0F, -6.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(10, 11).addBox(-1.0F, -7.0F, -4.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(14, 0).addBox(-1.0F, -6.0F, -5.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(9, 3).addBox(0.0F, -8.0F, -4.0F, 0.1F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(9, 0).addBox(0.0F, -1.0F, -4.0F, 0.1F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -7.0F, -3.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 9).addBox(0.0F, -8.0F, -3.0F, 0.1F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 6).addBox(0.0F, -9.0F, -2.0F, 0.1F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 8).addBox(0.0F, -1.0F, -3.0F, 0.1F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 7).addBox(0.0F, 0.0F, -2.0F, 0.1F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-0.5F, -6.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        torso.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(4, 16).addBox(-4.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 15).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.0F, -2.0F, -1.0F));

        PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(11, 0).addBox(0.0F, -4.5F, 2.5F, 0.1F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(9, 0).addBox(0.1F, 0.0F, 0.0F, 0.1F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -4.0F, 3.0F, -0.4363F, 0.0F, 0.0F));
        tail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(9, 1).addBox(-0.1F, -1.0F, 0.0F, 0.1F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -4.0F, 3.0F, 0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -12.0f, 12.0f);
        headPitch = Mth.clamp(headPitch, -8.0f, 8.0f);
        this.head.yRot = headYaw * 0.017456292F;
        this.head.xRot = headPitch * 0.017456292F;
    }

    private void applySwimmingAnimation(LivingEntityRenderState state) {
        float swimWave = Mth.sin(state.ageInTicks * 0.6F);
        float swimScale = state.isInWater ? 1.0F : 1.4F;
        this.main.yRot += -0.05F * swimWave * swimScale;
        this.tail.yRot += -0.22F * swimWave * swimScale;
    }



    @Override
    public void setupAnim(LivingEntityRenderState state) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.setHeadAngles(state.yRot, state.xRot);
        this.applySwimmingAnimation(state);
    }
}
