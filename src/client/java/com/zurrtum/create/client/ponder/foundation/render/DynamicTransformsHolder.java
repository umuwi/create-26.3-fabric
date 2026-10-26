package com.zurrtum.create.client.ponder.foundation.render;

import org.jspecify.annotations.Nullable;

public interface DynamicTransformsHolder {
    void ponder$updateTransforms(@Nullable GpuBufferSlice dynamicTransforms);
}
