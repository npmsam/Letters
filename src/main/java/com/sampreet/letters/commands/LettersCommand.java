package com.sampreet.letters.commands;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LettersCommand implements CommandExecutor, TabCompleter {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public LettersCommand(Letters plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String labelString, String[] args) {
        // Check if no subcommand was written.
        if (args.length == 0) {
            sendConfigMessage(sender, "messages.system.commands.no-command");
            return true;
        }

        // Check if the reload command was run
        if (args[0].equalsIgnoreCase("reload")) {
            // Check if the sender has the required permission
            if (!sender.hasPermission("letters.reload")) {
                sendConfigMessage(sender, "messages.system.commands.no-permission");
                return true;
            }

            // Reload the config and send a confirmation message
            plugin.reloadConfig();
            sendConfigMessage(sender, "messages.system.commands.reload");
            return true;
        }

        // If the subcommand was not found
        sendConfigMessage(sender, "messages.system.commands.invalid-command");
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        // Create a list to store possible completions
        List<String> completions = new ArrayList<>();

        // Only provide completions for the first argument (the subcommand)
        if (args.length != 1) return completions;

        // If the user has started typing "reload", suggest it
        if ("reload".startsWith(args[0].toLowerCase()) && sender.hasPermission("letters.reload")) {
            completions.add("reload");
        }

        // Return the list of suggestions
        return completions;
    }

    // Helper function to send a message to the command sender from the config
    private void sendConfigMessage(CommandSender sender, String path) {
        // Retrieve message from config and return if message is null
        String message = plugin.getConfig().getString(path);
        if (message == null || message.trim().isEmpty()) return;

        // Put colors into the message by translating & color codes and mini message
        Component messageComponent = MessagesHelper.translateColors(message);

        // Insert placeholders into the message
        messageComponent = PlaceholdersHelper.setPlaceholders(messageComponent, plugin);

        // Send the message to the command sender
        sender.sendMessage(messageComponent);
    }
}
