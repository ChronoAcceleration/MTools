package org.chronoplugins.mtools;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.chronoplugins.mtools.Command.GiveTool;
import org.chronoplugins.mtools.Command.RemoveTool;
import org.chronoplugins.mtools.Other.prefixlogger;
import org.chronoplugins.mtools.WandMagic.MainMagic;
import org.chronoplugins.mtools.WandMagic.SwitchMode;
import org.chronoplugins.mtools.WandMagic.TNTEvent;

public final class MTools extends JavaPlugin {
    public static Plugin getPlugin() {
        return JavaPlugin.getPlugin(MTools.class);
    }
    @Override
    public void onEnable() {
        // Register Commands

        getCommand("stickwand").setExecutor(new GiveTool());
        getCommand("removewand").setExecutor(new RemoveTool());

        // Register Events

        getServer().getPluginManager().registerEvents(new SwitchMode(), this); // Drop Event;
        getServer().getPluginManager().registerEvents(new TNTEvent(), this); // TNT Dupe Event;
        getServer().getPluginManager().registerEvents(new MainMagic(), this); // Click Event;

        // Finalize

        getServer().getLogger().info(prefixlogger.constructRawMessage("Completed Registration!"));
    }

    @Override
    public void onDisable() {
        getServer().getPluginManager().disablePlugin(this);
    }
}
