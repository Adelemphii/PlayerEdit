package me.adelemphii.playeredit.utils;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Utility {

    /**
     * Returns true if arg can be parsed as int, false if not
     */
    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    /**
     * Returns if this ChatColor is a color
     */
    static boolean isAColor(ChatColor color) {
        return !(color == ChatColor.BOLD || color == ChatColor.ITALIC ||
                color == ChatColor.MAGIC || color == ChatColor.RESET ||
                color == ChatColor.STRIKETHROUGH || color == ChatColor.UNDERLINE);
    }

}


