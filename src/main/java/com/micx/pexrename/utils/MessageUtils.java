package com.micx.pexrename.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MessageUtils {
    private static FileConfiguration messages;
    private static final Map<String, String> placeholders = new HashMap<>();

    public static void loadMessages(Plugin plugin) {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        messages = YamlConfiguration.loadConfiguration(file);
        placeholders.put("prefix", getPrefix());
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', messages.getString("prefix", "&8&l[&6Pex&eRename&8&l] "));
    }

    public static String getMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&', messages.getString("messages." + key, key));
    }

    public static String getMessage(String key, Map<String, String> replacements) {
        String message = getMessage(key);
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            message = message.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return message;
    }

    public static void sendMessage(Player player, String key) {
        player.sendMessage(getPrefix() + getMessage(key));
    }

    public static void sendMessage(Player player, String key, Map<String, String> replacements) {
        player.sendMessage(getPrefix() + getMessage(key, replacements));
    }
}
