package org.chronoplugins.mtools.WandMagic;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.chronoplugins.mtools.Other.itemownership;
import org.chronoplugins.mtools.Other.modeHandler;

import java.util.UUID;

public class SwitchMode implements Listener {

    private static void sendHotbarMessage(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1.1f);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        ItemStack item = event.getItemDrop().getItemStack();
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta.hasDisplayName() && itemMeta.getDisplayName().equals("Wand")) {
            final boolean Check = itemownership.checkItemOwnership(player, item);

            if (Check) {
                int current = modeHandler.getMode(player);
                current++;
                if (current > modeHandler.WandMode.values().length) current = 1;
                modeHandler.setMode(player, current);
                modeHandler.WandMode mode = modeHandler.WandMode.values()[current-1];

                sendHotbarMessage(player, "Wand Mode: " + mode.toString());
                event.setCancelled(true);
                return;
            } else {
                return;
            }
        }
    }

}
