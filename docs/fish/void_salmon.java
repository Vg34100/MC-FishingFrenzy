// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class void_salmon<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "void_salmon"), "main");
	private final ModelPart void_salmon;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart bottomfin;
	private final ModelPart topfin;
	private final ModelPart tail;

	public void_salmon(ModelPart root) {
		this.void_salmon = root.getChild("void_salmon");
		this.main = this.void_salmon.getChild("main");
		this.head = this.main.getChild("head");
		this.torso = this.main.getChild("torso");
		this.bottomfin = this.torso.getChild("bottomfin");
		this.topfin = this.torso.getChild("topfin");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition void_salmon = partdefinition.addOrReplaceChild("void_salmon", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = void_salmon.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -1.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(14, 11).addBox(-1.5F, -2.0F, -3.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(20, 8).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 16).addBox(-1.5F, 1.0F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -3.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(20, 0).addBox(-2.5F, 0.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(20, 4).addBox(-2.5F, 1.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 20).addBox(1.5F, 1.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(8, 20).addBox(1.5F, 0.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition bottomfin = torso.addOrReplaceChild("bottomfin", CubeListBuilder.create().texOffs(12, 24).addBox(1.0F, -1.0F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 3.0F, -3.0F));

		PartDefinition topfin = torso.addOrReplaceChild("topfin", CubeListBuilder.create().texOffs(26, 17).addBox(2.0F, -1.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(6, 24).addBox(2.0F, 1.0F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(2.0F, 0.0F, -2.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 17).addBox(2.0F, -2.0F, 1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -5.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(18, 24).addBox(0.0F, -2.5F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(26, 24).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 17).addBox(0.0F, -2.5F, 2.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(26, 11).addBox(0.0F, -3.5F, 1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(26, 14).addBox(0.0F, 2.5F, 1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 24).addBox(0.0F, 1.5F, 2.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 20).addBox(0.0F, 0.5F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		void_salmon.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}