package com.zurrtum.create.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.zurrtum.create.client.Create;
import com.zurrtum.create.client.content.equipment.armor.NetheriteBacktankFirstPersonRenderer;
import com.zurrtum.create.client.content.equipment.extendoGrip.ExtendoGripRenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// 26.3: ItemInHandRenderer заменён на FirstPersonHandsAndItemsRenderer с render state;
// руку на карте и пустую руку теперь рисует один renderPlayerHand.
@Mixin(FirstPersonHandsAndItemsRenderer.class)
public class ItemInHandRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @WrapOperation(method = "submitHandsWithItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FirstPersonHandsAndItemsRenderer;submitArmWithItem(Lnet/minecraft/client/renderer/state/level/PlayerRenderState;Lnet/minecraft/client/renderer/state/level/FirstPersonHandsAndItemsRenderState;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V"))
    private void renderItem(
        FirstPersonHandsAndItemsRenderer instance,
        PlayerRenderState playerState,
        FirstPersonHandsAndItemsRenderState state,
        float frameInterp,
        float xRot,
        InteractionHand hand,
        float attack,
        ItemStack itemStack,
        float inverseArmHeight,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords,
        Operation<Void> original
    ) {
        EntityRenderDispatcher entityRenderDispatcher = minecraft.getEntityRenderDispatcher();
        if (Create.ZAPPER_RENDER_HANDLER.onRenderPlayerHand(
            itemStack,
            minecraft,
            entityRenderDispatcher,
            state,
            poseStack,
            submitNodeCollector,
            lightCoords,
            frameInterp,
            hand,
            inverseArmHeight,
            attack
        ) || Create.POTATO_CANNON_RENDER_HANDLER.onRenderPlayerHand(
            itemStack,
            minecraft,
            entityRenderDispatcher,
            state,
            poseStack,
            submitNodeCollector,
            lightCoords,
            frameInterp,
            hand,
            inverseArmHeight,
            attack
        ) || ExtendoGripRenderHandler.onRenderPlayerHand(
            itemStack,
            minecraft,
            entityRenderDispatcher,
            poseStack,
            submitNodeCollector,
            lightCoords,
            hand,
            inverseArmHeight,
            attack
        )) {
            return;
        }
        original.call(
            instance,
            playerState,
            state,
            frameInterp,
            xRot,
            hand,
            attack,
            itemStack,
            inverseArmHeight,
            poseStack,
            submitNodeCollector,
            lightCoords
        );
    }

    @WrapOperation(method = "renderPlayerHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/ClientAsset$Texture;texturePath()Lnet/minecraft/resources/Identifier;"))
    private Identifier getHandTexture(ClientAsset.Texture instance, Operation<Identifier> original) {
        Identifier id = NetheriteBacktankFirstPersonRenderer.getHandTexture(minecraft.player);
        if (id != null) {
            return id;
        }
        return original.call(instance);
    }
}
