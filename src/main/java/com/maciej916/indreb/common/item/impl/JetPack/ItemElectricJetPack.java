package com.maciej916.indreb.common.item.impl.JetPack;

import com.maciej916.indreb.common.enums.EnergyTier;
import com.maciej916.indreb.common.enums.EnergyType;
import com.maciej916.indreb.common.interfaces.item.IJetpack;
import com.maciej916.indreb.common.item.base.ElectricArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;

public class ItemElectricJetPack extends ElectricArmorItem implements IJetpack {

    public ItemElectricJetPack(EquipmentSlot pSlot) {
        super(ArmorMaterials.IRON, pSlot, new Properties(), 0, 30000, EnergyType.RECEIVE, EnergyTier.BASIC);
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return 0;
    }

    @Override
    public boolean drainEnergy(ItemStack paramItemStack, int paramInt) {
        return false;
    }
}
