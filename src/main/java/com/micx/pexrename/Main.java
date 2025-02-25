package com.micx.pexrename;

import com.micx.pexrename.commands.RenameCommand;
import com.micx.pexrename.commands.SetLoreCommand;
import com.micx.pexrename.utils.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadMessages();
        getLogger().info(MessageUtils.getPrefix() + "Plugin enabled!");

        // Rejestracja komend
        getCommand("rename").setExecutor(new RenameCommand(this));
        getCommand("zmiennazwe").setExecutor(new RenameCommand(this));
        getCommand("setlore").setExecutor(new SetLoreCommand(this));
        getCommand("ustawlore").setExecutor(new SetLoreCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info(MessageUtils.getPrefix() + "Plugin disabled!");
    }

    private void loadMessages() {
        File messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        MessageUtils.loadMessages(this);
    }
}
