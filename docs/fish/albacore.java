// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class albacore extends EntityModel<Entity> {
	private final ModelPart albacore;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart topfin;
	private final ModelPart bottomfin;
	private final ModelPart tail;
	public albacore(ModelPart root) {
		this.albacore = root.getChild("albacore");
		this.main = this.albacore.getChild("main");
		this.head = this.main.getChild("head");
		this.torso = this.main.getChild("torso");
		this.topfin = this.torso.getChild("topfin");
		this.bottomfin = this.torso.getChild("bottomfin");
		this.tail = this.main.getChild("tail");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData albacore = modelPartData.addChild("albacore", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 27.0F, 0.0F));

		ModelPartData main = albacore.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -2.0F));

		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(0, 11).cuboid(-1.0F, -7.0F, -5.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(7, 11).cuboid(-1.0F, -7.0F, -6.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 11).cuboid(-1.0F, -4.0F, -6.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData torso = main.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -7.0F, -2.0F, 2.0F, 4.0F, 7.0F, new Dilation(0.0F))
		.uv(0, 10).cuboid(0.0F, -10.0F, 1.0F, 0.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-1.0F, -6.0F, 5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(10, 11).cuboid(-2.0F, -6.0F, -1.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(11, 0).cuboid(1.0F, -6.0F, -1.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData topfin = torso.addChild("topfin", ModelPartBuilder.create().uv(11, 0).cuboid(0.0F, -1.0F, -5.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(11, 0).cuboid(0.0F, -2.0F, -4.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(12, 10).cuboid(0.0F, -3.0F, -3.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 5).cuboid(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.0F, 3.0F));

		ModelPartData bottomfin = torso.addChild("bottomfin", ModelPartBuilder.create().uv(4, 4).cuboid(0.0F, -3.0F, -1.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(0.0F, -3.0F, 0.0F, 0.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData tail = main.addChild("tail", ModelPartBuilder.create().uv(0, 3).cuboid(0.0F, -6.0F, 6.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(10, 12).cuboid(0.0F, -7.0F, 7.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(11, 4).cuboid(0.0F, -4.0F, 7.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		albacore.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}