package com.sampreet.letters.lib;

import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.command.CommandSender;
import com.sampreet.letters.Letters;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.ChatColor;
import java.util.Objects;
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

    // Helper function to insert placeholders and colors into messages
    public String setPlaceholders(String message, Event event) {
        // Insert current plugin version into placeholder
        message = message.replace("%version%", plugin.getDescription().getVersion());

        // Inserting event-specific placeholders
        if (event!=null) {
            // Replace placeholders for when player joins server
            switch (event) {
                case PlayerJoinEvent joinEvent ->
                        message = message.replace("%player_name%", joinEvent.getPlayer().getDisplayName());

                // Replace placeholders for when player quits server
                case PlayerQuitEvent quitEvent ->
                        message = message.replace("%player_name%", quitEvent.getPlayer().getDisplayName());

                // Replace placeholders for when player dies
                case PlayerDeathEvent deathEvent ->
                        message = message
                                .replace("%player_name%", deathEvent.getEntity().getDisplayName())
                                .replace(
                                        "%death_message%",
                                        Objects.requireNonNullElse(
                                                deathEvent.getDeathMessage(),
                                                "%death_message%"
                                        )
                                );

                // Replace placeholders for when player makes an advancement
                case PlayerAdvancementDoneEvent advancementEvent ->
                        message = message
                                .replace("%player_name%", advancementEvent.getPlayer().getDisplayName())
                                .replace("%advancement_name%", Objects.requireNonNull(advancementEvent.getAdvancement().getDisplay()).getTitle())
                                .replace("%advancement_color%", "&" + advancementEvent.getAdvancement().getDisplay().getType().getColor().getChar());

                // Replace placeholders for when player sends a message in the server chat
                case AsyncPlayerChatEvent ignored ->
                        message = message
                                .replace("%player_name%", "%1$s")
                                .replace("%chat_message%", "%2$s");

                default -> {}
            }
        }

        // Translate color codes
        message = ChatColor.translateAlternateColorCodes('&', message);

        // Return the message with the placeholders set
        return message;
    }

    // Helper function to insert placeholders and colors into messages for commands
    public String setPlaceholders(String message, CommandSender sender, Player target, String whisperMessage) {
        // Insert current plugin version into placeholder
        message = message.replace("%version%", plugin.getDescription().getVersion());

        // Insert command sender name
        if (sender instanceof Player player) {
            message = message
                    .replace("%sender_name%", player.getDisplayName())
                    .replace("%player_name%", player.getDisplayName());
        } else {
            message = message
                    .replace("%sender_name%", sender.getName())
                    .replace("%player_name%", sender.getName());
        }

        // Insert recipient player name
        if (target!=null) {
            message = message.replace("%recipient_name%", target.getDisplayName());
        }

        // Insert whisper message
        if (whisperMessage!=null) {
            message = message.replace("%whisper_message%", whisperMessage);
        }

        // Translate color codes
        message = ChatColor.translateAlternateColorCodes('&', message);

        // Return the message with the placeholders set
        return message;
    }
}
