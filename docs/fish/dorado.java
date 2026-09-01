// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class dorado<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "dorado"), "main");
	private final ModelPart dorado;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;

	public dorado(ModelPart root) {
		this.dorado = root.getChild("dorado");
		this.main = this.dorado.getChild("main");
		this.head = this.main.getChild("head");
		this.body = this.main.getChild("body");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition dorado = partdefinition.addOrReplaceChild("dorado", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = dorado.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(-2.5F, -5.0F, -4.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 16).addBox(-2.5F, -4.0F, -5.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(22, 13).addBox(3.0F, -1.0F, -1.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 0).addBox(4.0F, -1.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 2).addBox(-5.0F, -1.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 22).addBox(-4.0F, -1.0F, -1.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(-2.5F, -4.0F, -1.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(24, 4).addBox(0.0F, -7.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 22).addBox(0.0F, -8.0F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 25).addBox(0.0F, -7.0F, 1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 9).addBox(-2.5F, -5.0F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 8).addBox(-1.0F, -2.0F, 5.0F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 12).addBox(-2.0F, -2.0F, 6.0F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 25).addBox(1.0F, -2.0F, 7.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 25).addBox(-2.0F, -2.0F, 7.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		dorado.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}