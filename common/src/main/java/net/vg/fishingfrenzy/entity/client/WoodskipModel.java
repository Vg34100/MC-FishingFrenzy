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

public class WoodskipModel extends EntityModel<LivingEntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath("fishingfrenzy", "woodskip"), "main");

    private final ModelPart woodskip;
    private final ModelPart main;
    private final ModelPart head;
    private final ModelPart tail;

    public WoodskipModel(ModelPart root) {
        super(root.getChild("woodskip"));
        this.woodskip = root.getChild("woodskip");
        this.main = this.woodskip.getChild("main");
        this.head = this.main.getChild("head");
        this.tail = this.main.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition woodskip = partdefinition.addOrReplaceChild("woodskip", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 25.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        PartDefinition main = woodskip.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(1.0F, -2.0F, 0.0F));

        main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 6).addBox(-3.0F, -1.5F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 10).addBox(-2.0F, -2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 6).addBox(-2.0F, 0.5F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, -0.5F, 0.0F));

        PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 11).addBox(-3.0F, -2.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(6, 14).addBox(2.0F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 13).addBox(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 14).addBox(-1.0F, -0.5F, 1.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 1).addBox(-3.0F, -1.5F, 1.5F, 0.1F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 4).addBox(-3.0F, 0.5F, 2.5F, 0.1F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 15).addBox(-3.0F, 0.5F, -3.5F, 0.1F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(2, 16).addBox(-3.0F, -1.5F, -2.5F, 0.1F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.5F, 0.0F));

        torso.addOrReplaceChild("topfin", CubeListBuilder.create().texOffs(14, 17).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F))
                        .texOffs(12, 15).addBox(-3.0F, -2.0F, -1.0F, 2.0F, 2.0F, 0.1F, new CubeDeformation(0.0F))
                        .texOffs(12, 17).addBox(-4.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.1F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.5F, 1.0F));

        torso.addOrReplaceChild("bottomfin", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 0.1F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.5F, 1.0F));

        main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(4, 16).addBox(0.0F, -1.0F, 0.0F, 1.0F, 2.0F, 0.1F, new CubeDeformation(0.0F))
                        .texOffs(10, 14).addBox(1.0F, -2.0F, 0.0F, 1.0F, 4.0F, 0.1F, new CubeDeformation(0.0F))
                        .texOffs(0, 16).addBox(2.0F, -2.0F, 0.0F, 1.0F, 4.0F, 0.1F, new CubeDeformation(0.0F)),
                PartPose.offset(3.0F, -0.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -12.0F, 12.0F);
        headPitch = Mth.clamp(headPitch, -8.0F, 8.0F);
        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
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
