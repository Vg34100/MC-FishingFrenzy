// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class chub<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "chub"), "main");
	private final ModelPart chub;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;

	public chub(ModelPart root) {
		this.chub = root.getChild("chub");
		this.main = this.chub.getChild("main");
		this.head = this.main.getChild("head");
		this.body = this.main.getChild("body");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition chub = partdefinition.addOrReplaceChild("chub", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = chub.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 10).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(6, 7).addBox(1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-2.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 12).addBox(0.0F, -3.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 10).addBox(0.0F, -2.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 1.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(12, 7).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 12).addBox(0.0F, -1.5F, 1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 12).addBox(0.0F, 0.5F, 1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 4.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		chub.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}