package com.micx.pexrename.commands;

import com.micx.pexrename.Main;
import com.micx.pexrename.utils.MessageUtils;
import com.micx.pexrename.utils.PermissionsUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class RenameCommand implements CommandExecutor {

    private final Main plugin;

    public RenameCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessageUtils.getPrefix() + MessageUtils.getMessage("only_players"));
            return true;
        }

        if (!player.hasPermission("pex.rename")) {
            MessageUtils.sendMessage(player, "no_permission");
            return true;
        }

        if (args.length == 0) {
            MessageUtils.sendMessage(player, "usage_rename");
            return true;
        }

        String newName = String.join(" ", args);

        // Sprawdzenie długości nazwy
        int maxNameLength = plugin.getConfig().getInt("max-name-length", 0);
        if (maxNameLength > 0 && newName.length() > maxNameLength) {
            MessageUtils.sendMessage(player, "name_too_long", Map.of("max", String.valueOf(maxNameLength)));
            return true;
        }

        if (!PermissionsUtils.hasPermissionForFormatting(player, newName)) {
            MessageUtils.sendMessage(player, "no_format_permission");
            return true;
        }

        newName = ChatColor.translateAlternateColorCodes('&', newName);
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(newName);
            item.setItemMeta(meta);
            MessageUtils.sendMessage(player, "item_renamed", Map.of("name", newName));
        } else {
            MessageUtils.sendMessage(player, "no_item_in_hand");
        }
        return true;
    }
}
