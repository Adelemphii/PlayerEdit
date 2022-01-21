package me.adelemphii.playeredit.utils;

import me.adelemphii.playeredit.PlayerEdit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerEditUtils implements Listener {

    public final PlayerEdit engine;

    public PlayerEditUtils(PlayerEdit engine) {
        this.engine = engine;
    }

    private static final int PE_MAXLINES = 20;
    private static final int PE_MAXWIDTH = 40;
    private static final String PE_PREFIX = ChatColor.BLUE + "PlayerEdit" + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY;

    private static final String PE_SETDESC = ChatColor.GREEN + " You have updated your items description";
    private static final String PE_SETNAME = ChatColor.GREEN + " You have updated your items name";
    private static final String PE_CLEAR = ChatColor.GREEN + " You have cleared your items description";
    private static final String PE_UNSIGNED = ChatColor.GREEN + " You have unsigned your item";
    private static final String PE_SIGNED = ChatColor.GREEN + " You have signed your item";


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBuild(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemInHand();


        if (PDCEditor.hasKey("signed", itemStack)) {
            ChatUtils.errorMessage(player, "You cannot place signed items");
            event.setCancelled(true);
        }

    }

    public static void applyDesc(String[] desc, ItemStack item, Player player) {
        ItemMeta meta = item.getItemMeta();
        List<String> newDesc;
        if (meta != null) {
            newDesc = meta.getLore();
            if (newDesc == null)
                newDesc = new ArrayList<>();

            StringBuilder lastString = new StringBuilder();
            if (newDesc.size() > 0) {
                lastString = new StringBuilder(ChatColor.stripColor(newDesc.get(newDesc.size() - 1)));
                newDesc.remove(newDesc.size() - 1);
            }

            for (String word : desc) {
                int currentLength = 0;
                if (lastString.toString().length() > 0) {
                    currentLength = lastString.toString().length() + 1;
                }

                String[] result = processWord(currentLength, ChatColor.stripColor(word));
                while (result.length > 1) {
                    lastString.append(result[0]);
                    newDesc.add(ChatColor.GRAY + lastString.toString());

                    lastString = new StringBuilder();
                    result = processWord(0, result[1]);
                }
                lastString.append(result[0]);
            }
            newDesc.add(ChatColor.GRAY + lastString.toString());

            if (newDesc.size() > PE_MAXLINES) {
                ChatUtils.errorMessage(player, "There is a maximum line count of &4&o20 &c&oadding this description will make your item go over 20 lines, to &4&o" + newDesc.size());
                return;
            }
            meta.setLore(newDesc);
            item.setItemMeta(meta);
        }
    }


    public static String[] processWord(int currentLength, String word) {
        String first = word;
        String second = null;

        if (first.length() > PE_MAXWIDTH) {
            int size = PE_MAXWIDTH - currentLength - 1;

            if (size > 0) {
                StringBuilder hyphenated = new StringBuilder();
                StringBuilder leftovers = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    hyphenated.append(first.toCharArray()[i]);
                }
                for (int i = size; i < first.length(); i++) {
                    leftovers.append(first.toCharArray()[i]);
                }
                hyphenated.append("-");

                first = hyphenated.toString();
                second = leftovers.toString();
            } else {
                second = first;
                first = "";
            }
        } else if (currentLength + first.length() > PE_MAXWIDTH) {
            second = first;
            first = "";
        }

        if (currentLength > 0 && first.length() > 0) {
            first = " " + first;
        }

        if (second == null) {
            return new String[]{first};
        } else {
            return new String[]{first, second};
        }
    }

    public void clearItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!(prechecks(item))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThere is an issue with your item!"));
            return;
        }

        if (isSigned(item)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThis item is signed, so may not be edited!"));
            return;
        }

        removeLore(item);
        player.sendMessage(PE_PREFIX + PE_CLEAR);
    }

    public void signItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!(prechecks(item))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThere is an issue with your item!"));
            return;
        }

        if (isSigned(item)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThis item is signed, so may not be edited!"));
            return;
        }

        setSigned(player, item, true);
        player.sendMessage(PE_PREFIX + PE_SIGNED);
    }

    public void forceUnsignItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!(prechecks(item))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThere is an issue with your item!"));
            return;
        }

        if (!isSigned(item)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThis item is not signed"));
            return;
        }

        unSign(player.getPlayer(), item, true);
        player.sendMessage(PE_PREFIX + PE_UNSIGNED);
    }

    public void unsignItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!(prechecks(item))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThere is an issue with your item!"));
            return;
        }

        if (!isSigned(item)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThis item is not signed"));
            return;
        }

        if (isSigned(item) && (!(isSignatory(player, item)))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThis item is signed, so may not be edited!"));
            return;
        }

        unSign(player, item, true);
        player.sendMessage(PE_PREFIX + PE_UNSIGNED);

    }

    public void nameItem(Player player, String[] args) {

        if (args.length <= 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cYou need to include a name!"));
            return;
        }

        if (args.length >= 45) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + " &cYour item name cannot be longer than 45 characters!"));
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (!(prechecks(item))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThere is an issue with your item!"));
            return;
        }

        if (isSigned(item)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThis item is signed, so may not be edited!"));
            return;
        }

        String name = StringUtils.combineArgs(args, 1);
        setName(name, item);
        player.sendMessage(PE_PREFIX + PE_SETNAME);

    }

    public void addDescriptionToItem(Player player, String[] args) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!(prechecks(item))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThere is an issue with your item!"));
            return;
        }

        if (isSigned(item)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',PE_PREFIX + "&cThis item is signed, so may not be edited!"));
            return;
        }

        String firstInput = StringUtils.combineArgs(args, 1);
        String[] desc = firstInput.split(" ");
        PlayerEditUtils.applyDesc(desc, item, player);
        player.sendMessage(PE_PREFIX + PE_SETDESC);
    }

    /**
     * ==========================
     * Misc Functions
     * ==========================
     */

    //Makes sure item is fine to use, not air
    public boolean prechecks(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }

    //Sets the display name of an item
    public void setName(String input, ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_AQUA + input);
        item.setItemMeta(meta);
    }

    //Sets signature
    public void setSignature(Player user, ItemStack item) {
        String name = user.getDisplayName();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "{Player Signed: " + ChatColor.GRAY + name + ChatColor.DARK_GRAY + "}");
        lore.add(ChatColor.DARK_GRAY + "      (" + ChatColor.GRAY + user.getPlayer().getName() + ChatColor.DARK_GRAY + ")");

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    //Checks if an item is signed or not, so it
    //knows whether or not the item can be edited
    public boolean isSigned(ItemStack item) {
        return PDCEditor.hasKey("signed", item);
    }


    //Checks if user is a signatory, so it knows whether
    //or not the signature can be removed
    public boolean isSignatory(Player player, ItemStack item) {
        String s = PDCEditor.getString("signed", item);
        return s.equals(player.getUniqueId().toString());
    }

    //Sets signature of item, as well as UUID of
    //player who signed it
    public ItemStack setSigned(Player user, ItemStack item, boolean command) {
        PDCEditor.setString(user.getUniqueId().toString(), "signed", item);
        setSignature(user, item);

        if (command){
            int slotNumber = user.getInventory().getHeldItemSlot();
            user.getInventory().setItem(slotNumber, item);
        } else {
            return item;
        }
        return null;
    }

    //Removes signature of an item of its NBT
    public ItemStack unSign(Player player, ItemStack item, boolean command) {
        PDCEditor.removeString("signed", item);
        if (command) player.getInventory().remove(item);
        removeSignature(item);
        if (command) player.getInventory().addItem(item);
        else {
            return item;
        }

        return null;
    }

    //Remove signature on the items description
    public void removeSignature(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore;
        lore = meta.getLore();

        if (lore.get(lore.size() - 1).contains("Signed on"))
            lore.remove(lore.size() - 1);

        int cap;
        if (lore.get(lore.size() - 1).contains("Signed")) cap = 2;
        else cap = 3;

        for (int i = 0; i < cap; i++) {
            if (lore.size() > 0) lore.remove(lore.size() - 1);
        }


        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    //Removes all lore on an item
    public void removeLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(null);
        item.setItemMeta(meta);
    }

    public void copyAmount(String a, Player player) {
        if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            ChatUtils.errorMessage(player, "You must hold an item in your hand!");
            return;
        }
        if(!Utility.isInt(a)) {
            ChatUtils.errorMessage(player, "That is not a valid amount.");
            return;
        }
        int amount = Integer.parseInt(a);
        if(amount <= 0) {
            ChatUtils.errorMessage(player, "That is not a valid amount.");
            return;
        }

        ItemStack itemToCopy = player.getInventory().getItemInMainHand();

        itemToCopy.setAmount(itemToCopy.getAmount() + amount);
        player.sendMessage(ChatColor.GREEN + PE_PREFIX + " Item copied " + amount + " times!");
    }

}

