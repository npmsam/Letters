package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerJoinListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        // Make empty message variable so it can be conditionally set later
        String joinMessage;

        // Check if player is joining the server for the first time
        if (!event.getPlayer().hasPlayedBefore())
            joinMessage = MessagesHelper.resolveRandomMessage(event.getPlayer(), "first-join", plugin);
            // Retrieve a random default message from config.yml
        else joinMessage = MessagesHelper.resolveRandomMessage(event.getPlayer(), "join", plugin);

        // Insert PlaceholderAPI placeholders into the message
        joinMessage = PlaceholderApiHook.usePlaceholderAPI(event.getPlayer(), joinMessage);

        // Put colors into the message by translating & color codes and mini message
        Component joinMessageComponent = MessagesHelper.translateColors(joinMessage);

        // Insert placeholders into the message
        joinMessageComponent = PlaceholdersHelper.setPlaceholders(joinMessageComponent, event, plugin);

        // Set the custom message as the system message
        event.joinMessage(joinMessageComponent);
    }
}
