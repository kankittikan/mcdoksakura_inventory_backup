package com.doksakura;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RestoreCommand implements CommandExecutor {

    private final InventoryBackup plugin;

    public RestoreCommand(InventoryBackup plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /restore <player>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return false;
        }

        File backupDir = new File(plugin.getDataFolder(), "backups/" + target.getUniqueId());
        if (!backupDir.exists() || backupDir.listFiles() == null || backupDir.listFiles().length == 0) {
            sender.sendMessage("No backups found for this player.");
            return false;
        }

        File[] backups = backupDir.listFiles();
        Arrays.sort(backups, Comparator.comparingLong(File::lastModified).reversed());

        File latestBackup = backups[0];
        org.bukkit.configuration.file.FileConfiguration config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(latestBackup);

        ItemStack[] armor = ((List<ItemStack>) config.get("inventory.armor")).toArray(new ItemStack[0]);
        ItemStack[] content = ((List<ItemStack>) config.get("inventory.content")).toArray(new ItemStack[0]);

        target.getInventory().setArmorContents(armor);
        target.getInventory().setContents(content);

        sender.sendMessage("Inventory restored for " + target.getName());
        return true;
    }
}
