package com.doksakura;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class DeathListener implements Listener {

    private final InventoryBackup plugin;

    public DeathListener(InventoryBackup plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        File backupDir = new File(plugin.getDataFolder(), "backups/" + player.getUniqueId());
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        File[] backups = backupDir.listFiles();
        if (backups != null && backups.length >= 3) {
            Arrays.sort(backups, Comparator.comparingLong(File::lastModified));
            backups[0].delete();
        }

        File backupFile = new File(backupDir, System.currentTimeMillis() + ".yml");
        try {
            backupFile.createNewFile();
            org.bukkit.configuration.file.FileConfiguration config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(backupFile);
            config.set("inventory.armor", player.getInventory().getArmorContents());
            config.set("inventory.content", player.getInventory().getContents());
            config.save(backupFile);
            player.sendMessage("Your inventory has been backed up.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
