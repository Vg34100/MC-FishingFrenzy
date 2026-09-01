// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class cobblestone_fish<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "cobblestone_fish"), "main");
	private final ModelPart cobblestone_fish;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;

	public cobblestone_fish(ModelPart root) {
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
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		cobblestone_fish.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}