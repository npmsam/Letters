package com.sampreet.letters.commands;

import com.sampreet.letters.Letters;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WhisperCommand implements CommandExecutor, TabCompleter {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public WhisperCommand(Letters plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label,
                             String @NonNull [] args) {
        // Check if the player has the permission to have custom whisper messages
        if (!sender.hasPermission("letters.whisper")) {
            // Redirect to vanilla /msg
            Bukkit.dispatchCommand(
                    sender,
                    "minecraft:msg" + (args.length == 0 ? "" : " " + String.join(" ", args)));
            return true;
        }

        // Check if no player name was provided.
        if (args.length == 0) {
            // Send error message from config.yml
            sender.sendMessage(plugin.getUtils().setPlaceholders(
                    plugin.getUtils().getMessage("messages.system.commands.no-player")));
            return true;
        }

        // Find recipient player
        Player recipient = Bukkit.getPlayerExact(args[0]);
        if (recipient == null) {
            // Send error message from config.yml
            sender.sendMessage(plugin.getUtils().setPlaceholders(
                    plugin.getUtils().getMessage("messages.system.commands.invalid-player")));
            return true;
        }

        // Check if no message was provided
        if (args.length < 2) {
            // Send error message from config.yml
            sender.sendMessage(plugin.getUtils().setPlaceholders(
                    plugin.getUtils().getMessage("messages.system.commands.no-message")));
            return true;
        }

        // Build whisper message
        String whisperMessage = String.join(" ", args)
                .substring(args[0].length())
                .trim();

        // Check if the built whisper message is empty
        if (whisperMessage.isEmpty()) {
            // Send error message from config.yml
            sender.sendMessage(plugin.getUtils().setPlaceholders(
                    plugin.getUtils().getMessage("messages.system.commands.no-message")));
            return true;
        }

        // Retrieve a random message from config.yml
        String senderMessage = plugin.getUtils().resolveRandomMessage((Player) sender, "whisper.sender");
        // Insert placeholders and colors into the message
        senderMessage = plugin.getUtils().setPlaceholders(senderMessage, sender, recipient, whisperMessage, Target.SENDER);

        // Retrieve a random message from config.yml
        String recipientMessage = plugin.getUtils().resolveRandomMessage(recipient, "whisper.recipient");
        // Insert placeholders and colors into the message
        recipientMessage = plugin.getUtils().setPlaceholders(recipientMessage, sender, recipient, whisperMessage, Target.RECIPIENT);

        // Send formatted whisper messages
        sender.sendMessage(senderMessage);
        recipient.sendMessage(recipientMessage);

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias,
                                      String @NonNull [] args) {
        // Create a list to store possible completions
        List<String> completions = new ArrayList<>();

        // Only provide completions for the first argument (the recipient name)
        if (args.length == 1) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(player.getName());
                }
            }
        }

        // Return the list of suggestions
        return completions;
    }

    // Defines whether the whisper message format is for the sender or the recipient.
    public enum Target {
        SENDER,
        RECIPIENT
    }
}
