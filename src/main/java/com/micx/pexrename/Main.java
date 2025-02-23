package com.micx.pexrename;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Main extends JavaPlugin {

    private FileConfiguration config;
    private static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&8&l[&6Pex&eRename&8&l] ");

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        getLogger().info(PREFIX + "by _MicX_ has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(PREFIX + "by _MicX_ has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (cmd.getName().equalsIgnoreCase("rename") || cmd.getName().equalsIgnoreCase("zmiennazwe")) {
                if (player.hasPermission("pex.rename")) {
                    if (args.length > 0) {
                        ItemStack item = player.getInventory().getItemInMainHand();
                        ItemMeta meta = item.getItemMeta();
                        if (meta != null) {
                            String newName = ChatColor.translateAlternateColorCodes('&', String.join(" ", args));
                            meta.setDisplayName(newName);
                            item.setItemMeta(meta);
                            player.sendMessage(PREFIX + config.getString("messages.item_renamed", "Item renamed to: ") + newName);
                            return true;
                        }
                        player.sendMessage(PREFIX + config.getString("messages.no_item_in_hand", "You must be holding an item to rename it."));
                    } else {
                        player.sendMessage(PREFIX + config.getString("messages.usage", "Usage: /rename <new name> or /zmiennazwe <new name>"));
                    }
                } else {
                    player.sendMessage(PREFIX + "Nie masz pozwolenia do używania tej komendy.");
                }
            }
        }
        return false;
    }
}
