package net.vg.fishingfrenzy.entity.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class CarpModel extends EntityModel<LivingEntityRenderState> {
	private final ModelPart carp;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;

	public CarpModel(ModelPart root) {
        super(root.getChild("carp"));
		this.carp = root.getChild("carp");
		this.main = this.carp.getChild("main");
		this.head = this.main.getChild("head");
		this.body = this.main.getChild("body");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition carp = partdefinition.addOrReplaceChild("carp", CubeListBuilder.create(), PartPose.offset(0.0F, 26.0F, 0.0F));

		PartDefinition main = carp.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, -5.0F, -6.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(20, 15).addBox(-1.5F, -6.0F, -5.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -3.0F, 4.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-1.5F, -6.0F, -3.0F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(22, 0).addBox(2.0F, -4.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(22, 4).addBox(3.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(22, 23).addBox(-3.0F, -4.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(20, 10).addBox(0.0F, -7.0F, -3.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(8, 24).addBox(0.0F, -8.0F, -2.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 8).addBox(0.0F, -9.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 23).addBox(-4.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(14, 18).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(0.0F, -3.0F, 2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 24).addBox(0.0F, -3.0F, 4.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 18).addBox(0.0F, 2.0F, 4.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 24).addBox(0.0F, 1.0F, 2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 4.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(LivingEntityRenderState state) {
		this.tail.yRot = Mth.sin(state.ageInTicks * (state.isInWater ? 0.55F : 1.25F)) * (state.isInWater ? 0.45F : 0.8F);
	}
}
