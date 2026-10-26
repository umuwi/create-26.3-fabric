package com.zurrtum.create.foundation.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

// 26.3 сделал BlockItem.updateCustomBlockEntityTag статическим, переопределять стало нечего.
// Предметы Create, которым нужно доделать блок-сущность после установки, реализуют этот
// интерфейс, а BlockItemMixin вызывает его вместо статического метода в BlockItem.place.
// Поведение по умолчанию — BlockItem.updateCustomBlockEntityTag(level, player, pos, stack).
public interface CustomBlockEntityTagItem {
    boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state);
}
