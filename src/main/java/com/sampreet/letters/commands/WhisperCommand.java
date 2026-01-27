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
    private final Letters plugin;

    public WhisperCommand(Letters plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String labelString, @NotNull String[] args) {
        if (args.length == 0) {
            redirectVanilla(sender, labelString, args);
            return true;
        }

        Player recipient = Bukkit.getPlayerExact(args[0]);
        if (recipient == null) {
            redirectVanilla(sender, labelString, args);
            return true;
        }

        if (args.length < 2) {
            redirectVanilla(sender, labelString, args);
            return true;
        }

        String whisperMessage = String.join(" ", args)
                .substring(args[0].length())
                .trim();

        if (whisperMessage.isEmpty()) {
            redirectVanilla(sender, labelString, args);
            return true;
        }

        String senderMessage = null;
        if (sender instanceof Player player) {
            senderMessage = MessagesHelper.resolveRandomMessage(player, "whisper.sender", plugin);
            senderMessage = PlaceholderApiHook.usePlaceholderAPI(player, senderMessage);
        } else {
            senderMessage = MessagesHelper.getRandomMessage("default.whisper.sender", plugin);
        }

        Component senderMessageComponent = MessagesHelper.translateColors(senderMessage);
        senderMessageComponent = PlaceholdersHelper.setPlaceholders(
                senderMessageComponent, sender, recipient, whisperMessage, plugin
        );

        String recipientMessage = MessagesHelper.resolveRandomMessage(recipient, "whisper.recipient", plugin);
        recipientMessage = PlaceholderApiHook.usePlaceholderAPI(recipient, recipientMessage);
        Component recipientMessageComponent = MessagesHelper.translateColors(recipientMessage);
        recipientMessageComponent = PlaceholdersHelper.setPlaceholders(
                recipientMessageComponent, sender, recipient, whisperMessage, plugin
        );

        if (senderMessageComponent != null)
            sender.sendMessage(senderMessageComponent);
        if (recipientMessageComponent != null)
            recipient.sendMessage(recipientMessageComponent);

        return true;
    }

    private void redirectVanilla(CommandSender sender, String label, String @NotNull [] args) {
        Bukkit.dispatchCommand(
                sender,
                "minecraft:" + label + (args.length == 0 ? "" : " " + String.join(" ", args))
        );
    }
}