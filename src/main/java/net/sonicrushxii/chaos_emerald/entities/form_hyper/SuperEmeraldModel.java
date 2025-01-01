package net.sonicrushxii.chaos_emerald.entities.form_hyper;// Made with Blockbench 4.11.2
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

public class SuperEmeraldModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ChaosEmerald.MOD_ID, "super_emerald_model"), "main");
	private final ModelPart octagon;
	private final ModelPart Top;
	private final ModelPart octagon2;
	private final ModelPart octagon3;
	private final ModelPart octagon4;
	private final ModelPart octagon5;
	private final ModelPart octagon6;
	private final ModelPart octagon7;
	private final ModelPart base;
	private final ModelPart group;
	private final ModelPart group2;
	private final ModelPart group3;
	private final ModelPart group4;
	private final ModelPart group5;

	public SuperEmeraldModel(ModelPart root) {
		this.octagon = root.getChild("octagon");
		this.Top = root.getChild("Top");
		this.octagon2 = this.Top.getChild("octagon2");
		this.octagon3 = this.octagon2.getChild("octagon3");
		this.octagon4 = this.Top.getChild("octagon4");
		this.octagon5 = this.Top.getChild("octagon5");
		this.octagon6 = this.Top.getChild("octagon6");
		this.octagon7 = this.Top.getChild("octagon7");
		this.base = root.getChild("base");
		this.group = this.base.getChild("group");
		this.group2 = this.base.getChild("group2");
		this.group3 = this.base.getChild("group3");
		this.group4 = this.base.getChild("group4");
		this.group5 = this.base.getChild("group5");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition octagon = partdefinition.addOrReplaceChild("octagon", CubeListBuilder.create().texOffs(0, 17).addBox(-3.3137F, -1.5F, -8.0F, 6.6274F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(46, 49).addBox(-8.0F, -1.5F, -3.3137F, 16.0F, 1.0F, 6.6274F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 0.0F));

		PartDefinition octagon_r1 = octagon.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(0, 49).addBox(-8.0F, -0.5F, -3.3137F, 16.0F, 1.0F, 6.6274F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.3137F, -0.5F, -8.0F, 6.6274F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition Top = partdefinition.addOrReplaceChild("Top", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		PartDefinition octagon2 = Top.addOrReplaceChild("octagon2", CubeListBuilder.create().texOffs(40, 34).addBox(-2.8995F, -2.5F, -7.0F, 5.799F, 1.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(68, 64).addBox(-7.0F, -2.5F, -2.8995F, 14.0F, 1.0F, 5.799F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r2 = octagon2.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(68, 57).addBox(-7.0F, -0.5F, -2.8995F, 14.0F, 1.0F, 5.799F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-2.8995F, -0.5F, -7.0F, 5.799F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon3 = octagon2.addOrReplaceChild("octagon3", CubeListBuilder.create().texOffs(46, 15).addBox(-2.8995F, -0.5F, -7.0F, 5.799F, 1.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(40, 71).addBox(-7.0F, -0.5F, -2.8995F, 14.0F, 1.0F, 5.799F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r3 = octagon3.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(0, 70).addBox(-7.0F, -0.5F, -2.8995F, 14.0F, 1.0F, 5.799F, new CubeDeformation(0.0F))
		.texOffs(46, 0).addBox(-2.8995F, -0.5F, -7.0F, 5.799F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon4 = Top.addOrReplaceChild("octagon4", CubeListBuilder.create().texOffs(32, 78).addBox(-2.2782F, -3.5F, -5.5F, 4.5563F, 1.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(86, 0).addBox(-5.5F, -3.5F, -2.2782F, 11.0F, 1.0F, 4.5563F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r4 = octagon4.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(80, 71).addBox(-5.5F, -0.5F, -2.2782F, 11.0F, 1.0F, 4.5563F, new CubeDeformation(0.0F))
		.texOffs(0, 77).addBox(-2.2782F, -0.5F, -5.5F, 4.5563F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon5 = Top.addOrReplaceChild("octagon5", CubeListBuilder.create().texOffs(34, 57).addBox(-2.4853F, 0.5F, -6.0F, 4.9706F, 1.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(80, 30).addBox(-6.0F, 0.5F, -2.4853F, 12.0F, 1.0F, 4.9706F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r5 = octagon5.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(64, 78).addBox(-6.0F, -0.5F, -2.4853F, 12.0F, 1.0F, 4.9706F, new CubeDeformation(0.0F))
		.texOffs(0, 57).addBox(-2.4853F, -0.5F, -6.0F, 4.9706F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon6 = Top.addOrReplaceChild("octagon6", CubeListBuilder.create().texOffs(64, 84).addBox(-2.0711F, 1.5F, -5.0F, 4.1421F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(86, 11).addBox(-5.0F, 1.5F, -2.0711F, 10.0F, 1.0F, 4.1421F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r6 = octagon6.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(86, 6).addBox(-5.0F, -0.5F, -2.0711F, 10.0F, 1.0F, 4.1421F, new CubeDeformation(0.0F))
		.texOffs(80, 36).addBox(-2.0711F, -0.5F, -5.0F, 4.1421F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon7 = Top.addOrReplaceChild("octagon7", CubeListBuilder.create().texOffs(0, 89).addBox(-1.4497F, -4.0F, -3.5F, 2.8995F, 0.5F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(86, 24).addBox(-3.5F, -4.0F, -1.4497F, 7.0F, 0.5F, 2.8995F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r7 = octagon7.addOrReplaceChild("octagon_r7", CubeListBuilder.create().texOffs(46, 30).addBox(-3.5F, 0.0F, -1.4497F, 7.0F, 0.5F, 2.8995F, new CubeDeformation(0.0F))
		.texOffs(86, 16).addBox(-1.4497F, 0.0F, -3.5F, 2.8995F, 0.5F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 21.5F, 2.0F));

		PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(30, 90).addBox(3.3F, 0.2F, -2.1F, 1.0F, 1.0F, 4.2F, new CubeDeformation(0.0F))
		.texOffs(28, 95).addBox(2.55F, 1.3F, -1.4F, 1.0F, 1.0F, 2.8F, new CubeDeformation(0.0F))
		.texOffs(76, 30).addBox(1.6F, 0.4F, -0.7F, 1.3F, 3.0F, 1.35F, new CubeDeformation(0.0F))
		.texOffs(66, 32).addBox(-2.1F, 0.2F, 3.3F, 4.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 96).addBox(-1.4F, 1.3F, 2.55F, 2.8F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 95).addBox(-0.65F, 0.4F, 1.6F, 1.35F, 3.0F, 1.3F, new CubeDeformation(0.0F))
		.texOffs(66, 30).addBox(-2.1F, 0.2F, -4.3F, 4.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 95).addBox(-1.4F, 1.3F, -3.55F, 2.8F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 95).addBox(-0.7F, 0.4F, -2.9F, 1.35F, 3.0F, 1.3F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, -2.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition group = base.addOrReplaceChild("group", CubeListBuilder.create().texOffs(88, 96).addBox(-0.5F, 1.4F, 2.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 52).addBox(-1.5F, -1.6F, 1.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 95).addBox(-1.5F, -1.7F, 0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 47).addBox(-1.5F, -1.8F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group2 = base.addOrReplaceChild("group2", CubeListBuilder.create().texOffs(88, 47).addBox(0.5F, 1.4F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 90).addBox(1.5F, -1.6F, 2.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(56, 90).addBox(2.5F, -1.7F, 2.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(92, 84).addBox(3.5F, -1.8F, 2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group3 = base.addOrReplaceChild("group3", CubeListBuilder.create().texOffs(86, 28).addBox(-1.5F, 1.4F, 4.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(92, 88).addBox(-1.5F, -1.6F, 5.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 95).addBox(-1.5F, -1.7F, 6.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(94, 28).addBox(-1.5F, -1.8F, 7.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(94, 96).addBox(-0.5F, 2.05F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group4 = base.addOrReplaceChild("group4", CubeListBuilder.create().texOffs(48, 96).addBox(-1.5F, 1.4F, 2.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 90).addBox(-2.5F, -1.6F, 2.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(92, 47).addBox(-3.5F, -1.7F, 2.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(92, 92).addBox(-4.5F, -1.8F, 2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group5 = base.addOrReplaceChild("group5", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition cube_r2 = group5.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(20, 94).addBox(-3.55F, 0.3F, -1.4F, 1.0F, 1.0F, 2.8F, new CubeDeformation(0.0F))
		.texOffs(20, 89).addBox(-4.3F, -0.8F, -2.1F, 1.0F, 1.0F, 4.2F, new CubeDeformation(0.0F))
		.texOffs(36, 95).addBox(-2.9F, -0.6F, -0.65F, 1.3F, 3.0F, 1.35F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 4.0F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		octagon.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}