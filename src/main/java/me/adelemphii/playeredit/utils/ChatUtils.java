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
        player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + ChatColor.ITALIC + ChatColor.translateAlternateColorCodes('&', error));
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
        player.sendMessage(ChatColor.DARK_RED + "[!] " + ChatColor.RED + "Syntax Error: " + ChatColor.ITALIC + ChatColor.translateAlternateColorCodes('&', correctSyntax));
        player.playSound(player.getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1F, 1F);
    }
}
