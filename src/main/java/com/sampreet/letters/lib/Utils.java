package com.sampreet.letters.lib;

import com.sampreet.letters.Letters;
import org.bukkit.ChatColor;

public class Utils {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public Utils(Letters plugin) {
        this.plugin = plugin;
    }

    // Helper function to retrieve a message from config.yml and skip if null or empty
    public String getMessage(String path) {
        String message = plugin.getConfig().getString(path);

        // Return if no message is set in config.yml
        if (message == null || message.trim().isEmpty()) {
            return null;
        }

        // Return the message as a string if it's not null or empty
        return message;
    }

    // Helper function to insert placeholders into messages
    public String setPlaceholders(String message) {
        // Insert current plugin version into placeholder
        message = message.replace("%version%", plugin.getDescription().getVersion());

        // Translate color codes
        message = ChatColor.translateAlternateColorCodes('&', message);

        // Return the message with the placeholders set
        return message;
    }
}
