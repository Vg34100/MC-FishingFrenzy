// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class bonefish extends EntityModel<Entity> {
	private final ModelPart carp;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart top_fin;
	private final ModelPart bottom_fin;
	private final ModelPart ribs;
	private final ModelPart tail;
	public bonefish(ModelPart root) {
		this.carp = root.getChild("carp");
		this.body = this.carp.getChild("body");
		this.head = this.body.getChild("head");
		this.torso = this.body.getChild("torso");
		this.top_fin = this.torso.getChild("top_fin");
		this.bottom_fin = this.torso.getChild("bottom_fin");
		this.ribs = this.torso.getChild("ribs");
		this.tail = this.body.getChild("tail");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData carp = modelPartData.addChild("carp", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = carp.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 7).cuboid(3.0F, -5.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 7).cuboid(4.0F, -5.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(1, 7).cuboid(4.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(2, 7).cuboid(5.0F, -3.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(10, 7).cuboid(6.0F, -4.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -5.0F, -1.0F, 7.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(10, 10).cuboid(-4.0F, -5.0F, -1.0F, -1.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData top_fin = torso.addChild("top_fin", ModelPartBuilder.create().uv(7, 3).cuboid(2.0F, -3.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
		.uv(7, 3).cuboid(1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(1, 13).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(1, 13).cuboid(3.0F, -1.0F, -1.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 13).cuboid(-3.0F, -1.0F, -1.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(12, 12).cuboid(-4.0F, -2.0F, -1.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, 1.0F));

		ModelPartData bottom_fin = torso.addChild("bottom_fin", ModelPartBuilder.create().uv(8, 8).cuboid(0.0F, -4.0F, -1.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(4, 13).cuboid(0.0F, -3.0F, -1.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 2.0F, 1.0F));

		ModelPartData ribs = torso.addChild("ribs", ModelPartBuilder.create().uv(1, 1).cuboid(-3.0F, -3.0F, -2.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(1, 1).cuboid(1.0F, -3.0F, -2.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(1, 1).cuboid(-1.0F, -4.0F, -2.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(1, 1).cuboid(-1.0F, -4.0F, 1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(1, 1).cuboid(1.0F, -3.0F, 1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(1, 1).cuboid(-3.0F, -3.0F, 1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(8, 7).cuboid(-7.0F, -4.0F, 0.0F, 2.0F, 3.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 1).cuboid(-8.0F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-8.0F, -5.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		carp.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}