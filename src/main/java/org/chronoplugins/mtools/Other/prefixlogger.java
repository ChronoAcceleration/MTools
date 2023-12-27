package org.chronoplugins.mtools.Other;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class prefixlogger {
    private static final String prefix = ChatColor.LIGHT_PURPLE + "[MTools]: " + ChatColor.RESET;
    private static final String defaultedPrefix = "[MTools] ";

    public static void sendChatMessage(String message, Player player) {
        player.sendMessage(prefix + message);
    }

    public static String constructRawMessage(String message) {
        return defaultedPrefix + message;
    }
}
