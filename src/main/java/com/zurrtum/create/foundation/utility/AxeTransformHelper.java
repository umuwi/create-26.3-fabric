package com.zurrtum.create.foundation.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

// 26.3: AxeItem/ShovelItem убраны, обдирание брёвен и соскабливание меди теперь —
// данные BlockTransformer в компоненте block_transformer у топора. Наждачка Create
// применяет тот же набор правил к блоку в мире (провайдеры читают состояние по позиции).
public class AxeTransformHelper {
    public static Optional<BlockState> getTransformed(LevelAccessor level, BlockPos pos, @Nullable Direction face) {
        Holder<BlockTransformer> transformer = Items.DIAMOND_AXE.getDefaultInstance().get(DataComponents.BLOCK_TRANSFORMER);
        if (transformer == null) {
            return Optional.empty();
        }
        for (BlockTransformer.BlockTransformData data : transformer.value().transforms()) {
            if (face != null && data.disallowedFaces().contains(face)) {
                continue;
            }
            BlockState state = data.blockStateProvider().value().getOptionalState(level, level.getRandom(), pos);
            if (state != null) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
