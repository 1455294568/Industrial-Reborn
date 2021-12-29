package com.maciej916.indreb.common.registries;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static KeyMapping BOOST_KEY = new KeyMapping("key.boost", GLFW.GLFW_KEY_LEFT_CONTROL, "key.categories.indreb");

    public static KeyMapping ALT_KEY = new KeyMapping("key.alt", GLFW.GLFW_KEY_LEFT_ALT, "key.categories.indreb");
}
