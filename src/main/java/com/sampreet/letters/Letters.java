package com.sampreet.letters;

import com.sampreet.letters.hooks.LuckPermsHook;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import com.sampreet.letters.listeners.PlayerJoinListener;
import com.sampreet.letters.listeners.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Letters extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (!getConfig().getBoolean("enabled", true)) {
            logMessage("system_messages.status.disabled");

            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PlaceholderApiHook.checkPlaceholderAPI(this);
        LuckPermsHook.checkLuckPerms(this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        logMessage("system_messages.lifecycle.enable");
    }

    @Override
    public void onDisable() {
        logMessage("system_messages.lifecycle.disable");
    }

    private void logMessage(String path) {
        String message = getConfig().getString(path);
        if (message == null || message.trim().isEmpty()) return;
        message = message.replace("<plugin_version>", getDescription().getVersion());
        getLogger().info(message);
    }
}