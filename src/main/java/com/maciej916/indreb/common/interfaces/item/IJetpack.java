package com.maciej916.indreb.common.interfaces.item;

import net.minecraft.world.item.ItemStack;

public interface IJetpack extends IElectricItem {

    boolean drainEnergy(ItemStack paramItemStack, int paramInt);

}
