package com.sampreet.letters.helpers;

import com.sampreet.letters.Letters;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

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
