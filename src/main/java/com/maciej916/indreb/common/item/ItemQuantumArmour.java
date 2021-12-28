package com.maciej916.indreb.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.maciej916.indreb.common.energy.impl.CapEnergyStorage;
import com.maciej916.indreb.common.energy.interfaces.IEnergy;
import com.maciej916.indreb.common.enums.EnergyTier;
import com.maciej916.indreb.common.enums.EnumEnergyType;
import com.maciej916.indreb.common.enums.EnumLang;
import com.maciej916.indreb.common.enums.ModArmorMaterials;
import com.maciej916.indreb.common.interfaces.item.IElectricItem;
import com.maciej916.indreb.common.registries.ModCapabilities;
import com.maciej916.indreb.common.util.CapabilityUtil;
import com.maciej916.indreb.common.util.LazyOptionalHelper;
import com.maciej916.indreb.common.util.TextComponentUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class ItemQuantumArmour extends ItemElectricArmour {

    protected static final Map<MobEffect, Integer> potionRemovalCost = new IdentityHashMap<>();

    public ItemQuantumArmour(EquipmentSlot pSlot, int energyStored, int maxEnergy, EnumEnergyType energyType, EnergyTier energyTier) {
        super(pSlot, energyStored, maxEnergy, energyType, energyTier, ModArmorMaterials.NANO);

        potionRemovalCost.put(MobEffects.WITHER, 25000);
        potionRemovalCost.put(MobEffects.POISON, 25000);

    }

    private float getChargeRatio(ItemStack stack) {
        LazyOptionalHelper<IEnergy> cap = CapabilityUtil.getCapabilityHelper(stack, ModCapabilities.ENERGY);
        return cap.getIfPresentElse(e -> (float) e.energyStored() / e.maxEnergy(), 0f);
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round(13.0F - ((1 - getChargeRatio(pStack)) * 13.0F));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        //super.onArmorTick(stack, world, player);

        var slot = this.getSlot();
        int air;

        switch (slot) {
            case HEAD:
                air = player.getAirSupply();
                stack.getCapability(ModCapabilities.ENERGY).ifPresent(energy -> {

                    if (energy.energyStored() > 1000 && air < 100) {
                        player.setAirSupply(air + 200);
                        energy.consumeEnergy(1000, false);
                    }

                    for (MobEffectInstance effect : new LinkedList<>(player.getActiveEffects())) {
                        var eff = effect.getEffect();
                        var cost = potionRemovalCost.get(eff);
                        if (cost != null) {
                            if (this.energyStored >= cost) {
                                energy.consumeEnergy(cost, false);
                                player.removeEffect(eff);
                            }
                        }
                    }
                });
                break;
            case CHEST:
                player.clearFire();
                break;
            case LEGS:
                break;
            case FEET:

                break;
        }
    }

    @Override
    public ArmorProperties getProperties(LivingEntity player, @NotNull ItemStack armor, DamageSource source, float damage, int slot) {
        int energyPerDamage = getEnergyPerDamage();
        int damageLimit = Integer.MAX_VALUE;
        if (energyPerDamage > 0) damageLimit = (int)Math.min(damageLimit, 25.0D * this.getEnergy(armor).energyStored() / energyPerDamage);

        /*
        if (source == DamageSource.field_76379_h) {

            if (this.field_77881_a == EntityEquipmentSlot.FEET)
                return new ISpecialArmor.ArmorProperties(10, 1.0D, damageLimit);
            if (this.field_77881_a == EntityEquipmentSlot.LEGS) {
                return new ISpecialArmor.ArmorProperties(9, 0.8D, damageLimit);
            }
        }
        */
        double absorptionRatio = getBaseAbsorptionRatio() * getDamageAbsorptionRatio();
        return new ISpecialArmor.ArmorProperties(8, absorptionRatio, damageLimit);
    }

    @Override
    public double getDamageAbsorptionRatio() {
        if (this.getSlot() == EquipmentSlot.CHEST) return 1.2D;
        return 1.0D;
    }

    @Override
    public int getEnergyPerDamage() {
        return 20000;
    }
}
