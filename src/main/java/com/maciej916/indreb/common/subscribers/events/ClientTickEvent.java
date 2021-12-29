package com.maciej916.indreb.common.subscribers.events;

import com.maciej916.indreb.IndReb;
import com.maciej916.indreb.common.util.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IndReb.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientTickEvent {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            var player = Minecraft.getInstance().player;
            if (Minecraft.getInstance().options.keyJump.isDown()) {
                Keyboard.getInstance().processKeyUpdaye(player, Keyboard.jump);
            } else if (Minecraft.getInstance().options.keyUp.isDown()) {
                Keyboard.getInstance().processKeyUpdaye(player, Keyboard.forward);
            }
        }
    }
}
