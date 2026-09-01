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


public class WalleyeModel extends EntityModel<LivingEntityRenderState> {
	private final ModelPart walleye;
	private final ModelPart main;
	private final ModelPart torso;
	private final ModelPart topfin;
	private final ModelPart bottomfin;
	private final ModelPart head;
	private final ModelPart tail;

	public WalleyeModel(ModelPart root) {
        super(root.getChild("walleye"));
		this.walleye = root.getChild("walleye");
		this.main = this.walleye.getChild("main");
		this.torso = this.main.getChild("torso");
		this.topfin = this.torso.getChild("topfin");
		this.bottomfin = this.torso.getChild("bottomfin");
		this.head = this.main.getChild("head");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition walleye = partdefinition.addOrReplaceChild("walleye", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = walleye.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(1.0F, -4.0F, 0.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition topfin = torso.addOrReplaceChild("topfin", CubeListBuilder.create().texOffs(8, 18).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 20).addBox(0.0F, -1.0F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 14).addBox(0.0F, -3.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 18).addBox(0.0F, -2.0F, -2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition bottomfin = torso.addOrReplaceChild("bottomfin", CubeListBuilder.create().texOffs(18, 18).addBox(0.0F, 1.0F, -2.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 14).addBox(0.0F, 0.0F, -2.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -1.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 14).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, -4.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(4, 20).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 18).addBox(0.0F, -2.0F, 1.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(0.0F, -2.75F, 2.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(2, 20).addBox(0.0F, 0.5F, 2.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 6.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(LivingEntityRenderState state) {
		this.tail.yRot = Mth.sin(state.ageInTicks * (state.isInWater ? 0.6F : 1.25F)) * (state.isInWater ? 0.46F : 0.82F);
	}
}
