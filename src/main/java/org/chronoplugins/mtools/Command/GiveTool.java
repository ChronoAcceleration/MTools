package org.chronoplugins.mtools.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.chronoplugins.mtools.Other.itemownership;
import org.chronoplugins.mtools.Other.prefixlogger;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class GiveTool implements CommandExecutor {
    private ItemStack createWand(Player player) {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Wand");
        meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&0") + player.getUniqueId().toString()));
        item.setItemMeta(meta);
        return item;
    }
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

            for(ItemStack item : targetPlayer.getInventory()) {
                if(item != null && item.getType() == Material.STICK) {
                    if (itemownership.checkItemOwnership(targetPlayer, item)) {
                        hasWand = true;
                        break;
                    }
                }
            }

            if (hasWand) {
                prefixlogger.sendChatMessage(targetPlayer.getName() + " already has a wand!", player);
                return true;
            } else {
                ItemStack wand = createWand(targetPlayer);
                targetPlayer.getInventory().addItem(wand);
                prefixlogger.sendChatMessage(targetPlayer.getName() + " has received a wand!", player);
                return true;
            }
        } else {
            for(ItemStack item : player.getInventory()) {
                if(item != null && item.getType() == Material.STICK) {
                    if (itemownership.checkItemOwnership(player, item)) {
                        hasWand = true;
                        break;
                    }
                }
            }

            if (hasWand) {
                prefixlogger.sendChatMessage("You already have a wand!", player);
                return true;
            } else {
                ItemStack wand = createWand(player);
                player.getInventory().addItem(wand);
                prefixlogger.sendChatMessage("You now have a wand!", player);
                return true;
            }
        }
    }
}
