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


public class CobblestoneFishModel extends EntityModel<LivingEntityRenderState> {
	private final ModelPart cobblestone_fish;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;

	public CobblestoneFishModel(ModelPart root) {
        super(root.getChild("cobblestone_fish"));
		this.cobblestone_fish = root.getChild("cobblestone_fish");
		this.main = this.cobblestone_fish.getChild("main");
		this.head = this.main.getChild("head");
		this.body = this.main.getChild("body");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition cobblestone_fish = partdefinition.addOrReplaceChild("cobblestone_fish", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = cobblestone_fish.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -3.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 26).addBox(-6.0F, 0.0F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 16).addBox(-7.0F, 0.0F, -3.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(16, 21).addBox(6.0F, 0.0F, -3.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(8, 26).addBox(-6.0F, 0.0F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(26, 16).addBox(-5.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(26, 19).addBox(4.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 26).addBox(5.0F, 0.0F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 24).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(-2.0F, -1.0F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 22).addBox(0.5F, -1.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 26).addBox(-2.5F, -1.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 4.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(LivingEntityRenderState state) {
		this.tail.xRot = Mth.sin(state.ageInTicks * (state.isInWater ? 0.45F : 1.1F)) * (state.isInWater ? 0.32F : 0.65F);
	}
}
