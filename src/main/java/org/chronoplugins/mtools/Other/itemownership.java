package org.chronoplugins.mtools.Other;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class itemownership {

    public static boolean checkItemOwnership(Player player, ItemStack item) {
        if (item == null) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            try {
                UUID uuid = UUID.fromString(ChatColor.stripColor(meta.getLore().get(0)));
                if (uuid.toString().equals(player.getUniqueId().toString())) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean checkItemOwnershipStrict(Player player, ItemStack item) {
        if (item == null) {
            return false;
        }
        if (item.getType() != Material.STICK) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName() || !meta.getDisplayName().equals("Wand")) {
            return false;
        }
        if (!itemownership.checkItemOwnership(player, item)) {
            return false;
        }
        return true;
    }

}
