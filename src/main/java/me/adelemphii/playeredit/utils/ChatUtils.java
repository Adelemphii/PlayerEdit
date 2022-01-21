package me.adelemphii.playeredit.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ChatUtils {

    /**
     * formats error message to be sent to a user
     *
     * @param player player to send error message to
     * @param error error message to send to user
     *
     */
    public static void errorMessage(Player player, String error) {
        player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED.toString() + ChatColor.ITALIC + ChatColor.translateAlternateColorCodes('&', error));
        player.playSound(player.getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1F, 1F);
    }

    /**
     * formats correct syntax for user to use
     *
     * @param player user to send syntax error to
     * @param correctSyntax the right syntax to use
     *
     */
    public static void syntaxError(Player player, String correctSyntax) {
        player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED.toString() + "Syntax Error: " + ChatColor.ITALIC + ChatColor.translateAlternateColorCodes('&', correctSyntax));
        player.playSound(player.getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1F, 1F);
    }

    /**
     * Returns GREEN (true) or RED (false)
     */
    public static net.md_5.bungee.api.ChatColor boolToChatColor(boolean b) {

        return b ? net.md_5.bungee.api.ChatColor.GREEN : net.md_5.bungee.api.ChatColor.RED;

    }

    /**
     * Fixes color on base components, so the color
     * does not go back to white once the text goes
     * on to the second line.
     * @param text text
     *
     * @return fixed base component
     */
    public static BaseComponent[] fixColor(String text) {
        return TextComponent.fromLegacyText(org.bukkit.ChatColor.translateAlternateColorCodes('&', text));
    }

}
