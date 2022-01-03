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
import com.maciej916.indreb.common.interfaces.item.ISpecialArmor;
import com.maciej916.indreb.common.registries.ModCapabilities;
import com.maciej916.indreb.common.util.CapabilityUtil;
import com.maciej916.indreb.common.util.Keyboard;
import com.maciej916.indreb.common.util.LazyOptionalHelper;
import com.maciej916.indreb.common.util.TextComponentUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemNanoArmour extends ItemElectricArmour{

    public ItemNanoArmour(EquipmentSlot pSlot, int energyStored, int maxEnergy, EnumEnergyType energyType, EnergyTier energyTier) {
        super(pSlot, energyStored, maxEnergy, energyType, energyTier, ModArmorMaterials.NANO);

        if (pSlot == EquipmentSlot.FEET) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

//    @Override
//    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
//        return false;
//    }
//
//    @Override
//    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
//        return false;
//    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityLivingFallEvent(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            var stack = player.getItemBySlot(EquipmentSlot.FEET);
            if (stack != null && stack.getItem() == this) {
                stack.getCapability(ModCapabilities.ENERGY).ifPresent(energy -> {
                    int fallDamage = (int)event.getDistance() - 3;
                    if (fallDamage >= 8)
                        return;
                    int energyCost = (getEnergyPerDamage() * fallDamage);
                    if (energy.energyStored() >= energyCost) {
                        energy.consumeEnergy(energyCost, false);
                        event.setCanceled(true);
                    }
                });
            }
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        CompoundTag nbtData = stack.getOrCreateTag();

        if (this.getSlot() == EquipmentSlot.HEAD) {
            stack.getCapability(ModCapabilities.ENERGY).ifPresent(energy -> {

                boolean nightvision = nbtData.getBoolean("Nightvision");
                byte toggleTimer = nbtData.getByte("toggleTimer");

                if (Keyboard.getInstance().isAltKeyDown(player)
                        && Keyboard.getInstance().isModeSwitchKeyDown(player)
                        && toggleTimer == 0) {
                    toggleTimer = 10;
                    nightvision = !nightvision;
                    nbtData.putBoolean("Nightvision", nightvision);
                }

                if (toggleTimer > 0) {
                    toggleTimer = (byte) (toggleTimer - 1);
                    nbtData.putByte("toggleTimer", toggleTimer);
                }

                if (nightvision && energy.energyStored() > 1) {
                    energy.consumeEnergy(1, false);
                    BlockPos blockpos = Minecraft.getInstance().getCameraEntity().blockPosition();
                    int skylight = Minecraft.getInstance().level.getLightEngine().getRawBrightness(blockpos, 0);//.getBrightness(LightLayer.BLOCK, blockpos);
                    if (skylight > 8) {
                        player.removeEffect(MobEffects.NIGHT_VISION);
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, true, true));
                    } else {
                        player.removeEffect(MobEffects.BLINDNESS);
                        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, true, true));
                    }
                }
            });
        }
    }

    @Override
    public ArmorProperties getProperties(LivingEntity player, @NotNull ItemStack armor, DamageSource source, float damage, int slot) {
        if (source == DamageSource.FALL) {
            int energyPerDamage = getEnergyPerDamage();
            int damageLimit = Integer.MAX_VALUE;
            if (energyPerDamage > 0)
                damageLimit = (int) Math.min(damageLimit, 25.0D * this.getEnergy(armor).energyStored() / energyPerDamage);

            return new ISpecialArmor.ArmorProperties(10, (damage < 8.0D) ? 1.0D : 0.875D, damageLimit);
        }
        return super.getProperties(player, armor, source, damage, slot);
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return 0.9D;
    }

    @Override
    public int getEnergyPerDamage() {
        return 5000;
    }
}
