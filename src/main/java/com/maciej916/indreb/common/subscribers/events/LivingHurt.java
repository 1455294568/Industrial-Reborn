package com.maciej916.indreb.common.subscribers.events;

import com.maciej916.indreb.IndReb;
import com.maciej916.indreb.common.energy.interfaces.IEnergy;
import com.maciej916.indreb.common.interfaces.item.IElectricItem;
import com.maciej916.indreb.common.item.ISpecialArmor;
import com.maciej916.indreb.common.item.ItemElectricArmour;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IndReb.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingHurt {

    @SubscribeEvent
    public static void event(LivingHurtEvent event) {
        float amount = event.getAmount();
        if (!event.getSource().isBypassInvul() && event.getEntityLiving() instanceof Player player) {
            amount = ISpecialArmor.ArmorProperties.applyArmor(player, player.getInventory().armor, event.getSource(), amount);
            float f = amount;
            amount = Math.max(amount - player.getAbsorptionAmount(), 0.0F);
            player.setAbsorptionAmount(player.getAbsorptionAmount() - (f - amount));
            event.setAmount(amount);
        }
    }
}
