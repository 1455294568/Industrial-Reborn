package com.maciej916.indreb.common.item;

import com.maciej916.indreb.common.energy.interfaces.IEnergy;
import com.maciej916.indreb.common.enums.EnergyTier;
import com.maciej916.indreb.common.enums.EnumEnergyType;
import com.maciej916.indreb.common.enums.ModArmorMaterials;
import com.maciej916.indreb.common.interfaces.item.IJetpack;
import com.maciej916.indreb.common.interfaces.item.ISpecialArmor;
import com.maciej916.indreb.common.registries.ModCapabilities;
import com.maciej916.indreb.common.util.CapabilityUtil;
import com.maciej916.indreb.common.util.Keyboard;
import com.maciej916.indreb.common.util.LazyOptionalHelper;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ItemQuantumArmour extends ItemElectricArmour implements IJetpack {

    protected static final Map<MobEffect, Integer> potionRemovalCost = new IdentityHashMap<>();

    public ItemQuantumArmour(EquipmentSlot pSlot, int energyStored, int maxEnergy, EnumEnergyType energyType, EnergyTier energyTier) {
        super(pSlot, energyStored, maxEnergy, energyType, energyTier, ModArmorMaterials.QUANTUM);

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
        CompoundTag nbtData = stack.getOrCreateTag();

        switch (slot) {
            case HEAD:
                int air = player.getAirSupply();

                stack.getCapability(ModCapabilities.ENERGY).ifPresent(energy -> {

                    if (energy.energyStored() > 1000 && air < 100) {
                        player.setAirSupply(air + 200);
                        energy.consumeEnergy(1000, false);
                    }

                    byte toggleTimer = nbtData.getByte("toggleTimer");
                    short hubmode = nbtData.getShort("HudMode");
                    boolean nightvision = nbtData.getBoolean("Nightvision");

                    if (Keyboard.getInstance().isAltKeyDown(player) && Keyboard.getInstance().isModeSwitchKeyDown(player) && toggleTimer == 0) {
                        nightvision = !nightvision;
                        toggleTimer = 10;
                        nbtData.putBoolean("Nightvision", nightvision);
                    }

                    if (nightvision && energy.energyStored() > 1) {
                        energy.consumeEnergy(1, false);
                        BlockPos pos = new BlockPos((int)Math.floor(player.position().x), (int)Math.floor(player.position().y), (int)Math.floor(player.position().z));
                        int skylight = player.getLevel().getLightEmission(pos);
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
}
