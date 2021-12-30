package com.maciej916.indreb.common.util;

import net.minecraft.world.entity.player.Player;

import java.util.*;

public class Keyboard {

    public static final int alt = 1;
    public static final int boost = 1 >> 1;
    public static final int forward = 1 >> 2;
    public static final int modeSwitch = 1 >> 3;
    public static final int jump = 1 >> 4;
    public static final int sideInventory = 1 >> 5;
    public static final int hubMode = 1 >> 6;

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
            keys.add(boost);
        }
        if ((key & modeSwitch) == modeSwitch) {
            keys.add(boost);
        }
        if ((key & jump) == jump) {
            keys.add(boost);
        }
        if ((key & sideInventory) == sideInventory) {
            keys.add(boost);
        }
        if ((key & hubMode) == hubMode) {
            keys.add(boost);
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
