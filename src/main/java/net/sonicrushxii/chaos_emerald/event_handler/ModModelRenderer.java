package net.sonicrushxii.chaos_emerald.event_handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.sonicrushxii.chaos_emerald.ChaosEmerald;
import net.sonicrushxii.chaos_emerald.event_handler.client_specific.ClientTickHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public class ModModelRenderer {
    public record Texture(String textureLocation, byte frameNo) {}

    //Used for Animations
    private static String getTextureLocation(Texture[] textures, byte animationLength)
    {
        if(animationLength == 0 || textures.length == 1)
            return textures[0].textureLocation;

        if(animationLength > 20 || 20%animationLength != 0)
            throw new RuntimeException("Incorrect Animation Length, Must be a divisor of 20");

        byte frame = (byte)(ClientTickHandler.clientTick%animationLength);
        while(true)
        {
            for(int i=0;i<textures.length;++i)
                if(frame == textures[i].frameNo)
                    return textures[i].textureLocation;
            frame = (byte) ((frame < 0)?19:frame-1);
        }
    }

    public static void renderModel(Class<? extends EntityModel> modelClass, RenderLivingEvent<?, ?> event, PoseStack poseStack)
    {
        MultiBufferSource buffer = event.getMultiBufferSource();
        LivingEntity entity = event.getEntity();
        int packedLight = event.getPackedLight();

        // Render the custom model
        try {
            //Get Layer Location
            Field layerField = modelClass.getDeclaredField("LAYER_LOCATION");
            ModelLayerLocation layerLocation = (ModelLayerLocation) layerField.get(null);

            //Get Texture Location
            Field textureField = modelClass.getDeclaredField("TEXTURE_LOCATIONS");
            Texture[] textures = (Texture[])textureField.get(null);
            Field animLengthField = modelClass.getDeclaredField("ANIMATION_LENGTH");
            byte animationLength = (byte)animLengthField.get(null);

            EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
            ModelPart modelPart = entityModelSet.bakeLayer(layerLocation);

            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(new ResourceLocation(ChaosEmerald.MOD_ID, getTextureLocation(textures,animationLength))));
            EntityModel model = modelClass.getConstructor(ModelPart.class).newInstance(modelPart);
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }

        catch (NullPointerException | ClassCastException | NoSuchMethodError | NoSuchFieldException |
               NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {}
    }

    public static void renderPlayerModel(Class<? extends EntityModel> modelClass, RenderLivingEvent<?, ?> event, PoseStack poseStack, Consumer<ModelPart> customTransform) {
        MultiBufferSource buffer = event.getMultiBufferSource();
        AbstractClientPlayer player = (AbstractClientPlayer) event.getEntity();
        int packedLight = event.getPackedLight();

        // Render the custom model
        try {
            //Get Layer Location
            Field layerField = modelClass.getDeclaredField("LAYER_LOCATION");
            ModelLayerLocation layerLocation = (ModelLayerLocation) layerField.get(null);

            //Find Model
            EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
            ModelPart modelPart = entityModelSet.bakeLayer(layerLocation);

            //Perform Custom Transform
            if (customTransform != null) customTransform.accept(modelPart);
            VertexConsumer vertexConsumer;

            vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(
                    player.getSkinTextureLocation()
            ));

            EntityModel model = modelClass.getConstructor(ModelPart.class).newInstance(modelPart);
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(player, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        } catch (NullPointerException | ClassCastException | NoSuchMethodError | NoSuchFieldException |
                 NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException ignored) {
        }
    }
}
