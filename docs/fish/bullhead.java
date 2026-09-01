// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class bullhead<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "bullhead"), "main");
	private final ModelPart bullhead;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart tail;

	public bullhead(ModelPart root) {
		this.bullhead = root.getChild("bullhead");
		this.main = this.bullhead.getChild("main");
		this.head = this.main.getChild("head");
		this.torso = this.main.getChild("torso");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bullhead = partdefinition.addOrReplaceChild("bullhead", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 0.0F));

		PartDefinition main = bullhead.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(8, 12).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-1.0F, -7.0F, -7.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -6.0F, -8.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(-1.0F, -3.0F, -8.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 0).addBox(0.0F, -2.0F, -7.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(2, 5).addBox(0.0F, 0.0F, -6.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 5).addBox(0.0F, -8.0F, -4.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -7.0F, -3.0F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(11, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(15, 8).addBox(0.0F, -8.0F, -3.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 11).addBox(0.0F, -6.0F, 4.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 13).addBox(0.0F, -7.0F, 5.0F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bullhead.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}