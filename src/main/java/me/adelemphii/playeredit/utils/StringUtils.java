package me.adelemphii.playeredit.utils;

public class StringUtils {

    /**
     * Combines elements from String array into a single String separated by spaces.
     * Example: args["This", "Is", "A", "Test"] -> "This Is A Test".
     *
     * @param args Args to be combined
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
}

