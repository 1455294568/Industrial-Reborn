package com.maciej916.indreb.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonConfig {
	public static ForgeConfigSpec.BooleanValue worldgen_enabled;

	public static ForgeConfigSpec.BooleanValue worldgen_tin_enabled;
	public static ForgeConfigSpec.IntValue worldgen_tin_placement;
	public static ForgeConfigSpec.IntValue worldgen_tin_placement_offset;
	public static ForgeConfigSpec.IntValue worldgen_tin_placement_vertical;
	public static ForgeConfigSpec.IntValue worldgen_tin_size;
	public static ForgeConfigSpec.IntValue worldgen_tin_size_large;

	public static ForgeConfigSpec.BooleanValue worldgen_rubber_tree_enabled;
	public static ForgeConfigSpec.DoubleValue worldgen_rubber_tree_chance;
	public static ForgeConfigSpec.DoubleValue worldgen_rubber_tree_chance_rich;
	public static ForgeConfigSpec.ConfigValue<List<String>> worldgen_rubber_tree_chance_biomes;
	public static ForgeConfigSpec.ConfigValue<List<String>> worldgen_rubber_tree_chance_biomes_rich;

	public static ForgeConfigSpec initCommon() {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

		builder.push("general");

		builder.pop();

		builder.push("worldgen");
		worldgen_enabled = builder.define("enabled", true);

		builder.push("tin");
		worldgen_tin_enabled = builder.define("enabled", true);
		worldgen_tin_placement = builder.defineInRange("worldgen_tin_placement", 16, 0, Integer.MAX_VALUE);
		worldgen_tin_placement_offset = builder.defineInRange("worldgen_tin_placement_offset", -16, 0, Integer.MAX_VALUE);
		worldgen_tin_placement_vertical = builder.defineInRange("worldgen_tin_placement_vertical", 112, 0, Integer.MAX_VALUE);
		worldgen_tin_size = builder.defineInRange("size", 10, 1, Integer.MAX_VALUE);
		worldgen_tin_size_large = builder.defineInRange("size", 20, 1, Integer.MAX_VALUE);
		builder.pop();

		builder.push("rubber_tree");
		worldgen_rubber_tree_enabled = builder.define("enabled", true);
		worldgen_rubber_tree_chance = builder.defineInRange("chance", 0.05F, 0, 1);
		worldgen_rubber_tree_chance_rich = builder.defineInRange("chance_rich", 0.1F, 0, 1);
		worldgen_rubber_tree_chance_biomes = builder.define("chance_biomes", new ArrayList<>(Arrays.asList("minecraft:forest", "minecraft:birch_forest", "minecraft:flower_forest", "minecraft:wooded_hills")));
		worldgen_rubber_tree_chance_biomes_rich = builder.define("chance_biomes_rich", new ArrayList<>(Arrays.asList("minecraft:swamp", "minecraft:swamp_hills")));
		builder.pop();

		return builder.build();
	}
}
