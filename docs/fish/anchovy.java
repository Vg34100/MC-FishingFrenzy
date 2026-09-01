// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class anchovy extends EntityModel<Entity> {
	private final ModelPart anchovy;
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart tail;
	public anchovy(ModelPart root) {
		this.anchovy = root.getChild("anchovy");
		this.main = this.anchovy.getChild("main");
		this.head = this.main.getChild("head");
		this.torso = this.main.getChild("torso");
		this.tail = this.main.getChild("tail");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData anchovy = modelPartData.addChild("anchovy", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 26.0F, 1.0F));

		ModelPartData main = anchovy.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(0, 8).cuboid(-1.0F, -5.0F, -5.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData torso = main.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -5.0F, -2.0F, 2.0F, 3.0F, 5.0F, new Dilation(0.0F))
		.uv(7, 5).cuboid(0.0F, -6.0F, -2.0F, 0.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 1).cuboid(0.0F, -2.0F, -1.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData tail = main.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = tail.addChild("cube_r1", ModelPartBuilder.create().uv(0, 7).cuboid(0.99F, -2.0F, 0.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -2.0F, 4.0F, 0.8727F, 0.0F, 0.0F));

		ModelPartData cube_r2 = tail.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(1.01F, -1.0F, -1.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -4.0F, 4.0F, 0.8727F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 16, 16);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		anchovy.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}