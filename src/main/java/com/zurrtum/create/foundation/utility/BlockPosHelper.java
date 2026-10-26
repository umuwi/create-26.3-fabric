package com.zurrtum.create.foundation.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

// 26.3 убрал конструктор BlockPos(Vec3i).
public class BlockPosHelper {
    public static BlockPos of(Vec3i vec) {
        return vec instanceof BlockPos pos ? pos.immutable() : new BlockPos(vec.getX(), vec.getY(), vec.getZ());
    }
}
