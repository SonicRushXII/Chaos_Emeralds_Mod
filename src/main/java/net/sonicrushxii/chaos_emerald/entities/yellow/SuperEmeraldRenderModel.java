package net.sonicrushxii.chaos_emerald.entities.yellow;// Made with Blockbench 4.11.2
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
import net.sonicrushxii.chaos_emerald.event_handler.ModModelRenderer;

public class SuperEmeraldRenderModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ChaosEmerald.MOD_ID, "super_emerald/render_model"), "main");
	public static final ModModelRenderer.Texture[] TEXTURE_LOCATIONS = {
			new ModModelRenderer.Texture("textures/block/yellow_emerald_texture",(byte)0)
	};
	private final ModelPart emerald;
	private final ModelPart base;
	private final ModelPart group2;
	private final ModelPart group3;
	private final ModelPart group4;
	private final ModelPart group5;
	private final ModelPart group;
	private final ModelPart Top;
	private final ModelPart octagon2;
	private final ModelPart octagon3;
	private final ModelPart octagon4;
	private final ModelPart octagon5;
	private final ModelPart octagon6;
	private final ModelPart octagon7;
	private final ModelPart octagon;

	public SuperEmeraldRenderModel(ModelPart root) {
		this.emerald = root.getChild("emerald");
		this.base = this.emerald.getChild("base");
		this.group2 = this.base.getChild("group2");
		this.group3 = this.base.getChild("group3");
		this.group4 = this.base.getChild("group4");
		this.group5 = this.base.getChild("group5");
		this.group = this.base.getChild("group");
		this.Top = this.emerald.getChild("Top");
		this.octagon2 = this.Top.getChild("octagon2");
		this.octagon3 = this.octagon2.getChild("octagon3");
		this.octagon4 = this.Top.getChild("octagon4");
		this.octagon5 = this.Top.getChild("octagon5");
		this.octagon6 = this.Top.getChild("octagon6");
		this.octagon7 = this.Top.getChild("octagon7");
		this.octagon = this.emerald.getChild("octagon");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition emerald = partdefinition.addOrReplaceChild("emerald", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition base = emerald.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 4.5F, 2.0F));

		PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 0).addBox(3.3F, 0.2F, -2.1F, 1.0F, 1.0F, 4.2F, new CubeDeformation(0.0F))
		.texOffs(31, 37).addBox(2.55F, 1.3F, -1.4F, 1.0F, 1.0F, 2.8F, new CubeDeformation(0.0F))
		.texOffs(35, 19).addBox(1.6F, 0.4F, -0.7F, 1.3F, 3.0F, 1.35F, new CubeDeformation(0.0F))
		.texOffs(1, 25).mirror().addBox(-2.1F, 0.2F, 3.3F, 4.2F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(35, 37).addBox(-1.4F, 1.3F, 2.55F, 2.8F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(3, 17).addBox(-0.65F, 0.4F, 1.6F, 1.35F, 3.0F, 1.3F, new CubeDeformation(0.0F))
		.texOffs(34, 15).addBox(-2.1F, 0.2F, -4.3F, 4.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 15).mirror().addBox(-1.4F, 1.3F, -3.55F, 2.8F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(2, 13).mirror().addBox(-0.7F, 0.4F, -2.9F, 1.35F, 3.0F, 1.3F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.5F, -2.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition group2 = base.addOrReplaceChild("group2", CubeListBuilder.create().texOffs(13, 8).mirror().addBox(-0.5F, 1.4F, 2.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(4, 9).addBox(-1.5F, -1.6F, 1.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 11).addBox(-1.5F, -1.7F, 0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 17).addBox(-1.5F, -1.8F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group3 = base.addOrReplaceChild("group3", CubeListBuilder.create().texOffs(13, 11).addBox(0.5F, 1.4F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 19).addBox(1.5F, -1.6F, 2.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(23, 34).addBox(2.5F, -1.7F, 2.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(3, 3).addBox(3.5F, -1.8F, 2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group4 = base.addOrReplaceChild("group4", CubeListBuilder.create().texOffs(16, 22).addBox(-1.5F, 1.4F, 4.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 27).addBox(-1.5F, -1.6F, 5.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 6).addBox(-1.5F, -1.7F, 6.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(27, 37).mirror().addBox(-1.5F, -1.8F, 7.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(4, 4).mirror().addBox(-0.5F, 2.05F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group5 = base.addOrReplaceChild("group5", CubeListBuilder.create().texOffs(20, 37).addBox(-1.5F, 1.4F, 2.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(3, 37).addBox(-2.5F, -1.6F, 2.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(6, 37).addBox(-3.5F, -1.7F, 2.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(15, 37).addBox(-4.5F, -1.8F, 2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition group = base.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -6.0F));

		PartDefinition cube_r2 = group.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(27, 19).addBox(-3.55F, 0.3F, -1.4F, 1.0F, 1.0F, 2.8F, new CubeDeformation(0.0F))
		.texOffs(28, 33).addBox(-4.3F, -0.8F, -2.1F, 1.0F, 1.0F, 4.2F, new CubeDeformation(0.0F))
		.texOffs(27, 12).addBox(-2.9F, -0.6F, -0.65F, 1.3F, 3.0F, 1.35F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 4.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Top = emerald.addOrReplaceChild("Top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon2 = Top.addOrReplaceChild("octagon2", CubeListBuilder.create().texOffs(0, 1).addBox(-2.8995F, -2.5F, -7.0F, 5.799F, 1.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-7.0F, -2.5F, -2.8995F, 14.0F, 1.0F, 5.799F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r1 = octagon2.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(0, 1).addBox(-7.0F, -0.5F, -2.8995F, 14.0F, 1.0F, 5.799F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-2.8995F, -0.5F, -7.0F, 5.799F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon3 = octagon2.addOrReplaceChild("octagon3", CubeListBuilder.create().texOffs(0, 1).addBox(-2.8995F, -0.5F, -7.0F, 5.799F, 1.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-7.0F, -0.5F, -2.8995F, 14.0F, 1.0F, 5.799F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r2 = octagon3.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(0, 1).addBox(-7.0F, -0.5F, -2.8995F, 14.0F, 1.0F, 5.799F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-2.8995F, -0.5F, -7.0F, 5.799F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon4 = Top.addOrReplaceChild("octagon4", CubeListBuilder.create().texOffs(0, 1).addBox(-2.2782F, -3.5F, -5.5F, 4.5563F, 1.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-5.5F, -3.5F, -2.2782F, 11.0F, 1.0F, 4.5563F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r3 = octagon4.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(0, 1).addBox(-5.5F, -0.5F, -2.2782F, 11.0F, 1.0F, 4.5563F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-2.2782F, -0.5F, -5.5F, 4.5563F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon5 = Top.addOrReplaceChild("octagon5", CubeListBuilder.create().texOffs(0, 1).addBox(-2.4853F, 0.5F, -6.0F, 4.9706F, 1.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-6.0F, 0.5F, -2.4853F, 12.0F, 1.0F, 4.9706F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r4 = octagon5.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(0, 1).addBox(-6.0F, -0.5F, -2.4853F, 12.0F, 1.0F, 4.9706F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-2.4853F, -0.5F, -6.0F, 4.9706F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon6 = Top.addOrReplaceChild("octagon6", CubeListBuilder.create().texOffs(0, 1).addBox(-2.0711F, 1.5F, -5.0F, 4.1421F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-5.0F, 1.5F, -2.0711F, 10.0F, 1.0F, 4.1421F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r5 = octagon6.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(0, 1).addBox(-5.0F, -0.5F, -2.0711F, 10.0F, 1.0F, 4.1421F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-2.0711F, -0.5F, -5.0F, 4.1421F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon7 = Top.addOrReplaceChild("octagon7", CubeListBuilder.create().texOffs(0, 1).addBox(-1.4497F, -4.0F, -3.5F, 2.8995F, 0.5F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-3.5F, -4.0F, -1.4497F, 7.0F, 0.5F, 2.8995F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r6 = octagon7.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(0, 1).addBox(-3.5F, 0.0F, -1.4497F, 7.0F, 0.5F, 2.8995F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-1.4497F, 0.0F, -3.5F, 2.8995F, 0.5F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition octagon = emerald.addOrReplaceChild("octagon", CubeListBuilder.create().texOffs(5, 1).addBox(-3.3137F, -1.5F, -8.0F, 6.6274F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(5, 1).addBox(-8.0F, -1.5F, -3.3137F, 16.0F, 1.0F, 6.6274F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition octagon_r7 = octagon.addOrReplaceChild("octagon_r7", CubeListBuilder.create().texOffs(5, 1).addBox(-8.0F, -0.5F, -3.3137F, 16.0F, 1.0F, 6.6274F, new CubeDeformation(0.0F))
		.texOffs(5, 1).addBox(-3.3137F, -0.5F, -8.0F, 6.6274F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		emerald.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}