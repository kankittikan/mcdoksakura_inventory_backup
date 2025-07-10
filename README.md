# InventoryBackup

A Minecraft 1.12.2 Spigot plugin that automatically backs up a player's inventory upon death. Administrators can restore the inventory from a backup.

## Features

-   **Automatic Backups**: When a player dies, their inventory (including armor) is automatically saved.
-   **Multiple Backups**: Stores the last three death inventories for each player.
-   **Admin Restoration**: Server operators can easily restore a player's inventory using a simple command.
-   **Backup Listing**: List all available backups for a player and restore a specific backup by index.

## Installation

1.  Download the latest `InventoryBackup-1.0-SNAPSHOT.jar` from the [releases page](<https://github.com/your-username/InventoryBackup/releases>).
2.  Place the `.jar` file into the `plugins` folder of your Spigot (or compatible) server.
3.  Restart your server.

## Commands

### `/listbackups <player>`
Lists all available backups for the specified player, showing their index and creation time.
- **Usage**: `/listbackups <player_name>`
- **Permission**: `inventorybackup.list`

### `/restore <player> [backup_index]`
Restores a player's inventory from a backup. If `backup_index` is not specified, restores the most recent backup.
- **Usage**: `/restore <player_name> [backup_index]`
- **Permission**: `inventorybackup.restore`

## Permissions

-   `inventorybackup.restore`: Allows a user to execute the `/restore` command.
    -   **Default**: `op`
-   `inventorybackup.list`: Allows a user to execute the `/listbackups` command.
    -   **Default**: `op`

## Building from Source

To build the plugin from source, you will need [Maven](<https://maven.apache.org/>) and [Git](<https://git-scm.com/>).

1.  Clone the repository:
    ```sh
    git clone https://github.com/your-username/InventoryBackup.git
    cd InventoryBackup
    ```
2.  Compile and package the plugin:
    ```sh
    mvn clean package
    ```
3.  The compiled `.jar` file will be located in the `target` directory.
