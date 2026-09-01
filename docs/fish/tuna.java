// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class tuna<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "tuna"), "main");
	private final ModelPart tuna;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart tail;

	public tuna(ModelPart root) {
		this.tuna = root.getChild("tuna");
		this.main = this.tuna.getChild("main");
		this.head = this.main.getChild("head");
		this.torso = this.main.getChild("torso");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tuna = partdefinition.addOrReplaceChild("tuna", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = tuna.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 11).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 16).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 11).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(10, 11).addBox(-2.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 0).addBox(1.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 16).addBox(-0.5F, -2.5F, 4.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 14).addBox(0.0F, -4.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(0.0F, -5.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 19).addBox(0.0F, 2.0F, -2.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 19).addBox(0.0F, 2.0F, 2.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 5).addBox(0.0F, 2.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(8, 19).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 5).addBox(0.0F, -2.0F, 1.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 19).addBox(0.0F, -3.0F, 3.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 19).addBox(0.0F, 1.0F, 3.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		tuna.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}