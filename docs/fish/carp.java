// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class carp<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "carp"), "main");
	private final ModelPart carp;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;

	public carp(ModelPart root) {
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

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(14, 18).addBox(-2.0F, -5.0F, 4.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(0.0F, -7.0F, 6.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 24).addBox(0.0F, -7.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 18).addBox(0.0F, -2.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 24).addBox(0.0F, -3.0F, 6.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		carp.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}