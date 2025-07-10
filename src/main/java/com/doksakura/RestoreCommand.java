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
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage("Usage: /restore <player> [backup_index]");
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

        int backupIndex = 1;
        if (args.length == 2) {
            try {
                backupIndex = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid backup index.");
                return false;
            }
        }

        if (backupIndex <= 0 || backupIndex > backups.length) {
            sender.sendMessage("Backup index out of bounds.");
            return false;
        }

        File backupToRestore = backups[backupIndex - 1];
        org.bukkit.configuration.file.FileConfiguration config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(backupToRestore);

        @SuppressWarnings("unchecked")
        List<ItemStack> armorList = (List<ItemStack>) config.getList("inventory.armor");
        @SuppressWarnings("unchecked")
        List<ItemStack> contentList = (List<ItemStack>) config.getList("inventory.content");

        ItemStack[] armor = armorList.toArray(new ItemStack[0]);
        ItemStack[] content = contentList.toArray(new ItemStack[0]);

        target.getInventory().setArmorContents(armor);
        target.getInventory().setContents(content);

        sender.sendMessage("Inventory restored for " + target.getName() + " from backup " + backupIndex);
        return true;
    }
}
