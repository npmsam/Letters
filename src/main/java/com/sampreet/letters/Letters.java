package com.sampreet.letters;

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
