// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class bream<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "bream"), "main");
	private final ModelPart bream;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart top_fin;
	private final ModelPart bottom_fin;
	private final ModelPart tail;

	public bream(ModelPart root) {
		this.bream = root.getChild("bream");
		this.main = this.bream.getChild("main");
		this.head = this.main.getChild("head");
		this.torso = this.main.getChild("torso");
		this.top_fin = this.torso.getChild("top_fin");
		this.bottom_fin = this.torso.getChild("bottom_fin");
		this.tail = this.main.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bream = partdefinition.addOrReplaceChild("bream", CubeListBuilder.create(), PartPose.offset(0.0F, 27.0F, 0.0F));

		PartDefinition main = bream.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -9.0F, -6.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 12).addBox(-1.0F, -8.0F, -7.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = main.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -9.0F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(16, 0).addBox(-1.0F, -7.0F, 5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 0).addBox(-1.0F, -8.0F, 4.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -8.0F, 3.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition top_fin = torso.addOrReplaceChild("top_fin", CubeListBuilder.create().texOffs(10, 15).addBox(-1.0F, -3.0F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 5).addBox(-1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -9.0F, 0.0F));

		PartDefinition bottom_fin = torso.addOrReplaceChild("bottom_fin", CubeListBuilder.create().texOffs(14, 15).addBox(-1.0F, -2.0F, 1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 4).addBox(-1.0F, -2.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -1.0F, -1.0F));

		PartDefinition tail = main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 11).addBox(0.0F, -7.0F, 6.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 3).addBox(0.0F, -8.0F, 7.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(7, 11).addBox(0.0F, -5.0F, 7.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(9, 11).addBox(0.0F, -4.0F, 8.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 4).addBox(0.0F, -8.0F, 8.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bream.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}