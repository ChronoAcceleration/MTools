package org.chronoplugins.mtools.Command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.chronoplugins.mtools.Other.itemownership;
import org.chronoplugins.mtools.Other.prefixlogger;
import org.jetbrains.annotations.NotNull;

public class RemoveTool implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        final Player player = (Player) commandSender;

        if (!player.hasPermission("mtools.wand")) {
            prefixlogger.sendChatMessage("You do not have permission to use this command!", player);
            return true;
        }

        final boolean hasArguments;
        hasArguments = strings.length != 0;

        boolean hasWand = false;

        if (hasArguments) {
            final String playerName = strings[0];
            final Player targetPlayer = Bukkit.getPlayer(playerName);
            ItemStack wand = null;

            for (ItemStack item : targetPlayer.getInventory()) {
                if (item != null && item.getType() == Material.STICK) {
                    if (itemownership.checkItemOwnership(targetPlayer, item)) {
                        hasWand = true;
                        wand = item;
                        break;
                    }
                }
            }

            if (hasWand) {
                targetPlayer.getInventory().remove(wand);
                prefixlogger.sendChatMessage("Wand removed from " + targetPlayer.getName() + "!", player);
                return true;
            } else {
                prefixlogger.sendChatMessage(targetPlayer.getName() + " doesn't have a wand!", player);
                return true;
            }
        } else {
            ItemStack wand = null;

            for (ItemStack item : player.getInventory()) {
                if (item != null && item.getType() == Material.STICK) {
                    if (itemownership.checkItemOwnership(player, item)) {
                        hasWand = true;
                        wand = item;
                        break;
                    }
                }
            }

            if (hasWand) {
                player.getInventory().remove(wand);
                prefixlogger.sendChatMessage("Removed Wand!", player);
                return true;
            } else {
                prefixlogger.sendChatMessage("You already have a wand!", player);
                return true;
            }
        }
    }
}
