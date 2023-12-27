package org.chronoplugins.mtools.Other;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.chronoplugins.mtools.MTools;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class modeHandler {

    private static Plugin plugin = MTools.getPlugin();
    private static Server server = plugin.getServer();
    private static HashMap<UUID, Integer> lightningCharge = new HashMap<>();

    private static void strikeLightning(Block block) {
        Location location = block.getLocation();
        World world = block.getWorld();
        world.strikeLightning(location);
        int randomNumber = Utility.random(1, 5);

        if (randomNumber != 1) {
            return;
        }
        server.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                world.strikeLightningEffect(location);
            }
        }, 13L);
    }

    static void incrementCharge(Player player) {
        UUID playerId = player.getUniqueId();

        if (!lightningCharge.containsKey(playerId)) {
            lightningCharge.put(playerId, 0);
        }

        int charge = lightningCharge.get(playerId);
        charge++;

        lightningCharge.put(playerId, charge);
    }

    private static TNTPrimed tntLaunch(Player player) {
        Location playerLoc = player.getLocation();
        @NotNull Vector direction = playerLoc.getDirection();
        Location spawnLoc = playerLoc.add(direction.multiply(2));
        TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(spawnLoc, EntityType.PRIMED_TNT);
        tnt.setVelocity(direction.multiply(1.3));
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                if(!tnt.isDead()) {
                    player.getWorld().playSound(tnt.getLocation(), Sound.ENTITY_GUARDIAN_ATTACK, 0.5f, 2f);
                    player.getWorld().playSound(tnt.getLocation(), Sound.ENTITY_TNT_PRIMED, 0.6f, 1.5f);
                } else {
                    this.cancel();
                }
            }
        };
        task.runTaskTimer(plugin, 0L, 5L);

        return tnt;
    }

    public enum WandMode {

        LIGHTNING(1),
        TNT(2);

        private final int value;

        WandMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void execute(Player player) {
            UUID playerId = player.getUniqueId();
            int charge = lightningCharge.getOrDefault(playerId, 0);

            if (this == WandMode.LIGHTNING) {
                if (!player.isSneaking()) {
                    Block targetBlock = Utility.getTargetBlock(player, 50);
                    strikeLightning(targetBlock);
                } else if (player.isSneaking()) {
                    if (charge == 7) {
                        Block targetBlock = Utility.getTargetBlock(player, 65);
                        List<Block> blocks = Utility.getBlocksInRadius(targetBlock, 10);

                        strikeLightning(targetBlock);
                        World world = targetBlock.getWorld();
                        world.createExplosion(targetBlock.getLocation(), 15f, true, true);
                        world.playSound(targetBlock.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 20f, 1.2f);

                        for (Block block : blocks) {
                            final int randomNumber = Utility.random(1, 250);
                            if (randomNumber != 1) continue;
                            strikeLightning(block);
                            world.createExplosion(block.getLocation(), 5f, true, true);
                        }
                        charge = 0;
                        lightningCharge.put(playerId, charge);
                    } else {
                        incrementCharge(player);
                        switch (charge) {
                            case 1 -> player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1f, 1f);
                            case 2 -> player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1f, 1.1f);
                            case 3 -> player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1f, 1.2f);
                            case 4 -> player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1f, 1.3f);
                            case 5 -> player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1f, 1.4f);
                            case 6 -> player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1f, 1.5f);
                        }
                    }
                }
            } else if (this == WandMode.TNT) {
                if (!player.isSneaking()) {
                    tntLaunch(player);
                } else {
                    final TNTPrimed tnt = tntLaunch(player);
                    tnt.setMetadata("TntDupe", new FixedMetadataValue(plugin, true));
                }
            }
        }

    }

    static HashMap<UUID, Integer> data = new HashMap<>();

    private static boolean checkData(Player player) {
        if (data.containsKey(player.getUniqueId())) {
            return true;
        } else {
            return false;
        }
    }

    public static void setMode(Player player, Integer mode) {
        if (checkData(player)) {
            data.replace(player.getUniqueId(), mode);
        } else {
            data.put(player.getUniqueId(), mode);
        }

    }

    public static Integer getMode(Player player) {
        if (checkData(player)) {
            return data.get(player.getUniqueId());
        } else {
            data.put(player.getUniqueId(), 1);
            return data.get(player.getUniqueId());
        }
    }
}
