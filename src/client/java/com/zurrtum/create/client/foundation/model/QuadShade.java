package com.zurrtum.create.client.foundation.model;

import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.Direction;

// 26.3: в BakedQuad.MaterialInfo вместо boolean shade хранится shadeDirectionOverride —
// грань, по которой берётся яркость. Старое shade=false («без затенения») датафиксер
// переводит в override = UP (яркость верха = 1.0), поэтому затенённым считаем всё остальное.
public final class QuadShade {
    private QuadShade() {
    }

    public static boolean isShaded(BakedQuad.MaterialInfo info) {
        return info.shadeDirectionOverride() != Direction.UP;
    }
}
