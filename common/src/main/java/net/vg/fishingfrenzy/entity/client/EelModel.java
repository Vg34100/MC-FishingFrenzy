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


public class EelModel extends EntityModel<LivingEntityRenderState> {
	private final ModelPart eel;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart body_0;
	private final ModelPart body_1;
	private final ModelPart body_2;

	public EelModel(ModelPart root) {
        super(root.getChild("eel"));
		this.eel = root.getChild("eel");
		this.main = this.eel.getChild("main");
		this.head = this.main.getChild("head");
		this.tail = this.main.getChild("tail");
		this.body_0 = this.main.getChild("body_0");
		this.body_1 = this.main.getChild("body_1");
		this.body_2 = this.main.getChild("body_2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition eel = partdefinition.addOrReplaceChild("eel", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition main = eel.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -1.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(8, 12).addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(2.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -1.0F, 0.0F));

		PartDefinition body_0 = main.addOrReplaceChild("body_0", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -1.0F, 0.0F));

		PartDefinition body_1 = main.addOrReplaceChild("body_1", CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 0.0F));

		PartDefinition body_2 = main.addOrReplaceChild("body_2", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -1.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(LivingEntityRenderState state) {
		float wave = state.ageInTicks * (state.isInWater ? 0.65F : 1.3F);
		float amplitude = state.isInWater ? 0.38F : 0.7F;
		this.body_0.yRot = Mth.sin(wave) * amplitude * 0.35F;
		this.body_1.yRot = Mth.sin(wave + 0.7F) * amplitude * 0.6F;
		this.body_2.yRot = Mth.sin(wave + 1.4F) * amplitude;
		this.tail.yRot = Mth.sin(wave + 2.0F) * amplitude;
	}
}
