package com.sampreet.letters.commands;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.sampreet.letters.Letters;
import org.bukkit.command.Command;

public class RootCommand implements CommandExecutor {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public RootCommand(Letters plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String [] args) {
        // Check if no subcommand was written.
        if (args.length == 0) {
            sendMessage(sender, "messages.system.commands.no-command");
            return true;
        }

        // Check if the reload command was run
        if (args[0].equalsIgnoreCase("reload")) {
            // Check if the sender has the required permission
            if (!sender.hasPermission("letters.reload")) {
                sendMessage(sender, "messages.system.commands.no-permission");
                return true;
            }
            // Reload the config and send a confirmation message
            plugin.reloadConfig();
            sendMessage(sender, "messages.system.commands.reload");
            return true;
        }

        // If the subcommand was not found
        sendMessage(sender, "messages.system.commands.invalid-command");
        return true;
    }

    // Helper function to send a message to the command sender from config.yml
    private void sendMessage(CommandSender sender, String path) {
        String message = plugin.getUtils().getMessage(path);

        // Return if no message is set in config.yml
        if (message==null) {
            return;
        }

        // Insert placeholders and colors into message
        message = plugin.getUtils().setPlaceholders(message);

        sender.sendMessage(message);
    }
}
