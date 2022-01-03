package com.maciej916.indreb.common.item;

import com.maciej916.indreb.common.energy.interfaces.IEnergy;
import com.maciej916.indreb.common.enums.EnergyTier;
import com.maciej916.indreb.common.enums.EnumEnergyType;
import com.maciej916.indreb.common.enums.ModArmorMaterials;
import com.maciej916.indreb.common.interfaces.item.IElectricItem;
import com.maciej916.indreb.common.interfaces.item.IJetpack;
import com.maciej916.indreb.common.interfaces.item.ISpecialArmor;
import com.maciej916.indreb.common.registries.ModCapabilities;
import com.maciej916.indreb.common.util.CapabilityUtil;
import com.maciej916.indreb.common.util.Keyboard;
import com.maciej916.indreb.common.util.LazyOptionalHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ItemQuantumArmour extends ItemElectricArmour implements IJetpack {

    protected static final Map<MobEffect, Integer> potionRemovalCost = new IdentityHashMap<>();

    public ItemQuantumArmour(EquipmentSlot pSlot, int energyStored, int maxEnergy, EnumEnergyType energyType, EnergyTier energyTier) {
        super(pSlot, energyStored, maxEnergy, energyType, energyTier, ModArmorMaterials.QUANTUM);

        potionRemovalCost.put(MobEffects.WITHER, 25000);
        potionRemovalCost.put(MobEffects.POISON, 25000);

        if (pSlot == EquipmentSlot.FEET) {
            MinecraftForge.EVENT_BUS.register(this);
        }

    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityLivingFallEvent(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            var stack = player.getItemBySlot(EquipmentSlot.FEET);
            if (stack != null && stack.getItem() == this) {
                stack.getCapability(ModCapabilities.ENERGY).ifPresent(energy -> {
                    int fallDamage = Math.max((int) event.getDistance() - 10, 0);
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
        var slot = this.getSlot();
        CompoundTag nbtData = stack.getOrCreateTag();

        switch (slot) {
            case HEAD:
                int air = player.getAirSupply();

                stack.getCapability(ModCapabilities.ENERGY).ifPresent(energy -> {

                    if (energy.energyStored() > 1000 && air < 100) {
                        player.setAirSupply(air + 200);
                        energy.consumeEnergy(1000, false);
                    }

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
                        int skylight;
                        if (Minecraft.getInstance().level.isNight()) {
                            skylight = Minecraft.getInstance().level.getBrightness(LightLayer.BLOCK, blockpos);
                        } else {
                            skylight = Minecraft.getInstance().level.getLightEngine().getRawBrightness(blockpos, 0);
                        }
                        if (skylight > 8) {
                            player.removeEffect(MobEffects.NIGHT_VISION);
                            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, true, true));
                        } else {
                            player.removeEffect(MobEffects.BLINDNESS);
                            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, true, true));
                        }
                    }

                    for (MobEffectInstance effect : new LinkedList<>(player.getActiveEffects())) {
                        var eff = effect.getEffect();
                        var cost = potionRemovalCost.get(eff);
                        if (cost != null) {
                            if (energy.energyStored() >= cost) {
                                energy.consumeEnergy(cost.intValue(), false);
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
                stack.getCapability(ModCapabilities.ENERGY).ifPresent(energy -> {
                    if (energy.energyStored() > 1000 && (player.isOnGround() || player.isInWater())
                       && Keyboard.getInstance().isForwardKeyDown(player) && player.isSprinting()) {

                        byte speedTicker = nbtData.getByte("speedTicker");
                              speedTicker = (byte)(speedTicker + 1);

                              if (speedTicker >= 10) {
                                  speedTicker = 0;
                                  energy.consumeEnergy(1000, false);
                              }

                              nbtData.putByte("speedTicker", speedTicker);

                        float speed = 0.22F;

                        if (player.isInWater())  {
                            speed = 0.1F;
                            if (Keyboard.getInstance().isJumpKeyDown(player)) {
                                Vec3 delMov = player.getDeltaMovement();
                                player.setDeltaMovement(new Vec3(delMov.x, delMov.y + 0.10000000149011612, delMov.z));
                            }
                        }

                        if (speed > 0.0F) {
                            player.moveRelative(speed, new Vec3(0.0F, 0.0F, 1.0F));
                        }
                    }
                });
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

        if (source == DamageSource.FALL) {

            if (this.getSlot() == EquipmentSlot.FEET)
                return new ISpecialArmor.ArmorProperties(10, 1.0D, damageLimit);
            if (this.getSlot() == EquipmentSlot.LEGS) {
                return new ISpecialArmor.ArmorProperties(9, 0.8D, damageLimit);
            }
        }

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

    @Override
    public boolean drainEnergy(ItemStack stack, int amount) {
        IEnergy energy = CapabilityUtil.getCapabilityHelper(stack, ModCapabilities.ENERGY).getValue();
        if (energy != null) {
            int cost = amount + 6;
            if (energy.energyStored() > amount) {
                energy.consumeEnergy(cost, false);
                return true;
            }
        }
        return false;
    }
}
