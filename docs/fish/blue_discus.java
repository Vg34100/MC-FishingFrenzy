// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class blue_discus<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "blue_discus"), "main");
	private final ModelPart blue_discus;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart arms;
	private final ModelPart tail;

	public blue_discus(ModelPart root) {
		this.blue_discus = root.getChild("blue_discus");
		this.main = this.blue_discus.getChild("main");
		this.head = this.main.getChild("head");
		this.torso = this.main.getChild("torso");
		this.arms = this.torso.getChild("arms");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition blue_discus = partdefinition.addOrReplaceChild("blue_discus", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 1.0F));

		PartDefinition main = blue_discus.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(14, 6).addBox(-1.0F, -5.0F, -6.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 11).addBox(-1.0F, -7.0F, -4.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 0).addBox(-1.0F, -6.0F, -5.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(9, 3).addBox(0.0F, -8.0F, -4.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(9, 0).addBox(0.0F, -1.0F, -4.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -7.0F, -3.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(0.0F, -8.0F, -3.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 6).addBox(0.0F, -9.0F, -2.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(0.0F, -1.0F, -3.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 7).addBox(0.0F, 0.0F, -2.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.5F, -6.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arms = torso.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(4, 16).addBox(-4.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -2.0F, -1.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(11, 0).addBox(0.0F, -4.5F, 2.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(9, 0).addBox(0.1F, 0.0F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 3.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r2 = tail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(9, 1).addBox(-0.1F, -1.0F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 3.0F, 0.4363F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		blue_discus.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}