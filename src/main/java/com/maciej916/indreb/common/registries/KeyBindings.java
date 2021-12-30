package com.maciej916.indreb.common.registries;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static KeyMapping BOOST_KEY = new KeyMapping("keybingding.indreb.boost_key", GLFW.GLFW_KEY_LEFT_CONTROL, "keybingding.indreb.category");

    public static KeyMapping ALT_KEY = new KeyMapping("keybingding.indreb.mode_switch_key", GLFW.GLFW_KEY_LEFT_ALT, "keybingding.indreb.category");
}
