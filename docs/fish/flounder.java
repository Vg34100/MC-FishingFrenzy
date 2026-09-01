// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class flounder<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "flounder"), "main");
	private final ModelPart flounder;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;

	public flounder(ModelPart root) {
		this.flounder = root.getChild("flounder");
		this.main = this.flounder.getChild("main");
		this.head = this.main.getChild("head");
		this.body = this.main.getChild("body");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition flounder = partdefinition.addOrReplaceChild("flounder", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = flounder.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 11).addBox(-1.0F, -2.5F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -2.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(14, 0).addBox(0.0F, -7.0F, -1.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(14, 5).addBox(0.0F, 0.0F, -1.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(8, 11).addBox(-1.0F, -5.5F, 3.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 10).addBox(-1.0F, -4.5F, 5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 6.0F));

		PartDefinition cube_r1 = tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 14).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 0.0F, -0.5F, -0.9599F, 0.0F, 0.0F));

		PartDefinition cube_r2 = tail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 18).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 1.5F, 1.0F, 0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		flounder.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}