package com.sampreet.letters.helpers;

import com.sampreet.letters.Letters;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlaceholdersHelper {
    // Prevent instantiation of the class
    private PlaceholdersHelper() {
    }

    // Helper function to insert placeholders into messages
    public static String setPlaceholders(String message, Event event, Letters plugin) {
        // Return null if the message passed is null
        if (message == null) return null;

        // Insert current plugin version into placeholder
        if (plugin != null) message = message.replace("%plugin_version%", plugin.getDescription().getVersion());

        // Replace placeholders for when player joins server
        if (event instanceof PlayerJoinEvent playerJoinEvent) {
            message = message
                    .replace("%player_display_name%", playerJoinEvent.getPlayer().getDisplayName())
                    .replace("%player_username%", playerJoinEvent.getPlayer().getName());
        }

        // Replace placeholders for when player quits server
        if (event instanceof PlayerQuitEvent playerQuitEvent) {
            message = message
                    .replace("%player_display_name%", playerQuitEvent.getPlayer().getDisplayName())
                    .replace("%player_username%", playerQuitEvent.getPlayer().getName());
        }

        // Return the message with the placeholders set
        return message;
    }

    // Helper function to use the function above without needing to pass an event
    public static String setPlaceholders(String message, Letters plugin) {
        // Call the original function but pass null for the event parameter
        return setPlaceholders(message, null, plugin);
    }
}
