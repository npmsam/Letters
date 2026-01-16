package com.sampreet.letters.commands;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WhisperCommand implements CommandExecutor {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public WhisperCommand(Letters plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String labelString,
                             String @NotNull [] args) {
        // Check if no player name was provided.
        if (args.length == 0) {
            // Redirect to vanilla /msg
            redirectVanilla(sender, labelString, args);
            return true;
        }

        // Find recipient player
        Player recipient = Bukkit.getPlayerExact(args[0]);
        if (recipient == null) {
            // Redirect to vanilla /msg
            redirectVanilla(sender, labelString, args);
            return true;
        }

        // Check if no message was provided
        if (args.length < 2) {
            // Redirect to vanilla /msg
            redirectVanilla(sender, labelString, args);
            return true;
        }

        // Build whisper message
        String whisperMessage = String.join(" ", args)
                .substring(args[0].length())
                .trim();

        // Check if the built whisper message is empty
        if (whisperMessage.isEmpty()) {
            // Redirect to vanilla /msg
            redirectVanilla(sender, labelString, args);
            return true;
        }

        // Retrieve a random message from config.yml
        String senderMessage = MessagesHelper.getRandomMessage("messages.default.whisper.sender", plugin);
        // Insert PlaceholderAPI placeholders into the message
        if (sender instanceof Player player)
            senderMessage = PlaceholderApiHook.usePlaceholderAPI(player, senderMessage);
        // Put colors into the message by translating & color codes and mini message
        Component senderMessageComponent = MessagesHelper.translateColors(senderMessage);
        // Insert placeholders into the message
        senderMessageComponent = PlaceholdersHelper.setPlaceholders(senderMessageComponent, sender, recipient,
                whisperMessage, plugin);

        // Retrieve a random message from config.yml
        String recipientMessage = MessagesHelper.getRandomMessage("messages.default.whisper.recipient", plugin);
        // Insert PlaceholderAPI placeholders into the message
        recipientMessage = PlaceholderApiHook.usePlaceholderAPI(recipient, recipientMessage);
        // Put colors into the message by translating & color codes and mini message
        Component recipientMessageComponent = MessagesHelper.translateColors(recipientMessage);
        // Insert placeholders into the message
        recipientMessageComponent = PlaceholdersHelper.setPlaceholders(recipientMessageComponent, sender, recipient,
                whisperMessage, plugin);

        // Send formatted whisper messages
        if (senderMessage != null)
            sender.sendMessage(senderMessageComponent);
        if (recipientMessage != null)
            recipient.sendMessage(recipientMessageComponent);
        return true;
    }

    // Helper function to redirect to the vanilla command
    private void redirectVanilla(CommandSender sender, String label, String[] args) {
        Bukkit.dispatchCommand(sender, "minecraft:" + label
                + (args.length == 0 ? "" : " " + String.join(" ", args)));
    }
}
