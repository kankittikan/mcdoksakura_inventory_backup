package com.doksakura;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class ListBackupsCommand implements CommandExecutor {

    private final InventoryBackup plugin;

    public ListBackupsCommand(InventoryBackup plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /listbackups <player>");
            return false;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
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

        sender.sendMessage("Backups for " + target.getName() + ":");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < backups.length; i++) {
            Date date = new Date(backups[i].lastModified());
            sender.sendMessage((i + 1) + ": " + sdf.format(date));
        }

        return true;
    }
}
