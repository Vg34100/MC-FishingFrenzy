// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class smallmouth_bass<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "smallmouth_bass"), "main");
	private final ModelPart smallmouth_bass;
	private final ModelPart main;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart tail;

	public smallmouth_bass(ModelPart root) {
		this.smallmouth_bass = root.getChild("smallmouth_bass");
		this.main = this.smallmouth_bass.getChild("main");
		this.torso = this.main.getChild("torso");
		this.head = this.main.getChild("head");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition smallmouth_bass = partdefinition.addOrReplaceChild("smallmouth_bass", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = smallmouth_bass.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(12, 8).addBox(0.0F, -4.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(0.0F, -3.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(2, 13).addBox(0.0F, 1.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 13).addBox(0.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 13).addBox(0.0F, 1.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -2.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(8, 8).addBox(0.0F, -3.0F, 0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 12).addBox(0.0F, 0.0F, 0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		smallmouth_bass.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}