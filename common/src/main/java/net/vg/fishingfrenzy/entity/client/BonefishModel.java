package net.vg.fishingfrenzy.entity.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

/** Model ported from the supplied Blockbench bonefish export. */
public class BonefishModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart carp;
    private final ModelPart tail;

    public BonefishModel(ModelPart root) {
        super(root.getChild("carp"));
        this.carp = root.getChild("carp");
        this.tail = this.carp.getChild("body").getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition carp = root.addOrReplaceChild("carp", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 28.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition body = carp.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 7).addBox(3.0F, -5.0F, -1.0F, 1.0F, 4.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(1, 7).addBox(4.0F, -5.0F, -1.0F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(1, 7).addBox(4.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(2, 7).addBox(5.0F, -3.0F, -1.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(10, 7).addBox(6.0F, -4.0F, -1.0F, 1.0F, 2.0F, 2.0F, CubeDeformation.NONE), PartPose.ZERO);

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -5.0F, -1.0F, 7.0F, 3.0F, 2.0F, CubeDeformation.NONE)
                .texOffs(10, 10).addBox(-4.0F, -5.0F, -1.0F, -1.0F, 4.0F, 2.0F, CubeDeformation.NONE), PartPose.ZERO);

        torso.addOrReplaceChild("top_fin", CubeListBuilder.create()
                .texOffs(7, 3).addBox(2.0F, -3.0F, -1.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(7, 3).addBox(1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(1, 13).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(1, 13).addBox(3.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(0, 13).addBox(-3.0F, -1.0F, -1.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(12, 12).addBox(-4.0F, -2.0F, -1.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -5.0F, 1.0F));
        torso.addOrReplaceChild("bottom_fin", CubeListBuilder.create()
                .texOffs(8, 8).addBox(0.0F, -4.0F, -1.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(4, 13).addBox(0.0F, -3.0F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(-1.0F, 2.0F, 1.0F));
        torso.addOrReplaceChild("ribs", CubeListBuilder.create()
                .texOffs(1, 1).addBox(-3.0F, -3.0F, -2.0F, 1.0F, 4.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(1, 1).addBox(1.0F, -3.0F, -2.0F, 1.0F, 4.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(1, 1).addBox(-1.0F, -4.0F, -2.0F, 1.0F, 4.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(1, 1).addBox(-1.0F, -4.0F, 1.0F, 1.0F, 4.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(1, 1).addBox(1.0F, -3.0F, 1.0F, 1.0F, 4.0F, 1.0F, CubeDeformation.NONE)
                .texOffs(1, 1).addBox(-3.0F, -3.0F, 1.0F, 1.0F, 4.0F, 1.0F, CubeDeformation.NONE), PartPose.ZERO);

        body.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(8, 7).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 3.0F, 0.0F, CubeDeformation.NONE)
                .texOffs(0, 1).addBox(-3.0F, -1.0F, 0.0F, 1.0F, 3.0F, 0.0F, CubeDeformation.NONE)
                .texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 1.0F, 2.0F, 0.0F, CubeDeformation.NONE), PartPose.offset(-5.0F, -2.0F, 0.0F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        this.tail.yRot = Mth.sin(state.ageInTicks * (state.isInWater ? 0.55F : 1.25F)) * (state.isInWater ? 0.45F : 0.8F);
    }
}
