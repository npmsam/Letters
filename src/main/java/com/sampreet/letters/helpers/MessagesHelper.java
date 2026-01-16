package com.sampreet.letters.helpers;

import com.sampreet.letters.Letters;
import com.sampreet.letters.hooks.LuckPermsHook;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MessagesHelper {
    // Prevent instantiation of the class
    private MessagesHelper() {
    }

    // Helper function to retrieve a random message from a string list in config.yml
    public static String getRandomMessage(String path, Letters plugin) {
        // Return null if the path or plugin instance passed is null
        if (path == null || plugin == null)
            return null;

        // Store all messages in a list
        List<String> messages = plugin.getConfig().getStringList(path);

        // Filter out all null or empty strings like ones which are just whitespaces
        List<String> validMessages = messages.stream()
                .filter(msg -> msg != null && !msg.trim().isEmpty())
                .toList();

        // Return null if no valid messages are found
        if (validMessages.isEmpty())
            return null;

        // Return a randomly selected message from the list of valid messages
        return validMessages.get(ThreadLocalRandom.current().nextInt(validMessages.size()));
    }

    // Helper function retrieve an appropriate message based on player name and primary group
    public static String resolveRandomMessage(Player player, String key, Letters plugin) {
        // Try getting the player-specific message from config.yml
        String message = getRandomMessage("messages.players." + player.getName() + "." + key, plugin);

        // If no player-specific message was found, then check for primary group
        if (message == null) {
            // The function returns the primary group name in lowercase or null
            String primaryGroup = LuckPermsHook.getPrimaryGroup(player);

            if (primaryGroup != null) message = getRandomMessage("messages.groups." + primaryGroup + "." + key, plugin);
        }

        // If none found, fall back to default message
        if (message == null) message = getRandomMessage("messages.default." + key, plugin);

        // Return the retrieved message
        return message;
    }

    // Helper function to convert a string containing & color codes and mini message
    // to a component
    public static Component translateColors(String message) {
        // Return null if the message passed is null
        if (message == null)
            return null;

        // Convert mini message format to a component
        Component messageComponent = MiniMessage.miniMessage().deserialize(message);

        // Apply legacy formating and colors on the component
        String legacySerialized = LegacyComponentSerializer.legacyAmpersand().serialize(messageComponent);

        // Convert the legacy serialized string back to a string
        return LegacyComponentSerializer.legacyAmpersand().deserialize(legacySerialized);
    }
}
