package com.sampreet.letters.lib;

import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import com.sampreet.letters.commands.WhisperCommand;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.concurrent.ThreadLocalRandom;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import com.sampreet.letters.Letters;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import java.util.Objects;
import java.util.List;

public class Utils {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public Utils(Letters plugin) {
        this.plugin = plugin;
    }

    // Helper function to retrieve a message from config.yml and skip if null or
    // empty
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
                ThreadLocalRandom.current().nextInt(validMessages.size()));
    }

    // Function without an event being required
    public String setPlaceholders(String message) {
        return setPlaceholders(message, null);
    }

    // Helper function to insert placeholders and colors into messages
    public String setPlaceholders(String message, Event event) {
        // Insert current plugin version into placeholder
        message = message.replace(Placeholders.PLUGIN_VERSION.key(), plugin.getDescription().getVersion());

        // Inserting event-specific placeholders
        if (event != null) {
            // Replace placeholders for when player joins server
            switch (event) {
                case PlayerJoinEvent joinEvent -> {
                    message = message.replace(Placeholders.PLAYER_NAME.key(), joinEvent.getPlayer().getDisplayName());
                    // Apply PlaceholderAPI placeholders if installed
                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        message = PlaceholderAPI.setPlaceholders(joinEvent.getPlayer(), message);
                    }
                }

                // Replace placeholders for when player quits server
                case PlayerQuitEvent quitEvent -> {
                    message = message.replace(Placeholders.PLAYER_NAME.key(), quitEvent.getPlayer().getDisplayName());
                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        message = PlaceholderAPI.setPlaceholders(quitEvent.getPlayer(), message);
                    }
                }

                // Replace placeholders for when player dies
                case PlayerDeathEvent deathEvent -> {
                    message = message
                            .replace(Placeholders.PLAYER_NAME.key(), deathEvent.getEntity().getDisplayName())
                            .replace(
                                    Placeholders.DEATH_MESSAGE.key(),
                                    Objects.requireNonNullElse(
                                            deathEvent.getDeathMessage(),
                                            Placeholders.DEATH_MESSAGE.key()));
                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        message = PlaceholderAPI.setPlaceholders(deathEvent.getEntity(), message);
                    }
                }

                // Replace placeholders for when player makes an advancement
                case PlayerAdvancementDoneEvent advancementEvent -> {
                    message = message
                            .replace(Placeholders.PLAYER_NAME.key(), advancementEvent.getPlayer().getDisplayName())
                            .replace(Placeholders.ADVANCEMENT_NAME.key(),
                                    Objects.requireNonNull(advancementEvent.getAdvancement().getDisplay()).getTitle())
                            .replace(Placeholders.ADVANCEMENT_COLOR.key(), "&"
                                    + advancementEvent.getAdvancement().getDisplay().getType().getColor().getChar());
                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        message = PlaceholderAPI.setPlaceholders(advancementEvent.getPlayer(), message);
                    }
                }

                // Replace placeholders for when player sends a message in the server chat
                case AsyncPlayerChatEvent asyncPlayerChatEvent -> {
                    message = message
                            .replace(Placeholders.PLAYER_NAME.key(), "%1$s")
                            .replace(Placeholders.CHAT_MESSAGE.key(), "%2$s");
                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        message = PlaceholderAPI.setPlaceholders(asyncPlayerChatEvent.getPlayer(), message);
                    }
                }

                default -> {
                }
            }
        }

        // Translate color codes
        message = ChatColor.translateAlternateColorCodes('&', message);

        // Return the message with the placeholders set
        return message;
    }

    // Helper function to insert placeholders and colors into messages for commands
    public String setPlaceholders(String message, CommandSender sender, Player recipient, String whisperMessage,
            WhisperCommand.Target target) {
        // Insert current plugin version into placeholder
        message = message.replace(Placeholders.PLUGIN_VERSION.key(), plugin.getDescription().getVersion());

        // Insert command sender name
        if (sender instanceof Player player) {
            message = message
                    .replace(Placeholders.SENDER_NAME.key(), player.getDisplayName());
        } else {
            message = message
                    .replace(Placeholders.SENDER_NAME.key(), sender.getName());
        }

        // Insert recipient player name
        if (recipient != null) {
            message = message.replace(Placeholders.RECIPIENT_NAME.key(), recipient.getDisplayName());
        }

        // Insert whisper message
        if (whisperMessage != null) {
            message = message.replace(Placeholders.WHISPER_MESSAGE.key(), whisperMessage);
        }

        // Decide PlaceholderAPI context based on target
        Player PAPIContext = switch (target) {
            case SENDER -> sender instanceof Player player ? player : null;
            case RECIPIENT -> recipient;
        };

        // Apply PlaceholderAPI placeholders if installed
        if (PAPIContext != null && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            message = PlaceholderAPI.setPlaceholders(PAPIContext, message);
        }

        // Translate color codes
        message = ChatColor.translateAlternateColorCodes('&', message);

        // Return the message with the placeholders set
        return message;
    }
}
