package com.sampreet.letters.hooks;

import com.sampreet.letters.Letters;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderApiHook {
    // Prevent instantiation of the class
    private PlaceholderApiHook() {
    }

    // Helper function to apply placeholders for a player
    public static String usePlaceholderAPI(Player player, String message) {
        // Return the same message without changes if PlaceholderAPI isn't found
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
            return message;

        // Return message with PlaceholderAPI placeholders set
        return PlaceholderAPI.setPlaceholders(player, message);
    }

    // Function to check if PlaceholderAPI is installed and print a message
    public static void checkPlaceholderAPI(@NotNull Letters plugin) {
        // Check if PlaceholderAPI is present
        boolean PlaceholderApiFound = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

        // Store config message path depending on whether PlaceholderAPI is present or
        // not
        String messagePath = PlaceholderApiFound
                ? "messages.system.checks.placeholder-api-found"
                : "messages.system.checks.placeholder-api-not-found";

        // Retrieve the message from the config
        String message = plugin.getConfig().getString(messagePath);

        // Return if the message is empty or null
        if (message == null || message.trim().isEmpty())
            return;

        // Log whether PlaceholderAPI was found or not
        if (PlaceholderApiFound)
            plugin.getLogger().info(message);
        else
            plugin.getLogger().warning(message);
    }
}
