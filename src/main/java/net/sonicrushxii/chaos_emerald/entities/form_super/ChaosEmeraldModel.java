package net.sonicrushxii.chaos_emerald.entities.form_super;// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;

public class ChaosEmeraldModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ChaosEmerald.MOD_ID, "chaos_emerald_model"), "main");
	private final ModelPart middle;
	private final ModelPart top;
	private final ModelPart base;
	private final ModelPart group;
	private final ModelPart group2;
	private final ModelPart group3;
	private final ModelPart group4;
	private final ModelPart group5;
	private final ModelPart accent;

	public ChaosEmeraldModel(ModelPart root) {
		this.middle = root.getChild("middle");
		this.top = root.getChild("top");
		this.base = root.getChild("base");
		this.group = this.base.getChild("group");
		this.group2 = this.base.getChild("group2");
		this.group3 = this.base.getChild("group3");
		this.group4 = this.base.getChild("group4");
		this.group5 = this.base.getChild("group5");
		this.accent = root.getChild("accent");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition middle = partdefinition.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 11).addBox(5.9289F, -0.5F, -13.0F, 4.1421F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(3.0F, -0.5F, -10.0711F, 10.0F, 1.0F, 4.1421F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 16.0F, 8.0F));

		PartDefinition octagon_r1 = middle.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(0, 22).addBox(-5.0F, -0.5F, -2.0711F, 10.0F, 1.0F, 4.1421F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0711F, -0.5F, -5.0F, 4.1421F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(28, 9).addBox(6.3431F, -1.5F, -12.0F, 3.3137F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(28, 22).addBox(4.0F, -1.5F, -9.6569F, 8.0F, 1.0F, 3.3137F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 16.0F, 8.0F));

		PartDefinition octagon_r2 = top.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(28, 18).addBox(-4.0F, -0.5F, -1.6569F, 8.0F, 1.0F, 3.3137F, new CubeDeformation(0.0F))
		.texOffs(28, 0).addBox(-1.6569F, -0.5F, -4.0F, 3.3137F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -1.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(-8.0F, 20.5F, 10.0F));

		PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(10, 34).addBox(3.3F, 0.2F, -2.1F, 1.0F, 1.0F, 4.2F, new CubeDeformation(0.0F))
		.texOffs(44, 34).addBox(2.55F, 1.3F, -1.4F, 1.0F, 1.0F, 2.8F, new CubeDeformation(0.0F))
		.texOffs(8, 45).addBox(1.6F, 0.4F, -0.7F, 1.3F, 3.0F, 1.35F, new CubeDeformation(0.0F))
		.texOffs(18, 40).addBox(-2.1F, 0.2F, 3.3F, 4.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 46).addBox(-1.4F, 1.3F, 2.55F, 2.8F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 46).addBox(-0.65F, 0.4F, 1.6F, 1.35F, 3.0F, 1.3F, new CubeDeformation(0.0F))
		.texOffs(8, 39).addBox(-2.1F, 0.2F, -4.3F, 4.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 46).addBox(-1.4F, 1.3F, -3.55F, 2.8F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 46).addBox(-0.7F, 0.4F, -2.9F, 1.35F, 3.0F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -4.5F, -10.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition group = base.addOrReplaceChild("group", CubeListBuilder.create().texOffs(46, 46).addBox(7.5F, -0.6F, -5.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 41).addBox(6.5F, -3.6F, -6.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 42).addBox(6.5F, -4.7F, -7.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 38).addBox(6.5F, -3.8F, -8.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group2 = base.addOrReplaceChild("group2", CubeListBuilder.create().texOffs(0, 47).addBox(8.5F, -0.6F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 34).addBox(9.5F, -3.6F, -5.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(28, 36).addBox(10.5F, -4.7F, -5.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 42).addBox(11.5F, -3.8F, -5.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group3 = base.addOrReplaceChild("group3", CubeListBuilder.create().texOffs(44, 40).addBox(6.5F, -0.6F, -3.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 26).addBox(6.5F, -3.6F, -2.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 30).addBox(6.5F, -4.7F, -1.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 45).addBox(6.5F, -3.8F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 47).addBox(7.5F, 0.05F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group4 = base.addOrReplaceChild("group4", CubeListBuilder.create().texOffs(40, 46).addBox(6.5F, -0.6F, -5.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(36, 36).addBox(5.5F, -3.6F, -5.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 39).addBox(4.5F, -4.7F, -5.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(32, 42).addBox(3.5F, -3.8F, -5.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group5 = base.addOrReplaceChild("group5", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition cube_r2 = group5.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(40, 42).addBox(-3.55F, 0.3F, -1.4F, 1.0F, 1.0F, 2.8F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-4.3F, -0.8F, -2.1F, 1.0F, 1.0F, 4.2F, new CubeDeformation(0.0F))
		.texOffs(12, 45).addBox(-2.9F, -0.6F, -0.65F, 1.3F, 3.0F, 1.35F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -3.0F, -4.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition accent = partdefinition.addOrReplaceChild("accent", CubeListBuilder.create().texOffs(0, 32).addBox(5.5F, -1.75F, -9.0355F, 5.0F, 0.25F, 2.0711F, new CubeDeformation(0.0F))
		.texOffs(28, 31).addBox(6.9645F, -1.75F, -10.5F, 2.0711F, 0.25F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 16.0F, 8.0F));

		PartDefinition octagon_r3 = accent.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(14, 32).addBox(-2.5F, 0.25F, -1.0355F, 5.0F, 0.25F, 2.0711F, new CubeDeformation(0.0F))
		.texOffs(28, 26).addBox(-1.0355F, 0.25F, -2.5F, 2.0711F, 0.25F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -2.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		middle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		accent.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}