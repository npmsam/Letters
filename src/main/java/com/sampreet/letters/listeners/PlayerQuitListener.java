package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PlayerQuitListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerQuitListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(@NonNull PlayerQuitEvent event) {
        // Check if the player has the permission to have custom quit messages
        if (!event.getPlayer().hasPermission("letters.quit"))
            return;

        // Retrieve a random message from config.yml
        String quitMessage = plugin.getUtils().resolveRandomMessage(event.getPlayer(), "quit");
        // Insert placeholders and colors into the message
        quitMessage = plugin.getUtils().setPlaceholders(quitMessage, event);
        // Translate MiniMessage and legacy color codes to a string
        quitMessage = plugin.getUtils().translateColors(quitMessage);

        // Set the custom message as the system message
        event.setQuitMessage(quitMessage);
    }
}
