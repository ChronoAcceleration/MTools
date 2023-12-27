package org.chronoplugins.mtools.Other;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static final Block getTargetBlock(Player player, int range) {
        BlockIterator iteration = new BlockIterator(player, range);
        Block lastBlock = iteration.next();
        while (iteration.hasNext()) {
            lastBlock = iteration.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static List<Block> getBlocksInRadius(Block centerBlock, int radius) {
        World world = centerBlock.getWorld();
        int centerX = centerBlock.getX();
        int centerY = centerBlock.getY();
        int centerZ = centerBlock.getZ();
        List<Block> blocksInRadius = new ArrayList<>();
        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int y = centerY - radius; y <= centerY + radius; y++) {
                for (int z = centerZ - radius; z <= centerZ + radius; z++) {
                    Location currentLocation = new Location(world, x, y, z);
                    Block block = world.getBlockAt(currentLocation);
                    blocksInRadius.add(block);
                }
            }
        }
        return blocksInRadius;
    }

}
