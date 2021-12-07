package com.maciej916.indreb.datagen.recipes.machines;

import com.maciej916.indreb.common.registries.ModBlocks;
import com.maciej916.indreb.common.registries.ModItems;
import com.maciej916.indreb.datagen.recipes.builder.RecipeBuilderCrushing;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class Crushing extends RecipeProvider {

    public Crushing(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        RecipeBuilderCrushing.builder(Items.BLAZE_POWDER,5)
                .setIngredient(Ingredient.of(Items.BLAZE_ROD), 1)
                .setExperience(0.1F)
                .setGroup("crushing")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BLAZE_ROD))
                .save(consumer, "blaze_rod_blaze_powder");

        RecipeBuilderCrushing.builder(Items.BONE_MEAL,14)
                .setIngredient(Ingredient.of(ItemTags.bind("forge:bones")), 1)
                .setExperience(0.1F)
                .setGroup("crushing")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE))
                .save(consumer, "bones_bone_meal");

        RecipeBuilderCrushing.builder(Items.CLAY_BALL,2)
                .setIngredient(Ingredient.of(Items.CLAY), 1)
                .setExperience(0.1F)
                .setGroup("crushing")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY))
                .save(consumer, "clay_clay_ball");

        RecipeBuilderCrushing.builder(Items.REDSTONE,9)
                .setIngredient(Ingredient.of(Items.REDSTONE_BLOCK), 1)
                .setGroup("crushing")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE_BLOCK))
                .save(consumer, "redstone_block_redstone");

        RecipeBuilderCrushing.builder(Items.GUNPOWDER,1)
                .setIngredient(Ingredient.of(Items.FLINT), 1)
                .setExperience(0.1F)
                .setGroup("crushing")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(consumer, "flint_gunpowder");

        RecipeBuilderCrushing.builder(Items.FLINT,1)
                .setIngredient(Ingredient.of(ItemTags.bind("forge:gravel")), 1)
                .setExperience(0.1F)
                .setGroup("crushing")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GRAVEL))
                .save(consumer, "gravel_flint");

        RecipeBuilderCrushing.builder(Items.QUARTZ,4)
                .setIngredient(Ingredient.of(Items.SMOOTH_QUARTZ), 1)
                .setGroup("crushing")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SMOOTH_QUARTZ))
                .save(consumer, "smooth_quartz_quartz");

        RecipeBuilderCrushing.builder(Items.SUGAR,1)
                .setIngredient(Ingredient.of(Items.SUGAR_CANE), 1)
                .setExperience(0.1F)
                .setGroup("crushing")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SUGAR_CANE))
                .save(consumer, "sugarcane_sugar");

        RecipeBuilderCrushing.builder(Items.STRING,4)
                .setIngredient(Ingredient.of(ItemTags.bind("minecraft:wool")), 1)
                .setGroup("crushing")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHITE_WOOL))
                .save(consumer, "wool_string");

        // Bricks

        RecipeBuilderCrushing.builder(Items.BRICK,4)
                .setIngredient(Ingredient.of(Items.BRICKS), 1)
                .setGroup("crushing/bricks")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BRICKS))
                .save(consumer, "bricks_brick");

        // Stones

        RecipeBuilderCrushing.builder(Items.GRAVEL,1)
                .setIngredient(Ingredient.of(Items.COBBLESTONE), 1)
                .setGroup("crushing/stones")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COBBLESTONE))
                .save(consumer, "cobblestone_gravel");

        RecipeBuilderCrushing.builder(Items.GLOWSTONE_DUST,4)
                .setIngredient(Ingredient.of(Items.GLOWSTONE), 1)
                .setGroup("crushing/stones")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLOWSTONE))
                .save(consumer, "glowstone_glowstone_dust");

        RecipeBuilderCrushing.builder(Items.COBBLESTONE,1)
                .setIngredient(Ingredient.of(Items.MOSSY_COBBLESTONE), 1)
                .setBonus(Items.VINE, 1, 80)
                .setGroup("crushing/stones")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COBBLESTONE))
                .save(consumer, "mossy_cobblestone_cobblestone");

        RecipeBuilderCrushing.builder(Items.RED_SAND,2)
                .setIngredient(Ingredient.of(Items.RED_SANDSTONE), 1)
                .setGroup("crushing/stones")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.RED_SANDSTONE))
                .save(consumer,"red_sandstone_red_sand");

        RecipeBuilderCrushing.builder(Items.SAND,2)
                .setIngredient(Ingredient.of(Items.SANDSTONE), 1)
                .setGroup("crushing/stones")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SANDSTONE))
                .save(consumer, "sandstone_sand");

        RecipeBuilderCrushing.builder(Items.STONE,4)
                .setIngredient(Ingredient.of(Items.STONE_BRICKS), 1)
                .setGroup("crushing/stones")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STONE_BRICKS))
                .save(consumer, "stone_bricks_stone");

        RecipeBuilderCrushing.builder(Items.COBBLESTONE,1)
                .setIngredient(Ingredient.of(Items.STONE), 1)
                .setGroup("crushing/stones")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STONE))
                .save(consumer, "stone_cobblestone");

        // Ores

        RecipeBuilderCrushing.builder(ModItems.CRUSHED_COPPER,2)
                .setIngredient(Ingredient.of(ItemTags.bind("forge:ores/copper")), 1)
                .setExperience(0.2F)
                .setBonus(Items.COBBLESTONE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_ORE.asItem()))
                .save(consumer, "copper_ore_crushed_copper");

        RecipeBuilderCrushing.builder(ModItems.CRUSHED_TIN,2)
                .setIngredient(Ingredient.of(ItemTags.bind("forge:ores/tin")), 1)
                .setExperience(0.2F)
                .setBonus(Items.COBBLESTONE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.TIN_ORE.asItem()))
                .save(consumer, "tin_ore_crushed_tin");

        RecipeBuilderCrushing.builder(Items.DIAMOND,2)
                .setIngredient(Ingredient.of(ItemTags.bind("forge:ores/diamond")), 1)
                .setExperience(0.2F)
                .setBonus(Items.COBBLESTONE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(consumer,"diamond_ore_diamond");

        RecipeBuilderCrushing.builder(Items.EMERALD,2)
                .setIngredient(Ingredient.of(ItemTags.bind("forge:ores/emerald")), 1)
                .setExperience(0.2F)
                .setBonus(Items.COBBLESTONE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.EMERALD))
                .save(consumer, "emerald_ore_emerald");

        RecipeBuilderCrushing.builder(ModItems.CRUSHED_GOLD,2)
                .setIngredient(Ingredient.of(ItemTags.bind("forge:ores/gold")), 1)
                .setExperience(0.2F)
                .setBonus(Items.COBBLESTONE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_ORE))
                .save(consumer, "gold_ore_crushed_gold");

        RecipeBuilderCrushing.builder(ModItems.CRUSHED_IRON,2)
                .setIngredient(Ingredient.of(ItemTags.bind("forge:ores/iron")), 1)
                .setExperience(0.2F)
                .setBonus(Items.COBBLESTONE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_ORE))
                .save(consumer, "iron_ore_crushed_iron");

        // Deepslate Ores

        RecipeBuilderCrushing.builder(ModItems.CRUSHED_COPPER,2)
                .setIngredient(Ingredient.of(Items.DEEPSLATE_COPPER_ORE), 1)
                .setExperience(0.2F)
                .setBonus(Items.DEEPSLATE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DEEPSLATE_COPPER_ORE.asItem()))
                .save(consumer, "deepslate_copper_ore_crushed_copper");

        RecipeBuilderCrushing.builder(ModItems.CRUSHED_TIN,2)
                .setIngredient(Ingredient.of(ModBlocks.DEEPSLATE_TIN_ORE), 1)
                .setExperience(0.2F)
                .setBonus(Items.DEEPSLATE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.DEEPSLATE_TIN_ORE.asItem()))
                .save(consumer, "deepslate_tin_ore_crushed_tin");

        RecipeBuilderCrushing.builder(Items.DIAMOND,2)
                .setIngredient(Ingredient.of(Items.DEEPSLATE_DIAMOND_ORE), 1)
                .setExperience(0.2F)
                .setBonus(Items.DEEPSLATE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DEEPSLATE_DIAMOND_ORE))
                .save(consumer,"deepslate_diamond_ore_diamond");

        RecipeBuilderCrushing.builder(Items.EMERALD,2)
                .setIngredient(Ingredient.of(Items.DEEPSLATE_EMERALD_ORE), 1)
                .setExperience(0.2F)
                .setBonus(Items.DEEPSLATE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DEEPSLATE_EMERALD_ORE))
                .save(consumer, "deepslate_emerald_ore_emerald");

        RecipeBuilderCrushing.builder(ModItems.CRUSHED_GOLD,2)
                .setIngredient(Ingredient.of(Items.DEEPSLATE_GOLD_ORE), 1)
                .setExperience(0.2F)
                .setBonus(Items.DEEPSLATE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DEEPSLATE_GOLD_ORE))
                .save(consumer, "deepslate_gold_ore_crushed_gold");

        RecipeBuilderCrushing.builder(ModItems.CRUSHED_IRON,2)
                .setIngredient(Ingredient.of(Items.DEEPSLATE_IRON_ORE), 1)
                .setExperience(0.2F)
                .setBonus(Items.DEEPSLATE, 1, 50)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DEEPSLATE_IRON_ORE))
                .save(consumer, "deepslate_iron_ore_crushed_iron");


        // Flowers

        RecipeBuilderCrushing.builder(Items.MAGENTA_DYE,1)
                .setIngredient(Ingredient.of(Items.ALLIUM), 1)
                .setExperience(0.1F)
                .setBonus(Items.MAGENTA_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ALLIUM))
                .save(consumer, "allium_magenta_dye");

        RecipeBuilderCrushing.builder(Items.LIGHT_GRAY_DYE,1)
                .setIngredient(Ingredient.of(Items.AZURE_BLUET), 1)
                .setExperience(0.1F)
                .setBonus(Items.LIGHT_GRAY_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.AZURE_BLUET))
                .save(consumer, "azure_bluet_light_gray_dye");

        RecipeBuilderCrushing.builder(Items.LIGHT_BLUE_DYE,1)
                .setIngredient(Ingredient.of(Items.BLUE_ORCHID), 1)
                .setExperience(0.1F)
                .setBonus(Items.LIGHT_BLUE_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BLUE_ORCHID))
                .save(consumer, "blue_orchid_light_blue_dye");

        RecipeBuilderCrushing.builder(Items.BLUE_DYE,1)
                .setIngredient(Ingredient.of(Items.CORNFLOWER), 1)
                .setExperience(0.1F)
                .setBonus(Items.BLUE_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CORNFLOWER))
                .save(consumer, "cornflower_blue_dye");

        RecipeBuilderCrushing.builder(Items.YELLOW_DYE,1)
                .setIngredient(Ingredient.of(Items.DANDELION), 1)
                .setExperience(0.1F)
                .setBonus(Items.YELLOW_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DANDELION))
                .save(consumer, "dandelion_yellow_dye");

        RecipeBuilderCrushing.builder(Items.MAGENTA_DYE,2)
                .setIngredient(Ingredient.of(Items.LILAC), 1)
                .setExperience(0.1F)
                .setBonus(Items.MAGENTA_DYE, 1, 50)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.LILAC))
                .save(consumer, "lilac_magenta_dye");

        RecipeBuilderCrushing.builder(Items.WHITE_DYE,1)
                .setIngredient(Ingredient.of(Items.LILY_OF_THE_VALLEY), 1)
                .setExperience(0.1F)
                .setBonus(Items.WHITE_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.LILY_OF_THE_VALLEY))
                .save(consumer, "lily_of_the_valley_white_dye");

        RecipeBuilderCrushing.builder(Items.ORANGE_DYE,1)
                .setIngredient(Ingredient.of(Items.ORANGE_TULIP), 1)
                .setExperience(0.1F)
                .setBonus(Items.ORANGE_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ORANGE_TULIP))
                .save(consumer, "orange_tulip_orange_dye");

        RecipeBuilderCrushing.builder(Items.LIGHT_GRAY_DYE,1)
                .setIngredient(Ingredient.of(Items.OXEYE_DAISY), 1)
                .setExperience(0.1F)
                .setBonus(Items.LIGHT_GRAY_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.OXEYE_DAISY))
                .save(consumer, "oxeye_daisy_light_gray_dye");

        RecipeBuilderCrushing.builder(Items.PINK_DYE,2)
                .setIngredient(Ingredient.of(Items.PEONY), 1)
                .setExperience(0.1F)
                .setBonus(Items.PINK_DYE, 1, 50)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.PEONY))
                .save(consumer, "peony_pink_dye");

        RecipeBuilderCrushing.builder(Items.PINK_DYE,1)
                .setIngredient(Ingredient.of(Items.PINK_TULIP), 1)
                .setExperience(0.1F)
                .setBonus(Items.PINK_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.PINK_TULIP))
                .save(consumer, "pink_tulip_pink_dye");

        RecipeBuilderCrushing.builder(Items.RED_DYE,1)
                .setIngredient(Ingredient.of(Items.POPPY), 1)
                .setExperience(0.1F)
                .setBonus(Items.RED_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.PINK_TULIP))
                .save(consumer, "poppy_red_dye");

        RecipeBuilderCrushing.builder(Items.RED_DYE,1)
                .setIngredient(Ingredient.of(Items.RED_TULIP), 1)
                .setExperience(0.1F)
                .setBonus(Items.RED_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.RED_TULIP))
                .save(consumer, "red_tulip_red_dye");

        RecipeBuilderCrushing.builder(Items.RED_DYE,2)
                .setIngredient(Ingredient.of(Items.ROSE_BUSH), 1)
                .setExperience(0.1F)
                .setBonus(Items.RED_DYE, 1, 50)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ROSE_BUSH))
                .save(consumer, "rose_bush_red_dye");

        RecipeBuilderCrushing.builder(Items.YELLOW_DYE,2)
                .setIngredient(Ingredient.of(Items.SUNFLOWER), 1)
                .setExperience(0.1F)
                .setBonus(Items.YELLOW_DYE, 1, 50)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SUNFLOWER))
                .save(consumer, "sunflower_yellow_dye");

        RecipeBuilderCrushing.builder(Items.GRAY_DYE,1)
                .setIngredient(Ingredient.of(Items.WHITE_TULIP), 1)
                .setExperience(0.1F)
                .setBonus(Items.GRAY_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHITE_TULIP))
                .save(consumer, "white_tulip_light_gray_dye");

        RecipeBuilderCrushing.builder(Items.BLACK_DYE,1)
                .setIngredient(Ingredient.of(Items.WITHER_ROSE), 1)
                .setExperience(0.1F)
                .setBonus(Items.BLACK_DYE, 1, 25)
                .setGroup("crushing/flowers")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WITHER_ROSE))
                .save(consumer, "wither_rose_black_dye");

        // Dust

        RecipeBuilderCrushing.builder(ModItems.COAL_DUST,1)
                .setIngredient(Ingredient.of(Items.COAL), 1)
                .setExperience(0.1F)
                .setGroup("crushing/dust")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer, "coal_coal_dust");

        RecipeBuilderCrushing.builder(ModItems.CRUSHED_GOLD,3)
                .setIngredient(Ingredient.of(Items.NETHER_GOLD_ORE), 1)
                .setExperience(0.4F)
                .setBonus(Items.NETHERRACK, 1, 75)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHER_GOLD_ORE))
                .save(consumer, "nether_gold_crushed_gold");

        RecipeBuilderCrushing.builder(Items.NETHERITE_SCRAP,2)
                .setIngredient(Ingredient.of(Items.ANCIENT_DEBRIS), 1)
                .setExperience(2.0F)
                .setGroup("crushing/ores")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ANCIENT_DEBRIS))
                .save(consumer, "ancient_debris_netherite_scrap");

        RecipeBuilderCrushing.builder(ModItems.DIAMOND_DUST,1)
                .setIngredient(Ingredient.of(Items.DIAMOND), 1)
                .setExperience(2.0F)
                .setGroup("crushing/items")
                .addCriterion("item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(consumer, "diamond_dust");


    }

}
