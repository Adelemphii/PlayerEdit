package me.adelemphii.playeredit.utils;

import me.adelemphii.playeredit.PlayerEdit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PDCEditor {

    public static boolean check(String s, String n, ItemStack item) {
        NamespacedKey key = new NamespacedKey(PlayerEdit.getInstance(), n);
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.isEmpty()) return false;
        if(container.has(key, PersistentDataType.STRING)) {
            return container.get(key, PersistentDataType.STRING).equalsIgnoreCase(s);
        }
        return false;
    }

    public static boolean hasKey(String n, ItemStack item) {
        NamespacedKey key = new NamespacedKey(PlayerEdit.getInstance(), n);
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.isEmpty()) return false;
        return container.has(key, PersistentDataType.STRING);
    }

    public static void setPDC(String user, String n, ItemStack item) {
        NamespacedKey key = new NamespacedKey(PlayerEdit.getInstance(), n);
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        container.set(key, PersistentDataType.STRING, user);
        item.setItemMeta(itemMeta);
    }

    public static void setString(String s, String n, ItemStack item) {
        NamespacedKey key = new NamespacedKey(PlayerEdit.getInstance(), n);
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        container.set(key, PersistentDataType.STRING, s.toLowerCase());
        item.setItemMeta(itemMeta);
    }

    public static String getString(String n, ItemStack item) {
        NamespacedKey key = new NamespacedKey(PlayerEdit.getInstance(), n);
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.has(key, PersistentDataType.STRING)) {
            return container.get(key, PersistentDataType.STRING);
        }
        return null;
    }

    public static void removeString(String n, ItemStack item) {
        NamespacedKey key = new NamespacedKey(PlayerEdit.getInstance(), n);
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.has(key, PersistentDataType.STRING)) {
            container.remove(key);
            item.setItemMeta(itemMeta);
        }
    }

    public static void setSigned(String user, String n, ItemStack item) {
        NamespacedKey key = new NamespacedKey(PlayerEdit.getInstance(), n);
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        container.set(key, PersistentDataType.STRING, user);
        item.setItemMeta(itemMeta);
    }
}
