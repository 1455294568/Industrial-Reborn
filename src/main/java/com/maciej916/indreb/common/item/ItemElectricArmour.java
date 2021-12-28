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

public abstract class ItemElectricArmour extends IndRebArmour implements IElectricItem, ISpecialArmor {

    protected static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};

    protected final int energyStored;
    protected final int maxEnergy;
    protected final EnumEnergyType energyType;
    protected final EnergyTier energyTier;

    public ItemElectricArmour(EquipmentSlot pSlot, int energyStored, int maxEnergy, EnumEnergyType energyType, EnergyTier energyTier, ModArmorMaterials material) {
        super(material, pSlot);
        this.energyStored = energyStored;
        this.maxEnergy = maxEnergy;
        this.energyType = energyType;
        this.energyTier = energyTier;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapEnergyStorage(energyStored, maxEnergy, energyTier.getBasicTransfer(), energyTier.getBasicTransfer(), energyType);
    }

    public EnumEnergyType getEnergyType() {
        return energyType;
    }

    @Override
    public IEnergy getEnergy(ItemStack stack) {
        return CapabilityUtil.getCapabilityHelper(stack, ModCapabilities.ENERGY).getValue();
    }

    @Override
    public EnergyTier getEnergyTier() {
        return energyTier;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        if (allowdedIn(tab)) {
            list.add(new ItemStack(this));

            ItemStack full = new ItemStack(this);
            LazyOptionalHelper<IEnergy> cap = CapabilityUtil.getCapabilityHelper(full, ModCapabilities.ENERGY);
            cap.getIfPresent(e -> e.setEnergy(e.maxEnergy()));

            list.add(full);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.add(TextComponentUtil.build(
                new TranslatableComponent(EnumLang.POWER_TIER.getTranslationKey()).withStyle(ChatFormatting.GRAY),
                new TranslatableComponent(energyTier.getLang().getTranslationKey()).withStyle(energyTier.getColor())
        ));

        int energyStored = CapabilityUtil.getCapabilityHelper(pStack, ModCapabilities.ENERGY).getIfPresentElse(IEnergy::energyStored, 0);
        pTooltipComponents.add(TextComponentUtil.build(
                new TranslatableComponent(EnumLang.STORED.getTranslationKey()).withStyle(ChatFormatting.GRAY),
                new TranslatableComponent(EnumLang.POWER.getTranslationKey(), TextComponentUtil.getFormattedEnergyUnit(energyStored)).withStyle(energyTier.getColor()),
                new TextComponent(" / ").withStyle(ChatFormatting.GRAY),
                new TranslatableComponent(EnumLang.POWER.getTranslationKey(), TextComponentUtil.getFormattedEnergyUnit(maxEnergy)).withStyle(energyTier.getColor())
        ));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }


    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return true;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (stack.getItem().equals(this)) {
            if (slot == getSlot()) {
                CompoundTag tag = stack.getTag();
                boolean active = false;

                if (tag != null && tag.getAllKeys().contains("active")) {
                    active = tag.getBoolean("active");
                } else {
                    IEnergy energy = getEnergy(stack);
                    if (energy != null) {
                        active = energy.energyStored() > 0;
                    }
                }

                Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
                modifiers.putAll(super.getAttributeModifiers(slot, stack));

                modifiers.removeAll(Attributes.ARMOR);
                UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[slot.getIndex()];

                if (active) {
                    modifiers.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", getDefense(), AttributeModifier.Operation.ADDITION));
                } else {
                    modifiers.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", 0, AttributeModifier.Operation.ADDITION));
                }

                return modifiers;
            }
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        if (!level.isClientSide()) {
            if (level.getGameTime() % 20 == 0) {
                IEnergy energy = getEnergy(stack);
                if (energy != null) {
                    CompoundTag tag = stack.getOrCreateTag();
                    tag.putBoolean("active", energy.energyStored() > 0);
                }
            }
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        int energyStored = CapabilityUtil.getCapabilityHelper(stack, ModCapabilities.ENERGY).getIfPresentElse(IEnergy::energyStored, 0);
        nbt.putInt("energyStored", energyStored);
        return nbt;
    }

    @Override
    public void readShareTag(ItemStack stack, @org.jetbrains.annotations.Nullable CompoundTag nbt) {
        if (nbt != null) {
            CapabilityUtil.getCapabilityHelper(stack, ModCapabilities.ENERGY).ifPresent(iEnergy -> {
                iEnergy.setEnergy(nbt.getInt("energyStored"));
            });
        }
        super.readShareTag(stack, nbt);
    }

    @Override
    public ISpecialArmor.ArmorProperties getProperties(LivingEntity player, ItemStack armor, DamageSource source, float damage, int slot) {
        double absorptionRatio = getBaseAbsorptionRatio() * getDamageAbsorptionRatio();
        int energyPerDamage = getEnergyPerDamage();

        int damageLimit = Integer.MAX_VALUE;
        if (energyPerDamage > 0)
            damageLimit = (int) (int)Math.min(damageLimit, 25.0D * this.getEnergy(armor).energyStored() / energyPerDamage);

        return new ISpecialArmor.ArmorProperties(0, absorptionRatio, damageLimit);
    }

    public int getEnergyPerDamage() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void damageArmor(LivingEntity entity, @NotNull ItemStack stack, DamageSource source, int damage, int slot) {
        this.getEnergy(stack).consumeEnergy(getEnergyPerDamage() * damage, false);
    }

    public abstract double getDamageAbsorptionRatio();

    protected final double getBaseAbsorptionRatio() {
        switch (this.getSlot()) {
            case HEAD:
                return 0.15D;
            case CHEST:
                return 0.4D;
            case LEGS:
                return 0.3D;
            case FEET:
                return 0.15D;
        }
        return 0.0D;
    }
}
