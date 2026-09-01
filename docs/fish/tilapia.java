// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class tilapia<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "tilapia"), "main");
	private final ModelPart tilapia;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart tail;

	public tilapia(ModelPart root) {
		this.tilapia = root.getChild("tilapia");
		this.main = this.tilapia.getChild("main");
		this.head = this.main.getChild("head");
		this.torso = this.main.getChild("torso");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tilapia = partdefinition.addOrReplaceChild("tilapia", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = tilapia.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(8, 9).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -1.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 0).addBox(1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 4).addBox(-2.0F, 0.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 15).addBox(0.0F, -5.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 15).addBox(0.0F, -5.0F, 0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 15).addBox(0.0F, -4.0F, -1.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 8).addBox(0.0F, 3.0F, -1.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 10).addBox(0.0F, 3.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(-1.0F, -2.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 1.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(8, 15).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 15).addBox(0.0F, -2.0F, 1.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 4.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		tilapia.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}