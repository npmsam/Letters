package com.sampreet.letters.lib;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.concurrent.ThreadLocalRandom;
import com.sampreet.letters.Letters;
import org.bukkit.event.Event;
import org.bukkit.ChatColor;
import java.util.List;

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

    // Helper function to retrieve a random message from a string list in config.yml
    public String getRandomMessage(String path) {
        // Store all messages in a list
        List<String> messages = plugin.getConfig().getStringList(path);

        // Filter out all null or empty strings like ones which are just whitespaces
        List<String> validMessages = messages.stream()
                .filter(msg -> msg != null && !msg.trim().isEmpty())
                .toList();

        // Return null if no valid messages are found
        if (validMessages.isEmpty()) {
            return null;
        }

        // Return a randomly selected message from the list of valid messages
        return validMessages.get(
                ThreadLocalRandom.current().nextInt(validMessages.size())
        );
    }

    // Function without an event being required
    public String setPlaceholders(String message) {
        return setPlaceholders(message, null);
    }

    // Helper function to insert placeholders into messages
    public String setPlaceholders(String message, Event event) {
        // Insert current plugin version into placeholder
        message = message.replace("%version%", plugin.getDescription().getVersion());

        // Inserting event-specific placeholders
        if (event!=null) {
            // Replace placeholders for when player joins server
            if (event instanceof PlayerJoinEvent joinEvent) {
                message = message.replace("%player_name%", joinEvent.getPlayer().getDisplayName());
            }
            // Replace placeholders for when player quits server
            else if (event instanceof PlayerQuitEvent quitEvent) {
                message = message.replace("%player_name%", quitEvent.getPlayer().getDisplayName());
            }
        }

        // Translate color codes
        message = ChatColor.translateAlternateColorCodes('&', message);

        // Return the message with the placeholders set
        return message;
    }
}
