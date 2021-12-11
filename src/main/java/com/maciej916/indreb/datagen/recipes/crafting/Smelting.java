package com.maciej916.indreb.datagen.recipes.crafting;

import com.maciej916.indreb.common.registries.ModBlocks;
import com.maciej916.indreb.common.registries.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.maciej916.indreb.IndReb.MODID;

public class Smelting extends RecipeProvider {

    public Smelting(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private ResourceLocation saveResource(String name) {
        return new ResourceLocation(MODID, "smelting/" + name);
    }
    
    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.STICKY_RESIN), ModItems.RUBBER, 0.1f, 160)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.RUBBER_LOG))
                .save(consumer, saveResource("rubber"));
        
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_TIN), ModItems.TIN_INGOT, 0.8f, 160)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.TIN_ORE))
                .save(consumer, saveResource("raw_tin"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.TIN_ORE), ModItems.TIN_INGOT, 0.8f, 160)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.TIN_ORE))
                .save(consumer, saveResource("tin_ingot"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.DEEPSLATE_TIN_ORE), ModItems.TIN_INGOT, 0.8f, 160)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.DEEPSLATE_TIN_ORE))
                .save(consumer, saveResource("deepslate_tin_ingot"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CRUSHED_COPPER), Items.COPPER_INGOT, 0.6f, 160)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRUSHED_COPPER))
                .save(consumer, saveResource("copper_ingot2"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CRUSHED_TIN), ModItems.TIN_INGOT, 0.8f, 160)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRUSHED_TIN))
                .save(consumer, saveResource("tin_ingot2"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CRUSHED_GOLD), Items.GOLD_INGOT, 0.8f, 160)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRUSHED_GOLD))
                .save(consumer, saveResource("gold_ingot"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CRUSHED_IRON), Items.IRON_INGOT, 0.8f, 160)
                .unlockedBy("item", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRUSHED_IRON))
                .save(consumer, saveResource("iron_ingot"));

    }

}