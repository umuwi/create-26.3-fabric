package com.zurrtum.create.mixin;

import com.zurrtum.create.api.registry.CreateRegisterPlugin;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Blocks.class)
public class BlocksMixin {
    // 26.3: цикл по BuiltInRegistries.BLOCK ушёл в лямбду SolidDebugger.runAndDump; регистрируемся до неё,
    // чтобы состояния блоков Create тоже попали в BLOCK_STATE_REGISTRY.
    @Inject(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/SolidDebugger;runAndDump(Ljava/lang/Runnable;)V"))
    private static void register(CallbackInfo ci) {
        CreateRegisterPlugin.registerBlock();
    }
}
