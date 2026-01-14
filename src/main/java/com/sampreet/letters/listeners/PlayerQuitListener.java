package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerQuitListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuitJoin(PlayerQuitEvent event) {
        // Retrieve a random message from config.yml
        String quitMessage = MessagesHelper.getRandomMessage("messages.default.quit", plugin);

        // Insert placeholders into the message
        quitMessage = PlaceholdersHelper.setPlaceholders(quitMessage, event, plugin);

        // Set the custom message as the system message
        event.setQuitMessage(quitMessage);
    }
}
