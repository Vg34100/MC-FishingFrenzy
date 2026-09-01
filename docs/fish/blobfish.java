// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class blobfish<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "blobfish"), "main");
	private final ModelPart blobfish;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart tail;

	public blobfish(ModelPart root) {
		this.blobfish = root.getChild("blobfish");
		this.main = this.blobfish.getChild("main");
		this.head = this.main.getChild("head");
		this.torso = this.main.getChild("torso");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition blobfish = partdefinition.addOrReplaceChild("blobfish", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = blobfish.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-4.0F, -1.0F, -3.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(-3.5F, -3.0F, -2.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -3.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 23).addBox(1.0F, -3.25F, -3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 2.0F, -2.0F, -0.3022F, -0.0297F, 0.0381F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -3.0F, 7.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(18, 19).addBox(3.5F, 1.0F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(6.5F, 2.0F, -3.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(8, 28).addBox(-7.5F, 2.0F, -3.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(22, 13).addBox(-6.5F, 1.0F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 23).addBox(-1.5F, -1.0F, 2.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 28).addBox(0.0F, -1.0F, 4.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(28, 23).addBox(0.0F, 1.0F, 4.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 28).addBox(0.0F, 0.0F, 4.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 5.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		blobfish.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}