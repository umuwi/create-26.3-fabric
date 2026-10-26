package com.zurrtum.create;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

// 26.3 убрал FuelValues: топливо — компонент предмета COOKING_FUEL, а время горения
// считается через LootContext (у угля оно зависит от того, «быстрая» ли печь).
// Предметы Create получают компонент прямо в AllItems (BLAZE_CAKE, картон),
// здесь — чтение так же, как это делает печь.
public class AllFuelTimes {
    public static CookingFuel fuel(int burnTime) {
        return new CookingFuel(
            new ResolvableInt.Constant(burnTime),
            ResolvableFloat.fromKey(ContextFloatProviders.COOKING_DEFAULT_SPEED_MULTIPLIER)
        );
    }

    public static boolean isFuel(ItemStack stack) {
        return stack.has(DataComponents.COOKING_FUEL);
    }

    public static int burnDuration(Level world, @Nullable BlockPos pos, @Nullable BlockState state, ItemStack stack) {
        CookingFuel fuel = stack.get(DataComponents.COOKING_FUEL);
        if (fuel == null) {
            return 0;
        }
        // LootContext строится только на сервере; на клиенте честно отвечаем лишь за константы.
        if (!(world instanceof ServerLevel level)) {
            return fuel.burnTime() instanceof ResolvableInt.Constant(int value) ? value : 0;
        }
        LootParams.Builder params = new LootParams.Builder(level);
        if (pos != null) {
            params.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos));
        }
        params.withOptionalParameter(LootContextParams.BLOCK_STATE, state);
        LootContext context = new LootContext.Builder(params.create(LootContextParamSets.ALL_PARAMS)).create(Optional.empty());
        return fuel.burnTime().get(context, 0);
    }

    public static void register() {
    }
}
