package com.maciej916.indreb.common.util;

import net.minecraft.world.entity.player.Player;

import java.util.*;

public class Keyboard {

    public static final int alt = 1;
    public static final int boost = 2;
    public static final int forward = 4;
    public static final int modeSwitch = 8;
    public static final int jump = 16;
    public static final int sideInventory = 32;
    public static final int hubMode = 64;

    private final Map<Player, Set<Integer>> playerKeys = new WeakHashMap<>();
    private final static Keyboard instance = new Keyboard();

    public static Keyboard getInstance() {
        return instance;
    }

    public void processKeyUpdate(Player player, int key) {
        Set<Integer> keys = new HashSet<>();

        if ((key & alt) == alt) {
            keys.add(alt);
        }
        if ((key & boost) == boost) {
            keys.add(boost);
        }
        if ((key & forward) == forward) {
            keys.add(forward);
        }
        if ((key & modeSwitch) == modeSwitch) {
            keys.add(modeSwitch);
        }
        if ((key & jump) == jump) {
            keys.add(jump);
        }
        if ((key & sideInventory) == sideInventory) {
            keys.add(sideInventory);
        }
        if ((key & hubMode) == hubMode) {
            keys.add(hubMode);
        }

        playerKeys.put(player, keys);
    }

    public boolean isAltKeyDown(Player player) {
        return getKey(player, alt);
    }

    public boolean isBoostKeyDown(Player player) {
        return getKey(player, boost);
    }

    public boolean isForwardKeyDown(Player player) {
        return getKey(player, forward);
    }

    public boolean isJumpKeyDown(Player player) {
        return getKey(player, jump);
    }

    public boolean isModeSwitchKeyDown(Player player){
        return getKey(player, modeSwitch);
    }

    public boolean isSideinventoryKeyDown(Player player) {
        return getKey(player, sideInventory);
    }

    public boolean isHudModeKeyDown(Player player) {
        return getKey(player, hubMode);
    }

    public boolean isSneakKeyDown(Player player) {
        return player.isShiftKeyDown();
    }

    private boolean getKey(Player player, int key) {
        var keys = this.playerKeys.get(player);
        if (keys != null) {
            return keys.contains(key);
        }
        return false;
    }
}
