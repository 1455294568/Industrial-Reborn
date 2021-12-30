package com.maciej916.indreb.common.item;

import com.maciej916.indreb.common.interfaces.item.IJetpack;
import com.maciej916.indreb.common.util.Keyboard;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class JetpackLogic {
    public static void onArmorTick(Level world, Player player, ItemStack stack, IJetpack jetpack) {
        if (stack == null) return;

        CompoundTag nbtData = stack.getOrCreateTag();
        boolean hoverMode = getHoverMode(nbtData);
        byte toggleTimer = nbtData.getByte("toggleTimer");
        boolean jetpackUsed = false;

        if (Keyboard.getInstance().isJumpKeyDown(player) && Keyboard.getInstance().isModeSwitchKeyDown(player) && toggleTimer == 0) {
            toggleTimer = 10;
            hoverMode = !hoverMode;
            setHoverMode(nbtData, !hoverMode);

            if (hoverMode) {
                // on log
            } else {
                // off log
            }
        }

        if (Keyboard.getInstance().isJumpKeyDown(player) || hoverMode) {
            jetpackUsed = useJetpack(player, hoverMode, jetpack, stack);

            if (player.isOnGround() && hoverMode) {
                setHoverMode(nbtData, false);
                // off log
            }
        }

        if (toggleTimer > 0) {
            toggleTimer = (byte)(toggleTimer - 1);
            nbtData.putByte("toggleTimer", toggleTimer);
        }
    }

    public static boolean useJetpack(Player player, boolean hoverMode, IJetpack jetpack, ItemStack stack) {
        return false;
    }

    private static void setHoverMode(CompoundTag nbt, boolean value) {
        nbt.putBoolean("hoverMode", value);
    }

    private static boolean getHoverMode(CompoundTag nbt) {
        return nbt.getBoolean("hoverMode");
    }
}
