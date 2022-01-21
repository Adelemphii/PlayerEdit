package me.adelemphii.playeredit.commands;

import me.adelemphii.playeredit.PlayerEdit;
import me.adelemphii.playeredit.utils.ChatUtils;
import me.adelemphii.playeredit.utils.PlayerEditUtils;
import me.adelemphii.playeredit.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerEditCommand implements CommandExecutor {

    private static final String PE_PREFIX = ChatColor.BLUE + "PlayerEdit" + ChatColor.DARK_GRAY + " »" + ChatColor.GRAY;
    private static final String PE_NOPERMS = ChatColor.RED + " You do not have the required permissions";

    PlayerEdit plugin;
    public PlayerEditCommand(PlayerEdit plugin) {
        this.plugin = plugin;
    }

    PlayerEditUtils mechanic = new PlayerEditUtils(PlayerEdit.getInstance());

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if(!player.hasPermission("playeredit.basic")) {
            ChatUtils.errorMessage(player, "You do not have permission to do that!");
            return false;
        }

        if (args.length == 0) sendHelpMessage(player);
        else if (args[0].equalsIgnoreCase("help")) sendHelpMessage(player);
        else if (args[0].equalsIgnoreCase("clear")) mechanic.clearItem(player);
        else if (args[0].equalsIgnoreCase("sign")) mechanic.signItem(player);
        else if (args[0].equalsIgnoreCase("unsign")) mechanic.unsignItem(player);
        else if (args[0].equalsIgnoreCase("name")) mechanic.nameItem(player, args);
        else if (args[0].equalsIgnoreCase("desc")) mechanic.addDescriptionToItem(player, args);
        else if (args[0].equalsIgnoreCase("forceunsign") && player.hasPermission("playeredit.admin"))
            mechanic.forceUnsignItem(player);
        else if (args.length == 2 && args[0].equalsIgnoreCase("copy") && player.hasPermission("playeredit.admin"))
            mechanic.copyAmount(args[1], player);
        else if (!player.hasPermission("playeredit.admin"))
            ChatUtils.errorMessage(player, "You do not have permission to do that!");
        else ChatUtils.syntaxError(player, "Usage: /edit");

        return false;
    }

    private void sendHelpMessage(Player player) {
        runAsync(() -> {
            player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
            player.sendMessage("");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"         &9&lPlayer Edit&r &7"));
            player.sendMessage("");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"   &a&l+ &b/edit name -&f Rename an item"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"   &a&l+ &b/edit desc -&f Add description to your item"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"   &a&l+ &b/edit clear -&f Clears the description of an item"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"   &a&l+ &b/edit sign -&f Signs an item"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"   &a&l+ &b/edit unsign -&f Unsigns an item"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"   &a&l+ &b/edit copy <num> -&f Copies an item X amount of times"));
            player.sendMessage("");

        });
    }

    private void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

}
