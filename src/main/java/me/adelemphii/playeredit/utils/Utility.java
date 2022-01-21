package me.adelemphii.playeredit.utils;

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
}


