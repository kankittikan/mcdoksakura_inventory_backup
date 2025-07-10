package com.doksakura;

import org.bukkit.plugin.java.JavaPlugin;

public class InventoryBackup extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("InventoryBackup enabled!");
        this.getCommand("restore").setExecutor(new RestoreCommand(this));
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("InventoryBackup disabled!");
    }
}
