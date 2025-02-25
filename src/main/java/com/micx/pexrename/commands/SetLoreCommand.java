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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SetLoreCommand implements CommandExecutor {

    private final Main plugin;

    public SetLoreCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessageUtils.getPrefix() + MessageUtils.getMessage("only_players"));
            return true;
        }

        if (!player.hasPermission("pex.setlore")) {
            MessageUtils.sendMessage(player, "no_permission");
            return true;
        }

        if (args.length == 0) {
            MessageUtils.sendMessage(player, "usage_setlore");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            MessageUtils.sendMessage(player, "no_item_in_hand");
            return true;
        }

        // Obsługa wieloliniowego lore (rozdzielane przez \n)
        List<String> newLore = Arrays.asList(String.join(" ", args).split("\\\\n"));

        // Sprawdzenie maksymalnej liczby linii
        int maxLoreLines = plugin.getConfig().getInt("max-lore-lines", 0);
        if (maxLoreLines > 0 && newLore.size() > maxLoreLines) {
            MessageUtils.sendMessage(player, "lore_too_many_lines", Map.of("max", String.valueOf(maxLoreLines)));
            return true;
        }

        // Sprawdzenie długości każdej linii
        int maxLoreLineLength = plugin.getConfig().getInt("max-lore-line-length", 0);
        if (maxLoreLineLength > 0) {
            for (String line : newLore) {
                if (line.length() > maxLoreLineLength) {
                    MessageUtils.sendMessage(player, "lore_line_too_long", Map.of("max", String.valueOf(maxLoreLineLength)));
                    return true;
                }
            }
        }

        if (!PermissionsUtils.hasPermissionForFormatting(player, String.join(" ", newLore))) {
            MessageUtils.sendMessage(player, "no_format_permission");
            return true;
        }

        // Konwersja kolorów
        List<String> formattedLore = new ArrayList<>();
        for (String line : newLore) {
            formattedLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        // Ustawienie lore i zapisanie w przedmiocie
        meta.setLore(formattedLore);
        item.setItemMeta(meta);

        // Tworzenie jednej linii tekstu z lore do wiadomości
        String loreString = String.join(", ", formattedLore);

        // Wysyłanie wiadomości z dynamicznym lore
        MessageUtils.sendMessage(player, "lore_set", Map.of("lore", loreString));
        return true;
    }
}
