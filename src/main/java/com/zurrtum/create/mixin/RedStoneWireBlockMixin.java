package com.zurrtum.create.mixin;

import com.zurrtum.create.foundation.block.RedStoneConnectBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// 26.3: RedStoneWireBlock переименован в RedstoneWireBlock, а решение «тянуть ли провод к блоку»
// перенесено в BlockState.shouldRedstoneWireConnectTo — туда и подключаем RedStoneConnectBlock.
@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class RedStoneWireBlockMixin {
    @Shadow
    protected abstract BlockState asState();

    @Inject(method = "shouldRedstoneWireConnectTo(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z", at = @At("HEAD"), cancellable = true)
    private void connectsTo(BlockGetter level, BlockPos pos, @Nullable Direction direction, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = asState();
        if (state.getBlock() instanceof RedStoneConnectBlock block) {
            cir.setReturnValue(block.canConnectRedstone(state, direction));
        }
    }
}
