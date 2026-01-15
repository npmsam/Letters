package com.sampreet.letters.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderAPIHook {
    // Prevent instantiation of the class
    private PlaceholderAPIHook() {
    }

    // Helper function to apply placeholders for a player
    public static String usePlaceholderAPI(Player player, String message) {
        // Return the same message without changes if PlaceholderAPI isn't found
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) return message;

        // Return message with PlaceholderAPI placeholders set
        return PlaceholderAPI.setPlaceholders(player, message);
    }
}
