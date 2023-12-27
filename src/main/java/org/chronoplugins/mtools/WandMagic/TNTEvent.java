package org.chronoplugins.mtools.WandMagic;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import java.util.List;

public class TNTEvent implements Listener {
    private void spawnAdditionalTNT(Location location, int count) {
        for (int i = 0; i < count; i++) {
            TNTPrimed additionalTNT = (TNTPrimed) location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
            additionalTNT.setVelocity(new Vector(Math.random() * 2 - 1, Math.random() * 2 - 1, Math.random() * 2 - 1));
        }
    }

    private static boolean checkTnt(Entity tnt) {
        List<MetadataValue> metadata = tnt.getMetadata("TntDupe");

        if (!metadata.isEmpty()) {
            return metadata.get(0).asBoolean();
        } else {
            return false;
        }
    }

    @EventHandler
    public void onTNT(EntityExplodeEvent e){
        Entity tnt = e.getEntity();
        if (tnt.getType() == EntityType.PRIMED_TNT && checkTnt(tnt)) {
            World world = e.getLocation().getWorld();
            world.playSound(e.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.2f, 0.3f);
            spawnAdditionalTNT(tnt.getLocation(), 4);
        }
    }
}
