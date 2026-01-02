package com.sampreet.letters.listeners;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import com.sampreet.letters.Letters;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerJoinListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(@NonNull PlayerJoinEvent event) {
        // Check if the player has the permission to have custom join messages
        if (!event.getPlayer().hasPermission("letters.join"))
            return;

        // Retrieve a random message from config.yml
        String joinMessage = plugin.getUtils().resolveRandomMessage(event.getPlayer(), "join");
        // Insert placeholders and colors into the message
        joinMessage = plugin.getUtils().setPlaceholders(joinMessage, event);

        // Set the custom message as the system message
        event.setJoinMessage(joinMessage);
    }
}
