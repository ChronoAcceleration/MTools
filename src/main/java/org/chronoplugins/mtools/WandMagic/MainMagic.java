package org.chronoplugins.mtools.WandMagic;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.chronoplugins.mtools.Other.itemownership;
import org.chronoplugins.mtools.Other.modeHandler;

public class MainMagic implements Listener {

    private static modeHandler.WandMode getWandEnum(Player player) {
        int current = modeHandler.getMode(player);
        return modeHandler.WandMode.values()[current-1];
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        assert item != null;

        if (!itemownership.checkItemOwnershipStrict(player, item)) {
            return;
        }

        modeHandler.WandMode mode = getWandEnum(player);

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            modeHandler.WandMode.valueOf(mode.name()).execute(player);
        }
    }
}
