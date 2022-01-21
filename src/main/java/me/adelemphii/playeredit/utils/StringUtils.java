package me.adelemphii.playeredit.utils;

import me.adelemphii.playeredit.utils.enums.DefaultFontInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    /**
     * Combines elements from String array into a single String separated by spaces.
     * Example: args["This", "Is", "A", "Test"] -> "This Is A Test".
     *
     * @param args
     * @param startPos Starting position in the array if elements are to be skipped.
     * @return Result String
     */
    public static String combineArgs(String[] args, int startPos) {

        StringBuilder output = new StringBuilder();

        for (int i = startPos; i < args.length; i++) {

            output.append(args[i]);
            if (i != args.length - 1) output.append(" ");

        }

        return output.toString();

    }

    /**
     * Same as "equalsIgnoreCase", but with a contains rather
     * than equals.
     *
     * @param string   String to search
     * @param lookFor  What we are looking for the string to contain
     * @return true if it contains the string to look for
     */
    public static boolean containsIgnoreCase(String string, String lookFor) {
        return string.toLowerCase().contains(lookFor.toLowerCase());
    }

    /**
     * Sets up the tab-complete for an argument.
     *
     * @param arg            The argument that will be tab-completed
     * @param possibleValues All values that arg can be tab-completed into
     * @return String List to be returned in the onTabComplete() method.
     */
    public static List<String> tabCompleteArg(String arg, List<String> possibleValues) {

        List<String> output = new ArrayList<>();

        if (arg.equals("")) {

            for (String value : possibleValues) {

                if (value.toLowerCase().startsWith(arg.toLowerCase())) {

                    output.add(value);

                }

            }

        } else {

            output.addAll(possibleValues);

        }

        return output;

    }

    private final static int CENTER_PX = 154;

    public static String colorCode(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Sends a message to an entity that is
     * centred in the minecraft chat
     *
     * @param livingEntity    thing to send to
     * @param message         message to center
     */

    public static void sendCenteredMessage(LivingEntity livingEntity, String message) {
        if (message == null || message.equals("")) livingEntity.sendMessage("");
        assert (message) != null;
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        livingEntity.sendMessage(sb + message);
    }

    /**
     * Converts a location to a serialized String
     *
     * @param l - Location to serialize
     * @return - Returns a String of a serialized String
     */
    static public String getStringLocation(final Location l) {
        if (l == null) {
            return "";
        }
        return l.getWorld().getName() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ();
    }

    /**
     * Converts Serialized String to a Location
     *
     * @param s - String
     * @return - Returns a Location if possible
     */
    static public Location getLocationString(final String s) {
        if (s == null || s.trim().equals("")) {
            return null;
        }
        final String[] parts = s.split(":");
        if (parts.length == 4) {
            final World w = Bukkit.getServer().getWorld(parts[0]);
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            final int z = Integer.parseInt(parts[3]);
            return new Location(w, x, y, z);
        }
        return null;
    }

    /**
     * Converts an array of strings in to
     * a full message, arg joiner
     *
     * @param args    array of strings
     * @param start   index to start from
     * @return string of args joined
     */
    public static String getFullMessage(String[] args, int start) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }

        return sb.toString().trim();
    }

    public static String parseConfigString(String toParse, String toReplace, String string) {
        return string.replace(toParse, toReplace);
    }

    public static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }




}

