package com.maciej916.indreb.common.item;

import com.maciej916.indreb.common.registries.ModItemGroups;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class IndRebArmour extends ArmorItem {

    public IndRebArmour(ArmorMaterial pMaterial, EquipmentSlot pSlot) {
        super(pMaterial, pSlot, new Properties().tab(ModItemGroups.MAIN_ITEM_GROUP));

    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {

        if (pCategory == CreativeModeTab.TAB_COMBAT) pItems.add(new ItemStack(this));

        super.fillItemCategory(pCategory, pItems);
    }

}
