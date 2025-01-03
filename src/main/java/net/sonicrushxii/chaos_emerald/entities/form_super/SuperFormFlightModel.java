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
import net.minecraft.world.entity.player.Player;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SuperFormFlightModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ChaosEmerald.MOD_ID, "super_form_flight"), "main");
	private final ModelPart WholeModel;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;
	private final ModelPart LeftArm;
	private final ModelPart RightArm;
	private final ModelPart Body;
	private final ModelPart Head;

	public SuperFormFlightModel(ModelPart root) {
		this.WholeModel = root.getChild("WholeModel");
		this.LeftLeg = this.WholeModel.getChild("LeftLeg");
		this.RightLeg = this.WholeModel.getChild("RightLeg");
		this.LeftArm = this.WholeModel.getChild("LeftArm");
		this.RightArm = this.WholeModel.getChild("RightArm");
		this.Body = this.WholeModel.getChild("Body");
		this.Head = this.WholeModel.getChild("Head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition WholeModel = partdefinition.addOrReplaceChild("WholeModel", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition LeftLeg = WholeModel.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.9F, 2.0F, 3.0F, 1.1345F, 0.0F, 0.0F));

		PartDefinition RightLeg = WholeModel.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-1.9F, 2.0F, 3.0F, 1.1345F, 0.0F, 0.0F));

		PartDefinition LeftArm = WholeModel.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(5.0F, -4.0F, -5.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition RightArm = WholeModel.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-5.0F, -4.0F, -6.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition Body = WholeModel.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -5.0F, -7.0F, 1.0908F, 0.0F, 0.0F));

		PartDefinition Head = WholeModel.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -6.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -6.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -4.0F, -8.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		WholeModel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public static @Nullable Consumer<ModelPart> getCustomTransform(Player player) {
		Consumer<ModelPart> customTransform = null;
		if(player.getAbilities().flying)
		{
			if(player.getXRot() > -45.0F && player.getXRot() < 45.0F)
			{
				customTransform = (modelPart)->{
					modelPart.getChild("WholeModel").getChild("Head").xRot = (float)(player.getXRot()*Math.PI/180);
				};
			}
			else
			{
				customTransform = (modelPart)->{
					modelPart.getChild("WholeModel").xRot =
							(player.getXRot() < 0) ?
									(float)(0.7222F  *   player.getXRot()    *   Math.PI/180) :
									(float)(1.0000F  *   player.getXRot()    *   Math.PI/180) ;
				};
			}
		}
		return customTransform;
	}
}