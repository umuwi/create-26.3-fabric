package com.zurrtum.create.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.llamalad7.mixinextras.sugar.Local;
import com.zurrtum.create.content.kinetics.mixer.PotionRecipe;
import com.zurrtum.create.content.processing.sequenced.SequencedAssemblyRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.TreeMap;

// 26.3: рецепты стали реестром данных, RecipeManager.prepare больше нет. Сгенерированные
// рецепты (последовательная сборка, зелья в миксере) добавляем в RecipeMap при его сборке.
@Mixin(RecipeMap.class)
public class RecipeMapMixin {
    @Inject(method = "create(Lnet/minecraft/core/HolderLookup;)Lnet/minecraft/world/item/crafting/RecipeMap;", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;forEach(Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER))
    private static void addGeneratedRecipes(
        HolderLookup<Recipe<?>> recipes,
        CallbackInfoReturnable<RecipeMap> cir,
        @Local ImmutableMultimap.Builder<RecipeType<?>, RecipeHolder<?>> byType,
        @Local ImmutableMap.Builder<ResourceKey<Recipe<?>>, RecipeHolder<?>> byKey
    ) {
        Map<Identifier, Recipe<?>> generated = new TreeMap<>(SequencedAssemblyRecipe.GENERATE_RECIPES);
        PotionRecipe.register(generated);
        for (Map.Entry<Identifier, Recipe<?>> entry : generated.entrySet()) {
            ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, entry.getKey());
            if (recipes.get(key).isPresent()) {
                continue;
            }
            RecipeHolder<?> holder = new RecipeHolder<>(key, entry.getValue());
            byType.put(entry.getValue().getType(), holder);
            byKey.put(key, holder);
        }
    }
}
