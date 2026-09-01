package net.vg.fishingfrenzy.entity.client;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class AnchovyModel extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("fishingfrenzy", "anchovy"), "main");
	private final ModelPart anchovy;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart tail;

	public AnchovyModel(ModelPart root) {
        super(root.getChild("anchovy"));
		this.anchovy = root.getChild("anchovy");
		this.main = anchovy.getChild("main");
		this.head = main.getChild("head");
		this.tail = main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition anchovy = partdefinition.addOrReplaceChild("anchovy", CubeListBuilder.create(), PartPose.offset(0.0F, 26.0F, 1.0F));

		PartDefinition main = anchovy.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -5.0F, -5.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -5.0F, -2.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(7, 5).addBox(0.0F, -6.0F, -2.0F, 0.1F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(0.0F, -2.0F, -1.0F, 0.1F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 7).addBox(0.99F, -2.0F, 0.0F, 0.1F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0F, 4.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition cube_r2 = tail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(1.01F, -1.0F, -1.0F, 0.1F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -4.0F, 4.0F, 0.8727F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

//	@Override
//	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//
//	}
//
//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		anchovy.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//	}

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
