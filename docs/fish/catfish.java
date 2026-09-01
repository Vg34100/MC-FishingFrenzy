// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class catfish<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "catfish"), "main");
	private final ModelPart catfish;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart head_antenna;
	private final ModelPart bottom_antenna;
	private final ModelPart whiskers;
	private final ModelPart bottom_left_whisker;
	private final ModelPart top_left_whisker;
	private final ModelPart bottom_right_whisker;
	private final ModelPart top_right_whisker;
	private final ModelPart torso;
	private final ModelPart tail;

	public catfish(ModelPart root) {
		this.catfish = root.getChild("catfish");
		this.main = this.catfish.getChild("main");
		this.head = this.main.getChild("head");
		this.head_antenna = this.head.getChild("head_antenna");
		this.bottom_antenna = this.head.getChild("bottom_antenna");
		this.whiskers = this.head.getChild("whiskers");
		this.bottom_left_whisker = this.whiskers.getChild("bottom_left_whisker");
		this.top_left_whisker = this.whiskers.getChild("top_left_whisker");
		this.bottom_right_whisker = this.whiskers.getChild("bottom_right_whisker");
		this.top_right_whisker = this.whiskers.getChild("top_right_whisker");
		this.torso = this.main.getChild("torso");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition catfish = partdefinition.addOrReplaceChild("catfish", CubeListBuilder.create(), PartPose.offset(0.0F, 26.0F, 0.0F));

		PartDefinition main = catfish.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(15, 0).addBox(-2.0F, -6.0F, -6.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_antenna = head.addOrReplaceChild("head_antenna", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -2.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(-2.0F, 0.0F, -2.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, -5.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition bottom_antenna = head.addOrReplaceChild("bottom_antenna", CubeListBuilder.create().texOffs(4, 3).addBox(-2.0F, -1.0F, -2.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 0).addBox(-2.0F, 0.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, -4.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition whiskers = head.addOrReplaceChild("whiskers", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.5F));

		PartDefinition bottom_left_whisker = whiskers.addOrReplaceChild("bottom_left_whisker", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, 1.5F, -0.2F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(15, 11).addBox(-3.0F, 0.5F, -0.2F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, -6.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition top_left_whisker = whiskers.addOrReplaceChild("top_left_whisker", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.5F, -0.2F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-3.0F, -1.5F, -0.2F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, -6.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition bottom_right_whisker = whiskers.addOrReplaceChild("bottom_right_whisker", CubeListBuilder.create().texOffs(15, 13).addBox(1.0F, 0.5F, -0.2F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(15, 12).addBox(2.0F, 1.5F, -0.2F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, -6.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition top_right_whisker = whiskers.addOrReplaceChild("top_right_whisker", CubeListBuilder.create().texOffs(0, 14).addBox(1.0F, -1.5F, -0.2F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(2.0F, -2.5F, -0.2F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, -6.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -3.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(-1.0F, -7.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.0F, -8.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(15, 15).addBox(2.0F, -5.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 11).addBox(-3.0F, -5.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 9).addBox(1.0F, -7.0F, 4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 10).addBox(1.0F, -5.0F, 4.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 2).addBox(1.0F, -3.0F, 4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		catfish.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}