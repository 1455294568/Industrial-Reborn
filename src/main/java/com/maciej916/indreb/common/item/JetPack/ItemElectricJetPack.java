package com.maciej916.indreb.common.item.JetPack;

import com.maciej916.indreb.common.energy.interfaces.IEnergy;
import com.maciej916.indreb.common.enums.EnergyTier;
import com.maciej916.indreb.common.enums.EnumEnergyType;
import com.maciej916.indreb.common.enums.ModArmorMaterials;
import com.maciej916.indreb.common.interfaces.item.IJetpack;
import com.maciej916.indreb.common.item.ItemElectricArmour;
import com.maciej916.indreb.common.registries.ModCapabilities;
import com.maciej916.indreb.common.util.CapabilityUtil;
import com.maciej916.indreb.common.util.LazyOptionalHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;

public class ItemElectricJetPack extends ItemElectricArmour implements IJetpack {

    public ItemElectricJetPack(EquipmentSlot pSlot, int energyStored, int maxEnergy, EnumEnergyType energyType, EnergyTier energyTier) {
        super(pSlot, energyStored, maxEnergy, energyType, energyTier, ArmorMaterials.IRON);
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
