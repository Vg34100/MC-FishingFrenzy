// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class spook_fish<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "spook_fish"), "main");
	private final ModelPart spook_fish;
	private final ModelPart main;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart tail;

	public spook_fish(ModelPart root) {
		this.spook_fish = root.getChild("spook_fish");
		this.main = this.spook_fish.getChild("main");
		this.torso = this.main.getChild("torso");
		this.head = this.main.getChild("head");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition spook_fish = partdefinition.addOrReplaceChild("spook_fish", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition main = spook_fish.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(22, 13).addBox(-2.0F, -2.0F, 4.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 28).addBox(0.0F, -6.0F, -2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(28, 28).addBox(0.0F, 3.0F, -2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(30, 0).addBox(0.0F, 5.0F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.0F, -7.0F, 1.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 21).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 19).addBox(-2.0F, -4.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -3.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 28).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, 5.5F, -2.25F, 0.3049F, -0.1836F, -0.1189F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 28).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, 1.5F, -4.75F, -0.5488F, -0.648F, 0.1496F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(26, 8).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 3.0F, -5.0F, -0.0873F, 0.5236F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(30, 21).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 0).addBox(0.0F, -3.0F, 1.0F, 0.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 5.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		spook_fish.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}