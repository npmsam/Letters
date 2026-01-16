package com.sampreet.letters.hooks;

import com.sampreet.letters.Letters;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LuckPermsHook {
    // Prevent instantiation of the class
    private LuckPermsHook() {
    }

    // Function to check if PlaceholderAPI is installed and print a message
    public static void checkLuckPerms(Letters plugin) {
        // Check if PlaceholderAPI is present
        boolean LuckPermsFound = Bukkit.getPluginManager().getPlugin("LuckPerms") != null;

        // Store config message path depending on whether PlaceholderAPI is present or
        // not
        String messagePath = LuckPermsFound
                ? "messages.system.checks.luck-perms-found"
                : "messages.system.checks.luck-perms-not-found";

        // Retrieve the message from the config
        String message = plugin.getConfig().getString(messagePath);

        // Return if the message is empty or null
        if (message == null || message.trim().isEmpty())
            return;

        // Log whether PlaceholderAPI was found or not
        if (LuckPermsFound)
            plugin.getLogger().info(message);
        else
            plugin.getLogger().warning(message);
    }

    // Helper function to get the primary group of a player
    public static String getPrimaryGroup(Player player) {
        // Return null if luckperms isn't installed
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") == null) return null;

        // Get player data stored in luckperms
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());

        // Return if the user isn't found in luckperms
        if (user == null)
            return null;

        // Return the primary group of the user
        return user.getPrimaryGroup().toLowerCase();
    }
}
