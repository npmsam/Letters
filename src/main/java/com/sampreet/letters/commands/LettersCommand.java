package com.sampreet.letters.commands;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LettersCommand implements CommandExecutor {
    private final Letters plugin;

    public LettersCommand(Letters plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String labelString,
                             String @NotNull [] args) {
        if (args.length == 0) {
            sendMessage(sender, "system.commands.errors.no_command");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("letters.reload")) {
                sendMessage(sender, "system.commands.errors.no_permission");
                return true;
            }

            plugin.reloadConfig();
            sendMessage(sender, "system.commands.reload");
            return true;
        }

        sendMessage(sender, "system.commands.errors.invalid_command");
        return true;
    }

    private void sendMessage(CommandSender sender, String path) {
        String message = plugin.getConfig().getString(path);
        if (message == null || message.trim().isEmpty())
            return;

        if (sender instanceof Player player)
            message = PlaceholderApiHook.usePlaceholderAPI(player, message);

        Component messageComponent = MessagesHelper.translateColors(message);
        messageComponent = PlaceholdersHelper.setPlaceholders(messageComponent, plugin);

        sender.sendMessage(messageComponent);
    }
}