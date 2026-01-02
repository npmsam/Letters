package com.sampreet.letters.listeners;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventHandler;
import com.sampreet.letters.Letters;
import org.bukkit.event.Listener;

public class PlayerDeathListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerDeathListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(@NonNull PlayerDeathEvent event) {
        // Check if the player has the permission to have custom death messages
        if (!event.getEntity().hasPermission("letters.death"))
            return;

        // Try getting the player-specific message from config.yml
        String deathMessage = plugin.getUtils().getRandomMessage("messages.players." + event.getEntity().getName() + ".death");
        // If none found, fall back to default message
        if (deathMessage==null) {
            // Retrieve a random message from config.yml
            deathMessage = plugin.getUtils().getRandomMessage("messages.default.death");
        }
        // Insert placeholders and colors into the message
        deathMessage = plugin.getUtils().setPlaceholders(deathMessage, event);

        // Set the custom message as the system message
        event.setDeathMessage(deathMessage);
    }
}
